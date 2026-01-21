package com.triple_stack.route_in_backend.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCourseFavoriteReqDto {
    private Integer userId;
    private Integer courseId;
}
