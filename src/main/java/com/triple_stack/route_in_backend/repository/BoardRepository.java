package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.board.BoardRespDto;
import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

// 서비스에서 게시글 저장/조회/수정/삭제를 할 때,Mapper를 직접 부르지 않도록 한 단계 감싸는 레이어
//

public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    // 게시글 추가
    public int addBoard(Board board) {
        return boardMapper.addBoard(board);
    }
    // 게시글을 DB에 저장하라고 시키고, 그 결과를 숫자로 돌려준다.

    // 게시글 수정
    public int updateBoard(Board board) {
        return boardMapper.updateBoard(board);
    }

    // 게시글 삭제
    public int removeBoard(Integer boardId) {
        return boardMapper.removeBoard(boardId);
    }

    // 게시글 전체 목록 조회
    public List<BoardRespDto> getBoardList() {
        return boardMapper.getBoardList();
    }

    // 무한 스크롤 조회
    public List<BoardRespDto> getBoardInfinite(LocalDateTime cursorCreateDt, Integer cursorBoardId, Integer limitPlusOne) {
        return boardMapper.getBoardInfinite(cursorCreateDt, cursorBoardId, limitPlusOne);
    }

    // 게시글 boardId로 상세 조회
    public Optional<BoardRespDto> getBoardByBoardId(Integer boardId) {
        return boardMapper.getBoardByBoardId(boardId);
    }

    // 검색 조회
    public List<BoardRespDto> getBoardListByKeyword(String keyword) {
        return boardMapper.getBoardListByKeyword(keyword);
    }

    // 게시글 userId로 상세 조회
    public List<BoardRespDto> getBoardListByUserId(Integer userId) {
        return boardMapper.getBoardListByUserId(userId);
    }
}