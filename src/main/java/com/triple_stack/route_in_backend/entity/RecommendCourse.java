package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendCourse {
    private Integer recommendCourseId;
    private Integer boardId;
    private Integer distanceM;
    private Double centerLat;
    private Double centerLng;
    private String region;
    private Integer estimatedMinutes;
    private String level;
    private LocalDate createDt;
}
