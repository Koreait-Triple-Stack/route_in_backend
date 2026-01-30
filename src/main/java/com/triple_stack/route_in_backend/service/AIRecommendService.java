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
import com.triple_stack.route_in_backend.entity.AIQuestion;
import com.triple_stack.route_in_backend.entity.AIRecommend;
import com.triple_stack.route_in_backend.repository.AIQuestionRepository;
import com.triple_stack.route_in_backend.repository.AIRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 주입 (Autowired 대신 권장)
public class AIRecommendService {

    @Autowired
    private AIRecommendRepository aiRecommendRepository;
    @Autowired
    private AIQuestionRepository aiQuestionRepository;

    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public ApiRespDto<?> getAIChatListByUserId(Integer userId) {
        List<AIQuestion> aiChatList = aiQuestionRepository.getAIChatListByUserId(userId);
        if (aiChatList.isEmpty()) {
            throw new RuntimeException("채팅 리스트를 가져오는데 실패했습니다.");
        }
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
            반드시 아래 JSON 형식으로만 응답하세요. (설명이나 마크다운 ```json 금지)
            {
                "runningTitle": "추천 제목",
                "runningReason": "추천 이유",
                "runningTags": ["#태그1", "#태그2"]
                "routineTitle": "추천 제목",
                "routineReason": "추천 이유",
                "routineTags": ["#태그1", "#태그2"]
            }
            """, aiRecommendRepository.getAIContext(userId));

            // 안전한 JSON Body 생성 (String 조작 대신 Jackson 사용)
            // {"contents": [{"parts": [{"text": "..."}]}]} 구조 생성
            ObjectNode contentNode = objectMapper.createObjectNode();
            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode partNode = objectMapper.createObjectNode();

            partNode.put("text", prompt);
            partsArray.add(partNode);
            contentNode.putArray("parts").addAll(partsArray);

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.putArray("contents").add(contentNode);

            String requestBody = objectMapper.writeValueAsString(rootNode);

            // HTTP 요청
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int result = aiRecommendRepository.addRecommendation(new AddRecommendationDto(userId, parseGeminiResponse(response.body())));

            if (result != 1) {
                throw new RuntimeException("추천운동 저장 중 오류가 발생했습니다.");
            }

            return new ApiRespDto<>("success", "새로운 추천을 완료했습니다.", result);

        } catch (Exception e) {
            e.printStackTrace();
            // 구체적인 에러 메시지 반환
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

    // [Helper 메소드] API 응답 JSON에서 텍스트만 뽑아내는 로직
    private String extractContentFromResponse(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            // Gemini API 응답 구조: candidates[0].content.parts[0].text
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

        // 안전 장치: 응답이 비어있거나 에러일 경우 처리
        if (!rootNode.has("candidates") || rootNode.path("candidates").isEmpty()) {
            // Gemini가 안전 필터 등으로 인해 응답을 거부했을 경우 등
            throw new RuntimeException("Gemini API 응답이 올바르지 않습니다.");
        }

        String contentText = rootNode.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();

        // 마크다운 제거 로직
        if (contentText.startsWith("```json")) {
            contentText = contentText.substring(7);
        } else if (contentText.startsWith("```")) { // json 글자 없이 ```만 있는 경우 대비
            contentText = contentText.substring(3);
        }

        if (contentText.endsWith("```")) {
            contentText = contentText.substring(0, contentText.length() - 3);
        }

        return objectMapper.readValue(contentText, RecommendationDto.class);
    }
}