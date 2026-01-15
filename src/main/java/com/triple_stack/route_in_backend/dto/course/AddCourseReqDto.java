package com.triple_stack.route_in_backend.dto.course;

import com.triple_stack.route_in_backend.entity.Course;
import com.triple_stack.route_in_backend.entity.CoursePoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseReqDto {
    private Integer userId;
    private Integer boardId;
    private String courseName;
    private Integer distanceM;
    private Double centerLat;
    private Double centerLng;
    private String si;
    private String gu;
    private String dong;

    private List<CoursePoint> points;

    public Course toEntity() {
        return Course.builder()
                .userId(userId)
                .boardId(boardId)
                .courseName(courseName)
                .distanceM(distanceM)
                .centerLat(centerLat)
                .centerLng(centerLng)
                .si(si)
                .gu(gu)
                .dong(dong)
                .build();
    }
}
