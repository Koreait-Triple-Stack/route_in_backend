package com.triple_stack.route_in_backend.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AIRespDto {
    private Integer userId;
    private String question;
    private String resp;
}
