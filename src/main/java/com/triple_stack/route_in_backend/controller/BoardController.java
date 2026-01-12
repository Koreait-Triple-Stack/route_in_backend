package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.board.AddBoardReqDto;
import com.triple_stack.route_in_backend.dto.board.RemoveBoardReqDto;
import com.triple_stack.route_in_backend.dto.board.UpdateBoardReqDto;
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

// HTTP 요청을 받아서 서비스로 넘기고, 결과를 HTTP 응답으로 돌려주는 역할
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 추가
    @PostMapping("/add")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    }
    // 프론트에서 POST /board/add요청이 들어오면 addBoard()를 실행하면 1번째 파라미터인 요청 body를 읽어서 AddBoardReqDto에 필드들에 대응시켜
    // addBoardReqDto에 넣고,게시글 추가/수정/삭제같은 기능은 누가 로그인 헀는지가 중요하니까 @AuthenticationPrincipal로
    // 로그인한 사용자 정보를 principalUser에 넣는다. 그럼 Controller는 Service를 호출하는데 Service에서
    // 로그인 검증/유저 존재 확인/DB저장같은 처리로직을 수행하니까 Board Service에 addBoard메소드에 BoardController의 addBoard(파라미터)에
    // 해당하는 검증 한 결과를 ResponseEntity.ok(...)로 감싸서 HTTP응답으로 변환되어 요청한 클라이언트인 프트로 돌아간다.
    // 1. PostMapping:
    // 2. ResponseEntity<?>:
    // 3. @RequestBody: 프론트가 보낸 요청 body에 들어있는 JSON데이터를 읽어서 메서드 파라미터에 넣어준다.
    // 4. @Authentication: 현재 로그인한 사람 정보를 여기에 꽂아달라는 뜻
    // 5. ResponseEntity.ok(...):

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
    // boardService.getBoardList()를 호출해서 게시글 목록 데이터를 가져온다.

    // 1. GetMapping:


    // 무한 스크롤
    @GetMapping("/list/infinite")
    public ResponseEntity<?> getBoardInfinite(
            @RequestParam Integer limit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorCreateDt,
            @RequestParam(required = false) Integer cursorBoardId
    ) {
        return ResponseEntity.ok(boardService.getBoardInfinite(limit, cursorCreateDt, cursorBoardId));
    }
    // 1. RequestParam: URL의
    // 2. DateTimeFormat:



    // boardId로 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));
    }
    // 그러니 프론트가 GET /board/10 이렇게 요청하면 /board/{boardId} 패턴이 맞으니까 URL에서 10을 꺼내서 boardId에 넣어준다.
    //파라미터가 Integer면 Integer로 받는다.

    // 1. PathVariable: URL 경로(path)에 들어있는 값(변수)을 꺼내서 메서드 파라미터에 넣어주는 것



    // userId로 게시글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBoardListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.getBoardListByUserId(userId));
    }


    @GetMapping("/search")
    public ResponseEntity<?> getBoardListByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(boardService.getBoardListByKeyword(keyword));
    }

    // @RequestParam: @RequestParamURL 뒤에 붙는 “쿼리 파라미터(query parameter)” 값을 꺼내서 메서드 파라미터에 넣어주는 것
    // 쿼리 파라미터: ?limit=10

}

// 1. BoardController는 요청을 받기/ 파라미터 받기/ 응답 반환만 하는게 좋다.

