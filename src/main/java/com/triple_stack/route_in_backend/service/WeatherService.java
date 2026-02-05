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

    @Value("${weather.geo.url}")
    private String geoUrl;

    private final ObjectMapper objectMapper;
    private final HttpClient client = HttpClient.newHttpClient();

    public ApiRespDto<?> getWeather(double lat, double lon) {
        try {
            String weatherRequestUrl = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric&lang=kr",
                    apiUrl, lat, lon, apiKey);
            String weatherJsonResponse = sendRequest(weatherRequestUrl);

            String geoRequestUrl = String.format("%s?lat=%f&lon=%f&limit=1&appid=%s",
                    geoUrl, lat, lon, apiKey);
            String geoJsonResponse = sendRequest(geoRequestUrl);

            return new ApiRespDto<>("success", "날씨 정보 조회 완료", parseWeatherResponse(weatherJsonResponse, geoJsonResponse));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("날씨 정보를 가져오는데 실패했습니다.");
        }
    }

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

        String description = weatherRoot.path("weather").get(0).path("description").asText();
        String icon = weatherRoot.path("weather").get(0).path("icon").asText();
        Double temp = weatherRoot.path("main").path("temp").asDouble();
        Double feelsLike = weatherRoot.path("main").path("feels_like").asDouble();
        Integer humidity = weatherRoot.path("main").path("humidity").asInt();

        String koreanCityName = "알 수 없음";

        if (geoRoot.isArray() && !geoRoot.isEmpty()) {
            JsonNode firstLocation = geoRoot.get(0);

            if (firstLocation.has("local_names") && firstLocation.get("local_names").has("ko")) {
                koreanCityName = firstLocation.get("local_names").get("ko").asText(); // "서울", "부산광역시" 등
            } else {
                koreanCityName = firstLocation.path("name").asText();
            }
        } else {
            koreanCityName = weatherRoot.path("name").asText();
        }

        return WeatherRespDto.builder()
                .description(description)
                .temp(temp)
                .feelsLike(feelsLike)
                .humidity(humidity)
                .icon(icon)
                .city(koreanCityName)
                .build();
    }
}
