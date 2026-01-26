package com.triple_stack.route_in_backend.dto.attendance;

import lombok.Data;

@Data
public class AttendanceMonthReqDto {
    private Integer userId;
    private String ym;
}
