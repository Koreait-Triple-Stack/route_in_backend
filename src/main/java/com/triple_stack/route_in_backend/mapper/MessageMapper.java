package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.message.MessageInfiniteParam;
import com.triple_stack.route_in_backend.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    int addMessage(Message message);
    int deleteMessage(Integer messageId);
    List<Message> getMessageListInfinite(MessageInfiniteParam messageInfiniteParam);
}
