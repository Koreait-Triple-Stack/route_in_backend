package com.triple_stack.route_in_backend.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 필요 없는 필드는 무시
public class WeatherRespDto {
    private List<Weather> weather;
    private Main main;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main; // 날씨 상태 (Rain, Clear, Clouds, Snow 등)
        private String description; // 상세 설명
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private double temp; // 현재 온도
    }
}
