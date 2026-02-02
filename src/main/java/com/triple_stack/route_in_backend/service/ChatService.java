package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.chat.*;
import com.triple_stack.route_in_backend.entity.*;
import com.triple_stack.route_in_backend.repository.MessageRepository;
import com.triple_stack.route_in_backend.repository.RoomRepository;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.utils.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationUtils notificationUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ApiRespDto<?> addRoom(AddRoomReqDto addRoomReqDto, PrincipalUser principalUser) {
        if (addRoomReqDto.getUserIds().isEmpty()) {
            throw new RuntimeException("1명 이상 있어야 합니다.");
        }

        String type = addRoomReqDto.getUserIds().size() > 2 ? "GROUP" : "DM";
        Room room = Room.builder()
                .type(type)
                .build();
        Optional<Room> optionalRoom = roomRepository.addRoom(room);
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("채팅방 생성에 실패했습니다");
        }

        Integer roomId = optionalRoom.get().getRoomId();
        for (Integer userId : addRoomReqDto.getUserIds()) {
            Integer profileUserId = addRoomReqDto.getUserIds().stream().filter(id -> !id.equals(userId)).findFirst().orElse(null);
            String username = userRepository.getUserByUserId(userId).get().getUsername();
            String title = addRoomReqDto.getUsernames().stream()
                    .filter(u -> !u.equals(username)).collect(Collectors.joining(", "));
            int result = roomRepository.addRoomParticipant(addRoomReqDto.toEntity(roomId, userId, title, "MEMBER", profileUserId));
            if (result != 1) {
                throw new RuntimeException("채팅방 생성에 실패했습니다");
            }

            int readResult = roomRepository.addRoomRead(addRoomReqDto.toReadEntity(roomId, userId));
            if (readResult != 1) {
                throw new RuntimeException("채팅방 생성에 실패했습니다");
            }
        }

        return new ApiRespDto<>("success", "채팅창 생성 성공!", roomId);
    }

    public ApiRespDto<?> getRoomListByUserId(Integer userId) {
        return new ApiRespDto<>("success", "채팅방 목록 조회", roomRepository.getRoomListByUserId(userId));
    }

    @Transactional
    public ApiRespDto<?> getRoomByRoomId(Integer roomId, PrincipalUser principalUser) {
        Optional<Room> foundRoom = roomRepository.getRoomByRoomId(roomId);
        if (foundRoom.isEmpty()) {
            throw new RuntimeException("채팅방 조회 실패");
        }

        RoomRead roomRead = RoomRead.builder()
                .roomId(roomId)
                .userId(principalUser.getUserId())
                .lastReadMessageId(foundRoom.get().getLastMessageId())
                .build();

        int result = roomRepository.changeRoomRead(roomRead);
        if (result != 1) throw new RuntimeException("읽음 처리 실패");

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend(
                        "/topic/room/" + roomId,
                        Map.of(
                                "type", "read",
                                "roomId", roomId,
                                "userId", principalUser.getUserId()
                        )
                );
            }
        });

        return new ApiRespDto<>("success", "채팅방 상세 조회 완료", foundRoom.get());
    }

    @Transactional
    public ApiRespDto<?> quitRoom(QuitRoomReqDto quitRoomReqDto) {
        Optional<RoomParticipant> optionalRoomParticipant = roomRepository.getRoomParticipantByUserIdAndRoomId(quitRoomReqDto.getRoomId(), quitRoomReqDto.getUserId());
        if (optionalRoomParticipant.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        int result = roomRepository.quitRoom(quitRoomReqDto.toParticipantEntity());
        if (result != 1) {
            throw new RuntimeException("채팅방 나가기에 실패했습니다");
        }

        Optional<Message> optionalMessage = messageRepository.addMessage(quitRoomReqDto.toMessageEntity());
        if (optionalMessage.isEmpty()) {
            throw new RuntimeException("채팅방 나가기에 실패했습니다");
        }

        RoomParticipant roomParticipant = RoomParticipant.builder().roomId(quitRoomReqDto.getRoomId()).build();
        List<Integer> userIds = roomRepository.getRoomByRoomId(quitRoomReqDto.getRoomId()).get()
                .getParticipants().stream().map(RoomParticipant::getUserId).filter(id -> !id.equals(quitRoomReqDto.getUserId())).toList();
        List<String> usernames = roomRepository.getRoomByRoomId(quitRoomReqDto.getRoomId()).get()
                .getParticipants().stream().map(RoomParticipant::getUsername).filter(name -> !name.equals(quitRoomReqDto.getUsername())).toList();
        for (Integer userId : userIds) {
            String username = userRepository.getUserByUserId(userId).get().getUsername();
            String title = usernames.stream().filter(name -> !name.equals(username)).collect(Collectors.joining(", "));
            if (title.isEmpty()) {
                title = username;
            }
            roomParticipant.setUserId(userId);
            roomParticipant.setTitle(title);
            int titleResult = roomRepository.changeRoomTitle(roomParticipant);
            if (titleResult != 1) {
                throw new RuntimeException("채팅방 나가기에 실패했습니다");
            }
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend(
                        "/topic/room/" + quitRoomReqDto.getRoomId(),
                        Map.of(
                                "type", "MESSAGE",
                                "roomId", quitRoomReqDto.getRoomId(),
                                "userId", quitRoomReqDto.getUserId()
                        )
                );
            }
        });

        return new ApiRespDto<>("success", "채팅방 나기기 완료", null);
    }

    public ApiRespDto<?> changeRoomTitle(ChangeRoomTitleReqDto changeRoomTitleReqDto) {
        Optional<RoomParticipant> foundRoom = roomRepository.getRoomParticipantByUserIdAndRoomId(changeRoomTitleReqDto.getRoomId(), changeRoomTitleReqDto.getUserId());
        if (foundRoom.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        int result = roomRepository.changeRoomTitle(changeRoomTitleReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("채팅방 나가기에 실패했습니다");
        }

        return new ApiRespDto<>("success", "채팅방 제목 수정 완료", null);
    }

    @Transactional
    public ApiRespDto<?> addMessage(AddMessageReqDto addMessageReqDto) {
        Optional<Room> optionalRoom = roomRepository.getRoomByRoomId(addMessageReqDto.getRoomId());
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        Optional<Message> optionalMessage = messageRepository.addMessage(addMessageReqDto.toMessageEntity());
        if (optionalMessage.isEmpty()) {
            throw new RuntimeException("메시지 전송에 실패했습니다");
        }

        int roomResult = roomRepository.changeRoomLastMessage(addMessageReqDto.toRoomEntity(optionalMessage.get().getMessageId()));
        if (roomResult != 1) {
            throw new RuntimeException("메시지 전송에 실패했습니다.");
        }

        List<RoomParticipant> roomParticipants = optionalRoom.get().getParticipants();

        if (roomParticipants.isEmpty()) {
            throw new RuntimeException("메시지 전송에 실패했습니다");
        }

        List<Notification> notifications = new ArrayList<>();
        for (RoomParticipant participant : roomParticipants) {
            notifications.add(Notification.builder()
                    .userId(participant.getUserId())
                    .title(participant.getTitle())
                    .message(addMessageReqDto.getContent())
                    .path("/chat/room/"+addMessageReqDto.getRoomId())
                    .profileImg(userRepository.getUserByUserId(addMessageReqDto.getSenderId()).get().getProfileImg())
                    .build());
        }

        notificationUtils.sendAndAddNotification(notifications, addMessageReqDto.getRoomId());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend(
                        "/topic/room/" + optionalRoom.get().getRoomId(),
                        Map.of(
                                "type", "MESSAGE",
                                "roomId", optionalRoom.get().getRoomId(),
                                "userId", addMessageReqDto.getSenderId()
                        )
                );
            }
        });

        return new ApiRespDto<>("success", "메시지 전송 완료", null);
    }

    public ApiRespDto<?> changeMessage(ChangeMessageReqDto changeMessageReqDto) {
        Optional<Message> optionalMessage = messageRepository.getMessageByMessageId(changeMessageReqDto.getMessageId());
        if (optionalMessage.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메시지 입니다");
        }
        Message foundMessage = optionalMessage.get();

        if (foundMessage.getSenderId() == null) {
            throw new RuntimeException("이미 삭제된 메시지 입니다.");
        }

        int result = messageRepository.changeMessage(changeMessageReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("메시지 수정을 실패했습니다");
        }

        Optional<Room> optionalRoom = roomRepository.getRoomByRoomId(foundMessage.getRoomId());
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("메시지 수정을 실패했습니다");
        }

        if (optionalRoom.get().getLastMessageId().equals(foundMessage.getMessageId())) {
            int roomResult = roomRepository.changeRoomLastMessage(changeMessageReqDto.toRoomEntity(optionalMessage.get().getRoomId()));
            if (roomResult != 1) {
                throw new RuntimeException("메시지 삭제를 실패했습니다");
            }
        }

        messagingTemplate.convertAndSend(
                "/topic/room/" + foundMessage.getRoomId(),
                Map.of(
                        "type", "MESSAGE",
                        "roomId", foundMessage.getRoomId(),
                        "userId", foundMessage.getSenderId()
                )
        );

        return new ApiRespDto<>("success", "메시지 수정 완료", null);
    }

    public ApiRespDto<?> deleteMessage(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.getMessageByMessageId(messageId);
        if (optionalMessage.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메시지 입니다");
        }
        Message foundMessage = optionalMessage.get();

        if (foundMessage.getSenderId() == null) {
            throw new RuntimeException("이미 삭제된 메시지 입니다.");
        }

        Message message = Message.builder()
                .messageId(messageId)
                .senderId(null)
                .content("삭제된 메시지입니다")
                .build();
        int result = messageRepository.changeMessage(message);
        if (result != 1) {
            throw new RuntimeException("메시지 삭제를 실패했습니다");
        }

        Optional<Room> optionalRoom = roomRepository.getRoomByRoomId(foundMessage.getRoomId());
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("메시지 삭제를 실패했습니다");
        }

        if (optionalRoom.get().getLastMessageId().equals(foundMessage.getMessageId())) {
            Room room = Room.builder()
                    .roomId(foundMessage.getRoomId())
                    .lastMessage("삭제된 메시지입니다")
                    .lastMessageId(messageId)
                    .build();
            int roomResult = roomRepository.changeRoomLastMessage(room);
            if (roomResult != 1) {
                throw new RuntimeException("메시지 삭제를 실패했습니다");
            }
        }

        messagingTemplate.convertAndSend(
                "/topic/room/" + foundMessage.getRoomId(),
                Map.of(
                        "type", "MESSAGE",
                        "roomId", foundMessage.getRoomId(),
                        "userId", foundMessage.getSenderId()
                )
        );

        return new ApiRespDto<>("success", "메시지 삭제 완료", null);
    }

    @Transactional
    public ApiRespDto<?> getMessageListInfinite(MessageInfiniteParam param, PrincipalUser principalUser) {
        if (param.getCursorMessageId() != null ^ param.getCursorCreateDt() != null) {
            throw new RuntimeException("cursorMessageId와 cursorCreateDt가 모두 전달되지 않았습니다");
        }

        int lastMessageId = messageRepository.getLastMessageIdByRoomId(param.getRoomId()).orElse(0);
        if (param.getCursorMessageId() == null && param.getCursorCreateDt() == null) {
            RoomRead roomRead = RoomRead.builder()
                    .roomId(param.getRoomId())
                    .userId(principalUser.getUserId())
                    .lastReadMessageId(lastMessageId)
                    .build();
            int result = roomRepository.changeRoomRead(roomRead);
            if (result != 1) {
                throw new RuntimeException("채팅방 조회 실패");
            }
        }

        param.setLimitPlusOne(param.getLimitPlusOne());

        List<Message> rows = messageRepository.getMessageListInfinite(param);
        boolean hasNext = rows.size() > param.getLimit();
        if (hasNext) {
            rows = rows.subList(0, param.getLimit());
        }

        MessageInfiniteRespDto data = new MessageInfiniteRespDto(rows, hasNext, null, null);
        if (!rows.isEmpty()) {
            Message last = rows.get(rows.size() - 1);
            data.setNextCursorMessageId(last.getMessageId());
            data.setNextCursorCreateDt(last.getCreateDt());
        }

        return new ApiRespDto<>("success", "메시지 무한스크롤 조회 완료", data);
    }

    @Transactional
    public ApiRespDto<?> addRoomParticipant(AddRoomParticipantReqDto addRoomParticipantReqDto, PrincipalUser principalUser) {
        String profileImg = userRepository.getUserByUserId(principalUser.getUserId()).get().getProfileImg();
        for (Integer userId : addRoomParticipantReqDto.getUserIds()) {
            String username = userRepository.getUserByUserId(userId).get().getUsername();

            Optional<RoomParticipant> participant = roomRepository.getRoomParticipantByUserIdAndRoomId(addRoomParticipantReqDto.getRoomId(), userId);
            String title = addRoomParticipantReqDto.getUsernames().stream().filter(name -> !name.equals(username)).collect(Collectors.joining(", "));
            if (participant.isPresent()) {
                participant.get().setTitle(title);
                participant.get().setProfileUserId(principalUser.getUserId());
                if (participant.get().getLeftDt() == null) {
                    int titleResult = roomRepository.changeRoomTitle(participant.get());
                    if (titleResult != 1) {
                        throw new RuntimeException("초대에 실패했습니다");
                    }
                    continue;
                }
                Optional<Message> optionalMessage = messageRepository.addMessage(addRoomParticipantReqDto.toMessageEntity(username));
                if (optionalMessage.isEmpty()) {
                    throw new RuntimeException("초대에 실패했습니다");
                }
                int result = roomRepository.cancelQuitRoom(participant.get());
                if (result != 1) {
                    throw new RuntimeException("초대에 실패했습니다");
                }
                int readResult= roomRepository.changeRoomRead(addRoomParticipantReqDto.toReadEntity(userId, optionalMessage.get().getMessageId()));
                if (readResult != 1) {
                    throw new RuntimeException("초대에 실패했습니다");
                }
            } else {
                Optional<Message> optionalMessage = messageRepository.addMessage(addRoomParticipantReqDto.toMessageEntity(username));
                if (optionalMessage.isEmpty()) {
                    throw new RuntimeException("초대에 실패했습니다");
                }
                int result = roomRepository.addRoomParticipant(addRoomParticipantReqDto.toParticipantEntity(userId, principalUser.getUserId(), profileImg));
                if (result != 1) {
                    throw new RuntimeException("초대에 실패했습니다");
                }
                int readResult = roomRepository.addRoomRead(addRoomParticipantReqDto.toReadEntity(userId, optionalMessage.get().getMessageId()));
                if (readResult != 1) {
                    throw new RuntimeException("초대에 실패했습니다");
                }
            }
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend(
                        "/topic/room/" + addRoomParticipantReqDto.getRoomId(),
                        Map.of(
                                "type", "MESSAGE",
                                "roomId", addRoomParticipantReqDto.getRoomId(),
                                "userId", principalUser.getUserId()
                        )
                );
            }
        });

        return new ApiRespDto<>("success", "초대 완료", null);
    }

    public ApiRespDto<?> countUnreadChatByUserId(Integer userId) {
        return new ApiRespDto<>("success", "안읽은 채팅 갯수 조회", roomRepository.countUnreadChatByUserId(userId));
    }

    public ApiRespDto<?> muteNotification(MuteNotificationReqDto muteNotificationReqDto) {
        int result = roomRepository.muteNotification(muteNotificationReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("알림 설정 실패");
        }

        return new ApiRespDto<>("success", "알림 설정 완료", null);
    }

    public ApiRespDto<?> changeRoomFavorite(ChangeRoomFavoriteReqDto changeRoomFavoriteReqDto) {
        int result = roomRepository.changeRoomFavorite(changeRoomFavoriteReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("즐겨찾기 변경 실패");
        }

        return new ApiRespDto<>("success", "즐겨찾기 변경 완료", null);
    }
}
