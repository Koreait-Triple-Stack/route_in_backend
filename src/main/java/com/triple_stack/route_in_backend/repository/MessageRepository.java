package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.chat.MessageInfiniteParam;
import com.triple_stack.route_in_backend.entity.Message;
import com.triple_stack.route_in_backend.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepository {
    @Autowired
    private MessageMapper messageMapper;

    public int addMessage(Message message) {
        return messageMapper.addMessage(message);
    }

    public int changeMessage(Message message) {
        return messageMapper.changeMessage(message);
    }

    public List<Message> getMessageListInfinite(MessageInfiniteParam messageInfiniteParam) {
        return messageMapper.getMessageListInfinite(messageInfiniteParam);
    }

    public Optional<Message> getMessageByMessageId(Integer messageId) {
        return messageMapper.getMessageByMessageId(messageId);
    }
}
