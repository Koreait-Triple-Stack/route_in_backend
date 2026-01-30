package com.triple_stack.route_in_backend.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendQuestionDto {
    private Integer userId;
    private String question;
}
