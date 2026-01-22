package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.board.*;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/add")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBoard(@RequestBody UpdateBoardReqDto updateBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.updateBoard(updateBoardReqDto, principalUser));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeBoard(@RequestBody RemoveBoardReqDto removeBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.removeBoard(removeBoardReqDto, principalUser));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList() {
        return ResponseEntity.ok(boardService.getBoardList());
    }

    @GetMapping("/list/infinite")
    public ResponseEntity<?> getBoardInfinite(BoardInfiniteParam boardInfiniteParam) {
        return ResponseEntity.ok(boardService.getBoardInfinite(boardInfiniteParam));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBoardListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.getBoardListByUserId(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getBoardListByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(boardService.getBoardListByKeyword(keyword));
    }

    @PostMapping("/change/recommend")
    public ResponseEntity<?> changeRecommend(@RequestBody ChangeRecommendReqDto changeRecommendReqDto) {
        return ResponseEntity.ok(boardService.changeRecommend(changeRecommendReqDto));
    }

    @GetMapping("/recommend/{boardId}")
    public ResponseEntity<?> getRecommendListByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getRecommendListByBoardId(boardId));
    }

    @PostMapping("/copy/payload")
    public ResponseEntity<?> copyCourse(@RequestBody CopyPayloadReqDto copyPayloadReqDto) {
        return ResponseEntity.ok(boardService.copyPayload(copyPayloadReqDto));
    }
}

