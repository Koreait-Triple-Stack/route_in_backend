package com.triple_stack.route_in_backend.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRespDto {
    private String description; // 날씨 설명 (예: 맑음, 구름 조금)
    private Double temp;        // 현재 온도
    private Double feelsLike;   // 체감 온도
    private Integer humidity;   // 습도
    private String icon;        // 날씨 아이콘 코드 (이미지 URL 만들 때 사용)
    private String city;        // 도시 이름
}
