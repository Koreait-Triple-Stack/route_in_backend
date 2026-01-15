package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private Integer courseId;
    private Integer userId;
    private Integer boardId;
    private String courseName;
    private Integer distanceM;
    private Double centerLat;
    private Double centerLng;
    private String region;
    private Integer favorite;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private List<CoursePoint> points;
}
