package com.triple_stack.route_in_backend.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChangeWeeklyRunReqDto {
    private Integer userId;
    private List<String> WeeklyRun;
}
