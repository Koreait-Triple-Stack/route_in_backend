package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.Gemini.AIRecommendReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AIRecommend {
    Optional<AIRecommendReqDto> getAIContext(Integer userId);
}
