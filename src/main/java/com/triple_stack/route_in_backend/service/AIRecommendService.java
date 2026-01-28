package com.triple_stack.route_in_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.Gemini.RecommendationDto;
import com.triple_stack.route_in_backend.repository.AIRecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class AIRecommendService {
    @Autowired
    private AIRecommendRepository aiRecommendRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환기

    public ApiRespDto<?> getAIContext(Integer userId) {
        try {
            String userContext = "사용자 정보는 " + aiRecommendRepository.getAIContext(userId);
            // 1. 프롬프트 구성 (JSON 포맷 강제)
            String prompt = String.format("""
            당신은 전문 퍼스널 트레이너이자 러닝 코치입니다.
            다음은 우리 회원의 상세 프로필과 최근 운동 기록입니다.

            [회원 프로필 데이터]
            %s

            [당신의 임무]
            위 회원의 신체 상태(인바디 기록, 키, 몸무게 등)와 최근 운동 기록(운동 강도)을 정밀 분석하여,
            오늘 당장 수행하기 가장 적합한 '러닝 코스' 또는 '홈트레이닝 루틴' 중 하나를 추천해주세요.

            [분석 가이드]
            - 어제 운동을 많이 했다면 '회복(Recovery)' 위주로 추천하세요.
            - 오랫동안 운동을 안 했다면 '기초 체력' 위주로 추천하세요.
            - 특정 부위 부상이 있다면 그 부위에 무리가 가지 않는 운동을 추천하세요.

            [응답 형식]
            반드시 아래 JSON 형식으로만 응답하세요. (설명이나 마크다운 ```json 금지)
            {
                "title": "추천 루틴/코스 제목 (예: 관절 부담 없는 가벼운 조깅)",
                "reason": "추천 이유 (회원의 프로필 중 어떤 점을 고려했는지 구체적으로 명시)",
                "tags": ["#태그1", "#태그2", "#회복러닝"]
            }
            """, userContext);

            // 2. 요청 JSON 본문 만들기 (Gemini API 규격)
            // 텍스트 안에 줄바꿈이 있으면 에러가 날 수 있으므로 간단히 처리
            String requestBody = "{\"contents\": [{\"parts\": [{\"text\": \""
                    + prompt.replace("\"", "\\\"").replace("\n", " ")
                    + "\"}]}]}";

            // 3. HTTP 요청 보내기 (아까 테스트한 코드와 동일)
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. 응답 파싱 (여기가 핵심!)
            return new ApiRespDto<>("success", "성공", parseGeminiResponse(response.body()));

        } catch (Exception e) {
            e.printStackTrace();
            // 에러 시 빈 객체나 에러 객체 반환
            return new ApiRespDto<>("success", "성공", new RecommendationDto());
        }
    }

    // 복잡한 Gemini 응답 JSON에서 우리가 원하는 알맹이만 쏙 빼내는 메서드
    private RecommendationDto parseGeminiResponse(String responseBody) throws Exception {
        // 1단계: Gemini 전체 응답을 Map으로 읽기
        var rootNode = objectMapper.readTree(responseBody);

        // 2단계: 깊숙이 숨겨진 텍스트 추출 (candidates -> content -> parts -> text)
        String contentText = rootNode.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();

        // 3단계: AI가 가끔 ```json ... ``` 이렇게 줄 때가 있어서 청소
        if (contentText.startsWith("```json")) {
            contentText = contentText.substring(7); // ```json 제거
        }
        if (contentText.endsWith("```")) {
            contentText = contentText.substring(0, contentText.length() - 3); // 뒤에 ``` 제거
        }

        // 4단계: 깨끗해진 문자열을 DTO로 변환
        return objectMapper.readValue(contentText, RecommendationDto.class);
    }
}