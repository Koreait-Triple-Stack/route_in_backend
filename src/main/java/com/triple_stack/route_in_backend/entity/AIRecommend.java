package com.triple_stack.route_in_backend.entity;

import com.triple_stack.route_in_backend.dto.ai.RecommendationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIRecommend {
    private Integer aiRecommendId;
    private Integer userId;
    private RecommendationDto aiResp;
    private LocalDate createDt;
}
