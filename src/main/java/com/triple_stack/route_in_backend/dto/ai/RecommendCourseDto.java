package com.triple_stack.route_in_backend.dto.ai;

import com.triple_stack.route_in_backend.entity.CoursePoint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RecommendCourseDto {
    private Integer boardId;
    private Integer distanceM;
    private Double centerLat;
    private Double centerLng;
    private String region;
    private Integer estimatedMinutes;
    private String level;
    private LocalDate createDt;
    private List<CoursePoint> points;
}
