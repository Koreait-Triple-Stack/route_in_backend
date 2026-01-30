package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.ai.AIRespDto;
import com.triple_stack.route_in_backend.entity.AIQuestion;
import com.triple_stack.route_in_backend.mapper.AIQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AIQuestionRepository {
    @Autowired
    private AIQuestionMapper aiQuestionMapper;

    public int sendQuestion(AIRespDto aiRespDto) {
        return aiQuestionMapper.sendQuestion(aiRespDto);
    }

    public List<AIQuestion> getAIChatListByUserId(Integer userId) {
        return aiQuestionMapper.getAIChatListByUserId(userId);
    }
}
