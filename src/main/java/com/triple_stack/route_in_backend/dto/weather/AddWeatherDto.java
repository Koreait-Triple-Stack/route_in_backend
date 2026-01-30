package com.triple_stack.route_in_backend.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddWeatherDto {
    private Integer userId;
    private Double lat;
    private Double lng;
    private String status;
    private String description;
    private Float temp;
}
