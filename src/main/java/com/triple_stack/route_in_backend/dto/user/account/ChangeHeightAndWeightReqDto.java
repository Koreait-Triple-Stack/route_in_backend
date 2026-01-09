package com.triple_stack.route_in_backend.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeHeightAndWeightReqDto {
    private Integer userId;
    private Integer height;
    private Integer weight;
}
