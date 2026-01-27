package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.chat.MessageInfiniteParam;
import com.triple_stack.route_in_backend.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MessageMapper {
    int addMessage(Message message);
    int changeMessage(Message message);
    List<Message> getMessageListInfinite(MessageInfiniteParam messageInfiniteParam);
    Optional<Message> getMessageByMessageId(Integer messageId);
}
