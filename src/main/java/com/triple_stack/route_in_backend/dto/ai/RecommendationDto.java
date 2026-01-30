package com.triple_stack.route_in_backend.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RecommendationDto {
    private String runningTitle;
    private String runningReason;
    private List<String> runningTags;
    private String routineTitle;
    private String routineReason;
    private List<String> routineTags;
}