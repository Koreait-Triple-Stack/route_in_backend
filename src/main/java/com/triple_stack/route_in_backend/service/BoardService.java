package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.board.*;
import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.entity.User;
import com.triple_stack.route_in_backend.repository.BoardRepository;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    // 게시글 추가
    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
        // addBoardReqDto: 프론트가 보낸 게시글 작성 데이터(type/title/content/tags/userId등)
        // principalUser: 서버가 인증해서 만든 로그인한 사용자 정보
        // addBoard는 이 둘을 검증 조합해서 게시글 저장 로직을 수행하고, 저장 결과를 ApiRespDto로 반환한다.

        if (!addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }
        // 요청한 userId와 인증된 userId와 비교해서 같으면 밑으로 다르면 잘못된 접근입니다. 출력

        Optional<User> foundUser = userRepository.getUserByUserId(addBoardReqDto.getUserId());
        if (foundUser.isEmpty()) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }
        // addBoardReqDto에서 userId를 꺼내고 이 userId로 userRepository가 DB조회를 하고 조회 결과를 foundUser에 담는다.
        // 유저가 있으면 User객체가 들어가고 유저가 없으면 비어있다. 만약 비어있으면 통과 없으면 유저가 존재하지 않습니다 출력

        List<String> tags = (addBoardReqDto.getTags() == null) ? List.of() : addBoardReqDto.getTags();
        // addBoardReqDto에 Tags가 null이면 tags에 빈 리스트를 넣고 null이 아니면 DTO에 들어있는 List<String>tags값을 tags변수에 넣는다.

        Board board = addBoardReqDto.toEntity(principalUser.getUserId(), tags);
        // addBoardReqDto안의 type/title/content같은 값 + 파라미터로 받은 userId/ tags를 합쳐서 Board객체로 만들어서 board변수에 담는다.

        int result = boardRepository.addBoard(board);
        // board를 DB에 insert하도록 boardRepository에 요청하고 결과를 result에 담는다.
        if (result != 1) {
            throw new RuntimeException("게시물 추가 실패.");
        }

        return new ApiRespDto<>("success", "게시물이 추가되었습니다.", null);
    }
    // 1. 권한 검증 -> 유저 존재 확인 -> 태그 정리 -> 엔티티 생성 -> DB저장 -> 응답 반환흐름



    // 전체 조회
    public ApiRespDto<?> getBoardList() {
        return new ApiRespDto<>("success", "게시물 전체 조회 완료", boardRepository.getBoardList());
    }



    // 무한 스크롤
    public ApiRespDto<?> getBoardInfinite(Integer limit, LocalDateTime cursorCreateDt, Integer cursorBoardId) {
        int limitPlusOne = limit + 1;

        List<BoardRespDto> rows = boardRepository.getBoardInfinite(cursorCreateDt, cursorBoardId, limitPlusOne);

        boolean hasNext = rows.size() > limit;
        if (hasNext) {
            rows = rows.subList(0, limit);
        }

        BoardNextCursor nextCursor = null;
        if (!rows.isEmpty()) {
            BoardRespDto last = rows.get(rows.size() - 1);
            nextCursor = new BoardNextCursor(last.getCreateDt(), last.getBoardId());
        }

        BoardInfiniteRespDto data = new BoardInfiniteRespDto(rows, hasNext, nextCursor);
        return new ApiRespDto<>("success", "게시물 무한스크롤 조회 완료", data);
    }
    // limit+1개를 DB에서 조회(다음 페이지 존재 여부 판단용) -> 조회 결과가 limit보다 많으면 hasNext=true로 설정하고 딱 limit개만 남김
    // 마지막 게시글의 (createDt, boardId)로 다음 커서 생성 -> rows + hasNext +nextCursor를 BoardInfiniteRespDto에 담아 성공응답 반환




    // 단건 조회
    public ApiRespDto<?> getBoardByBoardId(Integer boardId) {
        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(boardId);
        if (foundBoard.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시물입니다..");
        }
        return new ApiRespDto<>("success", "게시물 조회 완료", foundBoard.get());
    }
    // boardId로 DB에서 게시글 조회 -> 없으면 예외 -> 있으면 게시글 데이터를 담아 성공 응답 반환



    // 검색
    public ApiRespDto<?> getBoardListByKeyword(String keyword) {
        return new ApiRespDto<>("success", "게시물 검색 조회 완료", boardRepository.getBoardListByKeyword(keyword));
    }



    // 수정
    public ApiRespDto<?> updateBoard(UpdateBoardReqDto updateBoardReqDto, PrincipalUser principalUser) {
        if (!updateBoardReqDto.getUserId().equals(principalUser.getUserId())
                && !"ROLE_ADMIN".equals(principalUser.getRole())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(updateBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시물입니다.");
        }

        List<String> tags = (updateBoardReqDto.getTags() == null) ? List.of() : updateBoardReqDto.getTags();
        Board board = updateBoardReqDto.toEntity(tags);

        int result = boardRepository.updateBoard(board);
        if (result != 1) {
            throw new RuntimeException("게시물 수정 실패.");
        }

        return new ApiRespDto<>("success", "게시물 수정 완료", null);
    }
    // 권한 확인 -> 게시글 존재 확인 -> tags정리 -> DTO를 Board변환 -> DB update 실행 -> 성공 응답 반환



    // 삭제
    public ApiRespDto<?> removeBoard(RemoveBoardReqDto removeBoardReqDto, PrincipalUser principalUser) {
        if (!removeBoardReqDto.getUserId().equals(principalUser.getUserId())
                && !"ROLE_ADMIN".equals(principalUser.getRole())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(removeBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시물입니다.");
        }

        int result = boardRepository.removeBoard(removeBoardReqDto.getBoardId());
        if (result != 1) {
            throw new RuntimeException("게시물 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "게시물 삭제 완료", null);
    }
    // 권한 확인 -> 게시글 존재 확인 -> DB delete실행 -> 성공 응답 반환




    // 유저별 게시글 조회
    public ApiRespDto<?> getBoardListByUserId(Integer userId) {
        Optional<User> foundUser = userRepository.getUserByUserId(userId);
        if (foundUser.isEmpty()) {
            throw new RuntimeException("회원 정보가 존재하지 않습니다..");
        }

        return new ApiRespDto<>("success", "유저 게시물 리스트 조회 완료", boardRepository.getBoardListByUserId(userId));
    }
    // userId로 유저 존재 확인(DB조회) -> 없으면 예외 -> 있으면 해당 유저의 게시글 리스트 조회 -> 리스트를 담아 성공 응답 반환
}


// BoardService는 컨트롤러(요청)와 DB(매퍼/레포지토리) 사이에서 비즈니스 규칙을 처리하는 계층
// 1. 이 유저가 맞는지 검증, 게시글이 존재하는지 검증 , 규칙, 무한스크롤 계산 같은 규칙
// 2. “게시판이 동작하는 규칙과 흐름(검증/권한/커서 계산/기본값 처리)”