package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.ai.AIRecommendReqDto;
import com.triple_stack.route_in_backend.dto.ai.AddRecommendationDto;
import com.triple_stack.route_in_backend.entity.AIRecommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AIRecommendMapper {
    Optional<AIRecommendReqDto> getAIContext(Integer userId);
    Optional<AIRecommend> getRecommendationByUserId(Integer userId);
    int addRecommendation(AddRecommendationDto addRecommendationDto);
}
