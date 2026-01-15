package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoursePoint {
    private Integer pointId;
    private Integer courseId;
    private Integer seq;
    private Double lat;
    private Double lng;
}
