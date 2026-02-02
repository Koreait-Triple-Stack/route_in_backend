package com.triple_stack.route_in_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.weather.WeatherRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper;

    public ApiRespDto<?> getWeather(double lat, double lon) {
        try {
            // 1. OpenWeatherMap API 호출 URL 생성
            // lat: 위도, lon: 경도, units=metric: 섭씨 온도, lang=kr: 한국어 응답
            String requestUrl = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric&lang=kr",
                    apiUrl, lat, lon, apiKey);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .GET()
                    .build();

            // 2. 요청 전송
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 3. 응답 파싱 (JSON -> DTO)
            return new ApiRespDto<>("success", "날씨 정보 조회 완료", parseWeatherResponse(response.body()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("날씨 정보를 가져오는데 실패했습니다.");
        }
    }

    private WeatherRespDto parseWeatherResponse(String jsonResponse) throws Exception {
        JsonNode root = objectMapper.readTree(jsonResponse);

        // OpenWeatherMap JSON 구조에 맞춰서 데이터 추출
        String description = root.path("weather").get(0).path("description").asText();
        String icon = root.path("weather").get(0).path("icon").asText();
        Double temp = root.path("main").path("temp").asDouble();
        Double feelsLike = root.path("main").path("feels_like").asDouble();
        Integer humidity = root.path("main").path("humidity").asInt();
        String city = root.path("name").asText();

        return WeatherRespDto.builder()
                .description(description)
                .temp(temp)
                .feelsLike(feelsLike)
                .humidity(humidity)
                .icon(icon)
                .city(city)
                .build();
    }
}