package com.triple_stack.route_in_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.triple_stack.route_in_backend.dto.board.AddBoardReqDto;
import com.triple_stack.route_in_backend.dto.board.RemoveBoardReqDto;
import com.triple_stack.route_in_backend.dto.board.UpdateBoardReqDto;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.BoardService;

@RestController
@RequestMapping("/board")

public class BoardController {
    @Autowired
    private BoardService boardService;

    // 추가
    @PostMapping("/add")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    }

    // 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateBoard(@RequestBody UpdateBoardReqDto updateBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.updateBoard(updateBoardReqDto, principalUser));
    }

    // 삭제
    @PostMapping("/remove")
    public ResponseEntity<?> removeBoard(@RequestBody RemoveBoardReqDto removeBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.removeBoard(removeBoardReqDto, principalUser));
    }

    // 게시글 목록
    @GetMapping("/list")
    public ResponseEntity<?> getBoardList() {
        return ResponseEntity.ok(boardService.getBoardList());
    }
    // 키워드 검색

    // 무한 스크롤
//    @GetMapping("/list/infinite")
//    public ResponseEntity<?> getBoardInfinite(
//            @RequestParam Integer limit,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorCreateDt,
//            @RequestParam(required = false) Integer cursorBoardId
//    ) {
//        return ResponseEntity.ok(boardService.getBoardInfinite(limit, cursorCreateDt, cursorBoardId));
//    }
    // boardId로 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));   }

    // userId로 게시글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBoardListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.getBoardListByUserId(userId));
    }


}