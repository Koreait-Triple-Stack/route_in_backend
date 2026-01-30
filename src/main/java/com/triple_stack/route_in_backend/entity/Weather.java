package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Weather {
    private Integer weatherId;
    private Integer userId;
    private Double lat;
    private Double lng;
    private String status;
    private String description;
    private Float temp;
}
