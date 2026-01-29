package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.Message;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import com.triple_stack.route_in_backend.entity.RoomRead;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddRoomParticipantReqDto {
    private Integer roomId;
    private List<Integer> userIds;
    private String title;

    public RoomParticipant toParticipantEntity(Integer userId, String profileImg) {
        return RoomParticipant.builder()
                .roomId(roomId)
                .userId(userId)
                .title(title)
                .profileImg(profileImg)
                .role("MEMBER")
                .build();
    }

    public Message toMessageEntity(String username) {
        return Message.builder()
                .roomId(roomId)
                .senderId(null)
                .type("TEXT")
                .content(username+"님이 입장하셨습니다")
                .build();
    }

    public RoomRead toReadEntity(Integer userId, Integer lastMessageId) {
        return RoomRead.builder()
                .roomId(roomId)
                .userId(userId)
                .lastReadMessageId(lastMessageId)
                .build();
    }
}
