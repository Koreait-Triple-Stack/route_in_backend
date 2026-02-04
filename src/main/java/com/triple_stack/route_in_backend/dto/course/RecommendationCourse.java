package com.triple_stack.route_in_backend.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationCourse {
    private Integer distanceM;
    private Double centerLat;
    private Double centerLng;
    private String region;
    private Integer estimatedMinutes;
}
