package com.triple_stack.route_in_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
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

    // 게시글 추가
    // @PostMapping("/add")
    // public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
    //     return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    // }
    //
    // // 게시글 수정
    // @PostMapping("/update")
    // public ResponseEntity<?> updateBoard(@RequestBody UpdateBoardReqDto updateBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
    //     return ResponseEntity.ok(boardService.updateBoard(updateBoardReqDto, principalUser));
    // }
    //
    // // 게시글 삭제
    // @PostMapping("/remove")
    // public ResponseEntity<?> removeBoard(@RequestBody RemoveBoardReqDto removeBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
    //     return ResponseEntity.ok(boardService.removeBoard(removeBoardReqDto, principalUser));
    // }
}

//    @GetMapping("/{boardId}")
//    public ResponseEntity<?> getBoardByBoardId(@PathVariable Integer boardId) {
//        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));
//    }