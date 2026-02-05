package com.triple_stack.route_in_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.triple_stack.route_in_backend.dto.ai.AIRespDto;
import com.triple_stack.route_in_backend.dto.ai.AddRecommendationDto;
import com.triple_stack.route_in_backend.dto.ai.SendQuestionDto;
import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.ai.RecommendationDto;
import com.triple_stack.route_in_backend.entity.*;
import com.triple_stack.route_in_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AIRecommendService {
    @Autowired
    private AIRecommendRepository aiRecommendRepository;
    @Autowired
    private AIQuestionRepository aiQuestionRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private RecommendCourseRepository recommendCourseRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;
    @Autowired
    private CourseRepository courseRepository;

    public ApiRespDto<?> getAIChatListByUserId(Integer userId) {
        List<AIQuestion> aiChatList = aiQuestionRepository.getAIChatListByUserId(userId);
        return new ApiRespDto<>("success", "채팅 리스트를 불러왔습니다.", aiChatList);
    }

    @Transactional
    public ApiRespDto<?> getTodayRecommendation(Integer userId) {
        try {
            Optional<AIRecommend> optionalRecommendationDto = aiRecommendRepository.getRecommendationByUserId(userId);

            if (optionalRecommendationDto.isPresent()) {
                return new ApiRespDto<>("success", "오늘의 추천 내역을 불러왔습니다.", optionalRecommendationDto.get());
            }

            String prompt = String.format("""
            당신은 전문 퍼스널 트레이너이자 러닝 코치입니다.
            다음은 우리 회원의 상세 프로필과 최근 운동 기록입니다.

            [회원 프로필 데이터]
            %s

            [당신의 임무]
            위 회원의 신체 상태와 최근 운동 기록을 정밀 분석하여,
            오늘 당장 수행하기 가장 적합한 '러닝 코스' 와 '홈트레이닝 루틴' 중 하나를 추천해주세요.

            [분석 가이드]
            - 어제 운동을 많이 했다면 '회복(Recovery)' 위주로 추천하세요.
            - 오랫동안 운동을 안 했다면 '기초 체력' 위주로 추천하세요.
            - 특정 부위 부상이 있다면 그 부위에 무리가 가지 않는 운동을 추천하세요.

            [응답 형식]
            글자수는 200자 이하.
            반드시 아래 JSON 형식으로만 응답하세요. (설명이나 마크다운 ```json 금지)
            {
                "runningTitle": "추천 제목",
                "runningReason": "추천 이유",
                "runningTags": ["#태그1", "#태그2"],
                "routineTitle": "추천 제목",
                "routineReason": "추천 이유",
                "routineTags": ["#태그1", "#태그2"]
            }
            """, aiRecommendRepository.getAIContext(userId));

            ObjectNode contentNode = objectMapper.createObjectNode();
            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode partNode = objectMapper.createObjectNode();

            partNode.put("text", prompt);
            partsArray.add(partNode);
            contentNode.putArray("parts").addAll(partsArray);

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.putArray("contents").add(contentNode);

            ArrayNode safetySettingsArray = objectMapper.createArrayNode();
            String[] categories = {
                    "HARM_CATEGORY_HARASSMENT",
                    "HARM_CATEGORY_HATE_SPEECH",
                    "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                    "HARM_CATEGORY_DANGEROUS_CONTENT"
            };

            for (String category : categories) {
                ObjectNode setting = objectMapper.createObjectNode();
                setting.put("category", category);
                setting.put("threshold", "BLOCK_NONE");
                safetySettingsArray.add(setting);
            }
            rootNode.set("safetySettings", safetySettingsArray);

            String requestBody = objectMapper.writeValueAsString(rootNode);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            try {
                int result = aiRecommendRepository.addRecommendation(new AddRecommendationDto(userId, parseGeminiResponse(response.body())));
                if (result != 1) {
                    throw new RuntimeException("추천 운동 저장 실패");
                }

                return new ApiRespDto<>("success", "새로운 추천을 완료했습니다.", aiRecommendRepository.getRecommendationByUserId(userId));

            } catch (DuplicateKeyException e) {
                Optional<AIRecommend> retry = aiRecommendRepository.getRecommendationByUserId(userId);

                if (retry.isEmpty()) {
                    throw new RuntimeException("데이터 조회 중 알 수 없는 오류 발생");
                }

                return new ApiRespDto<>("success", "오늘의 추천 내역을 불러왔습니다.", retry.get());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AI 추천 실패: " + e.getMessage());
        }
    }

    public ApiRespDto<?> getAIResp(SendQuestionDto sendQuestionDto) {
        try {
            String userProfileJson = objectMapper.writeValueAsString(
                    aiRecommendRepository.getAIContext(sendQuestionDto.getUserId())
            );
            String todayRecommendationJson = objectMapper.writeValueAsString(
                    aiRecommendRepository.getRecommendationByUserId(sendQuestionDto.getUserId())
            );
            String historyJson = objectMapper.writeValueAsString(
                    aiQuestionRepository.getAIChatListByUserId(sendQuestionDto.getUserId())
            );

            String prompt = String.format("""
            당신은 전문 퍼스널 트레이너이자 러닝 코치입니다.
            다음은 우리 회원의 상세 프로필과 최근 운동 기록, 게시판 목록 그리고 오늘의 추천 운동입니다.
    
            [회원 프로필 데이터]
            %s
    
            [오늘의 운동 추천]
            %s
    
            [이전 대화 내역]
            %s
            
            [사용자 질문]
            %s
    
            [지침]
            글자수는 200자 이하.
            위 정보를 바탕으로 사용자의 질문에 대해 친절하게 줄글(Text)로 답변하세요.
            """, userProfileJson, todayRecommendationJson, historyJson, sendQuestionDto.getQuestion());

            ObjectNode rootNode = objectMapper.createObjectNode();
            ArrayNode contentsArray = objectMapper.createArrayNode();
            ObjectNode contentNode = objectMapper.createObjectNode();
            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode partNode = objectMapper.createObjectNode();

            partNode.put("text", prompt);
            partsArray.add(partNode);
            contentNode.set("parts", partsArray);
            contentsArray.add(contentNode);
            rootNode.set("contents", contentsArray);

            ArrayNode safetySettingsArray = objectMapper.createArrayNode();
            String[] categories = {
                    "HARM_CATEGORY_HARASSMENT",
                    "HARM_CATEGORY_HATE_SPEECH",
                    "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                    "HARM_CATEGORY_DANGEROUS_CONTENT"
            };

            for (String category : categories) {
                ObjectNode setting = objectMapper.createObjectNode();
                setting.put("category", category);
                setting.put("threshold", "BLOCK_NONE");
                safetySettingsArray.add(setting);
            }
            rootNode.set("safetySettings", safetySettingsArray);

            String requestBody = objectMapper.writeValueAsString(rootNode);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String aiResponseText = extractContentFromResponse(response.body());

            AIRespDto aiRespDto = new AIRespDto(sendQuestionDto.getUserId(), sendQuestionDto.getQuestion(), aiResponseText);

            int result = aiQuestionRepository.sendQuestion(aiRespDto);
            if (result != 1) {
                throw new RuntimeException("AI 질문 저장 중 오류가 발생했습니다.");
            }

            return new ApiRespDto<>("success", "답변 완료", aiRespDto);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AI 처리 실패: " + e.getMessage());
        }
    }

    public ApiRespDto<?> getRecommendationCourse() {
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        try {
            Optional<RecommendCourse> optionalRecommendCourse = recommendCourseRepository.getCourseByDate(localDate);

            if (optionalRecommendCourse.isPresent()) {
                return new ApiRespDto<>("success", "오늘의 추천 내역을 불러왔습니다.", optionalRecommendCourse.get());
            }

            String prompt = String.format("""
            당신은 전문 러닝 코치입니다. 게시글 목록에서 boardId를 찾아와서 코스에 있는 정보,
            난이도 그리고 성인을 기준으로 했을 때 거리만큼 뛰었을 때 예상시간 추출.
            'estimatedMinutes' 계산 시, **반드시 평범한 성인의 러닝 속도(10km/h)를 기준으로 계산하세요.** (걷기 속도 아님)
            예: 3000m -> 18분, 5000m -> 30분
            'level' 예: 쉬움, 보통, 어려움
            'region'은 도시랑 구, 동까지만. 예: 서울시 중구 중림동
            
            [게시글]
            %s
            
            [코스]
            %s
            
            [지침]
            반드시 아래 JSON 형식으로만 응답하세요. (설명이나 마크다운 ```json 금지)
            {
                "boardId": 게시글Id,
                "distanceM": 거리,
                "centerLat": 위도,
                "centerLng": 경도,
                "region": "지역",
                "level": "난이도",
                "estimatedMinutes": 예상시간
            }
            """, boardRepository.getBoardByRecommendCnt(), courseRepository.getCourseList());
            ObjectNode contentNode = objectMapper.createObjectNode();
            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode partNode = objectMapper.createObjectNode();

            partNode.put("text", prompt);
            partsArray.add(partNode);
            contentNode.putArray("parts").addAll(partsArray);

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.putArray("contents").add(contentNode);

            String requestBody = objectMapper.writeValueAsString(rootNode);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            try {
                int result = recommendCourseRepository.addCourse(parseCourseGeminiResponse(response.body()));
                if (result != 1) {
                    throw new RuntimeException("추천 코스 저장 실패");
                }

                return new ApiRespDto<>("success", "추천 코스", parseCourseGeminiResponse(response.body()));

            } catch (DuplicateKeyException e) {
                Optional<RecommendCourse> retry = recommendCourseRepository.getCourseByDate(localDate);

                if (retry.isEmpty()) {
                    RecommendCourse recommendCourse = parseCourseGeminiResponse(response.body());
                    recommendCourse.setCreateDt(Date.valueOf(localDate).toLocalDate());
                    return new ApiRespDto<>("success", "오늘의 추천 코스(생성됨)", recommendCourse);
                }

                return new ApiRespDto<>("success", "오늘의 추천 내역을 불러왔습니다.", retry.get());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AI 추천 실패: " + e.getMessage());
        }
    }

    private String extractContentFromResponse(String jsonResponse) {
        System.out.println("Gemini Chat Response: " + jsonResponse);
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");
                if (parts.isArray() && parts.size() > 0) {
                    return parts.get(0).path("text").asText();
                }
            }
            return "죄송합니다. AI가 답변을 생성하지 못했습니다.";
        } catch (Exception e) {
            throw new RuntimeException("응답 파싱 오류");
        }
    }

    private RecommendationDto parseGeminiResponse(String responseBody) throws Exception {
        var rootNode = objectMapper.readTree(responseBody);

        if (!rootNode.has("candidates") || rootNode.path("candidates").isEmpty()) {
            throw new RuntimeException("Gemini API 응답이 올바르지 않습니다.");
        }

        String contentText = rootNode.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();

        if (contentText.startsWith("```json")) {
            contentText = contentText.substring(7);
        } else if (contentText.startsWith("```")) {
            contentText = contentText.substring(3);
        }

        if (contentText.endsWith("```")) {
            contentText = contentText.substring(0, contentText.length() - 3);
        }

        return objectMapper.readValue(contentText, RecommendationDto.class);
    }

    private RecommendCourse parseCourseGeminiResponse(String responseBody) throws Exception {
        JsonNode rootNode = objectMapper.readTree(responseBody);

        JsonNode candidates = rootNode.path("candidates");
        if (candidates.isMissingNode() || candidates.isEmpty()) {
            System.out.println("Gemini 응답 에러: " + responseBody);
            throw new RuntimeException("AI가 코스를 추천할 수 없습니다. (응답이 비어있음)");
        }

        String contentText = candidates.get(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText()
                .trim();

        if (contentText.isEmpty()) {
            throw new RuntimeException("AI 응답 텍스트가 비어있습니다.");
        }

        contentText = contentText.replaceAll("(?s)^```(?:json)?\\s*(.*?)\\s*```$", "$1").trim();
        JsonNode jsonNode = objectMapper.readTree(contentText);

        if (jsonNode.isArray()) {
            jsonNode = jsonNode.get(0);
        }

        return objectMapper.treeToValue(jsonNode, RecommendCourse.class);
    }
}