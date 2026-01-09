package com.triple_stack.route_in_backend.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChangeCurrentRunReqDto {
    private Integer userId;
    private List<String> currentRun;
}
