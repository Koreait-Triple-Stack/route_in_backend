package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.Gemini.AIRecommendReqDto;
import com.triple_stack.route_in_backend.mapper.AIRecommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AIRecommendRepository {
    @Autowired
    private AIRecommend aiRecommend;

    public Optional<AIRecommendReqDto> getAIContext(Integer userId) {
        return aiRecommend.getAIContext(userId);
    }
}
