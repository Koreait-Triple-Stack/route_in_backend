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

    @Value("${weather.geo.url}") // [추가] 지오코딩 URL
    private String geoUrl;

    private final ObjectMapper objectMapper;
    private final HttpClient client = HttpClient.newHttpClient();

    public ApiRespDto<?> getWeather(double lat, double lon) {
        try {
            // 1. [병렬 처리 권장되지만 일단 순차 처리] 날씨 데이터 가져오기
            String weatherRequestUrl = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric&lang=kr",
                    apiUrl, lat, lon, apiKey);
            String weatherJsonResponse = sendRequest(weatherRequestUrl);

            // 2. [추가] 정확한 한국어 도시 이름 가져오기 (Reverse Geocoding)
            // limit=1: 가장 정확한 1개만 가져옴
            String geoRequestUrl = String.format("%s?lat=%f&lon=%f&limit=1&appid=%s",
                    geoUrl, lat, lon, apiKey);
            String geoJsonResponse = sendRequest(geoRequestUrl);

            // 3. 응답 파싱 (JSON -> DTO)
            return new ApiRespDto<>("success", "날씨 정보 조회 완료", parseWeatherResponse(weatherJsonResponse, geoJsonResponse));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("날씨 정보를 가져오는데 실패했습니다.");
        }
    }

    // HTTP 요청 보내는 공통 메서드
    private String sendRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private WeatherRespDto parseWeatherResponse(String weatherJson, String geoJson) throws Exception {
        JsonNode weatherRoot = objectMapper.readTree(weatherJson);
        JsonNode geoRoot = objectMapper.readTree(geoJson);

        // 1. 날씨 정보 추출 (기존과 동일)
        String description = weatherRoot.path("weather").get(0).path("description").asText();
        String icon = weatherRoot.path("weather").get(0).path("icon").asText();
        Double temp = weatherRoot.path("main").path("temp").asDouble();
        Double feelsLike = weatherRoot.path("main").path("feels_like").asDouble();
        Integer humidity = weatherRoot.path("main").path("humidity").asInt();

        // 2. [핵심] 한국어 도시 이름 추출 로직
        String koreanCityName = "알 수 없음";

        // Geocoding 응답은 배열 형태입니다. ([...])
        if (geoRoot.isArray() && !geoRoot.isEmpty()) {
            JsonNode firstLocation = geoRoot.get(0);

            // "local_names" 안에 "ko"(한국어)가 있는지 확인
            if (firstLocation.has("local_names") && firstLocation.get("local_names").has("ko")) {
                koreanCityName = firstLocation.get("local_names").get("ko").asText(); // "서울", "부산광역시" 등
            } else {
                // 한국어 이름이 없으면 기본 name(영어) 사용
                koreanCityName = firstLocation.path("name").asText();
            }
        } else {
            // Geocoding 실패 시 날씨 API에 있던 name 사용 (비상용)
            koreanCityName = weatherRoot.path("name").asText();
        }

        return WeatherRespDto.builder()
                .description(description)
                .temp(temp)
                .feelsLike(feelsLike)
                .humidity(humidity)
                .icon(icon)
                .city(koreanCityName) // 정확한 한국어 이름 주입!
                .build();
    }
}
