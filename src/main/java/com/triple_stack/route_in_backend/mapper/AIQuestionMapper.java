package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.ai.AIRespDto;
import com.triple_stack.route_in_backend.entity.AIQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AIQuestionMapper {
    int sendQuestion(AIRespDto aiRespDto);
    List<AIQuestion> getAIChatListByUserId(Integer userId);
}
