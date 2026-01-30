package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.ai.AIRecommendReqDto;
import com.triple_stack.route_in_backend.dto.ai.AddRecommendationDto;
import com.triple_stack.route_in_backend.entity.AIRecommend;
import com.triple_stack.route_in_backend.mapper.AIRecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AIRecommendRepository {
    @Autowired
    private AIRecommendMapper aiRecommendMapper;

    public Optional<AIRecommendReqDto> getAIContext(Integer userId) {
        return aiRecommendMapper.getAIContext(userId);
    }

    public Optional<AIRecommend> getRecommendationByUserId(Integer userId) {
        return aiRecommendMapper.getRecommendationByUserId(userId);
    }

    public int addRecommendation(AddRecommendationDto addRecommendationDto) {
        return aiRecommendMapper.addRecommendation(addRecommendationDto);
    }
}
