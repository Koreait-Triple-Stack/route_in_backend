package com.triple_stack.route_in_backend.dto.Gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RecommendationDto {
    private String title;
    private String reason;
    private List<String> tags;
}