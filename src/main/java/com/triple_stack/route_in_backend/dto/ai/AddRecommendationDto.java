package com.triple_stack.route_in_backend.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddRecommendationDto {
    private Integer userId;
    private RecommendationDto aiResp;
}
