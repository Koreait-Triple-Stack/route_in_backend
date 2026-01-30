package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIQuestion {
    private Integer questionId;
    private Integer userId;
    private String question;
    private String resp;
    private LocalDate createDt;
}
