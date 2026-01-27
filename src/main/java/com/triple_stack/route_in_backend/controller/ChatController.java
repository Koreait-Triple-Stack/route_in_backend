package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.chat.*;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/room/add")
    private ResponseEntity<?> addRoom(@RequestBody AddRoomReqDto addRoomReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(chatService.addRoom(addRoomReqDto, principalUser));
    }

    @GetMapping("/room/list/{userId}")
    private ResponseEntity<?> getRoomListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(chatService.getRoomListByUserId(userId));
    }

    @GetMapping("/room/{roomId}")
    private ResponseEntity<?> getRoomByRoomId(@PathVariable Integer roomId) {
        return ResponseEntity.ok(chatService.getRoomByRoomId(roomId));
    }

    @PostMapping("/room/quit")
    private ResponseEntity<?> quitRoom(@RequestBody QuitRoomReqDto quitRoomReqDto) {
        return ResponseEntity.ok(chatService.quitRoom(quitRoomReqDto));
    }

    @PostMapping("/room/change/title")
    private ResponseEntity<?> changeRoomTitle(@RequestBody ChangeRoomTitleReqDto changeRoomTitleReqDto) {
        return ResponseEntity.ok(chatService.changeRoomTitle(changeRoomTitleReqDto));
    }

    @PostMapping("/message/add")
    private ResponseEntity<?> addMessage(@RequestBody AddMessageReqDto addMessageReqDto) {
        return ResponseEntity.ok(chatService.addMessage(addMessageReqDto));
    }

    @PostMapping("/message/change")
    private ResponseEntity<?> changeMessage(@RequestBody ChangeMessageReqDto changeMessageReqDto) {
        return ResponseEntity.ok(chatService.changeMessage(changeMessageReqDto));
    }

    @PostMapping("/message/delete/{messageId}")
    private ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        return ResponseEntity.ok(chatService.deleteMessage(messageId));
    }

    @GetMapping("/message/list")
    private ResponseEntity<?> getMessageListInfinite(MessageInfiniteParam messageInfiniteParam) {
        return ResponseEntity.ok(chatService.getMessageListInfinite(messageInfiniteParam));
    }
}
