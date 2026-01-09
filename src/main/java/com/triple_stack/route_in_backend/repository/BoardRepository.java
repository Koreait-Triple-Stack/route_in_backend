package com.triple_stack.route_in_backend.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.triple_stack.route_in_backend.dto.board.BoardRespDto;
import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.mapper.BoardMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;
    // 추가 *
    public int addBoard(Board board){
        return boardMapper.addBoard(board);
    }
    // 제거
    public int removeBoard(Integer boardId) {
        return boardMapper.removeBoard(boardId);
    }
    // 수정
    public int updateBoard(Board board) {
        return boardMapper.updateBoard(board);
    }
    // 게시글 목록
    public List<BoardRespDto> getBoardList() {
        return boardMapper.getBoardList();
    }
    // 키워드 검색
    public List<BoardRespDto> getBoardListByKeyword(String keyword) {
        return boardMapper.getBoardListByKeyword(keyword);
    }
    // boardId로 단건 조회
    public Optional<BoardRespDto> getBoardByBoardId(Integer boardId) {
        return boardMapper.getBoardByBoardId(boardId);
    }
    // 무한 스크롤
    public List<BoardRespDto> getBoardInfinite(LocalDateTime cursorCreateDt, Integer cursorBoardId, Integer limitPlusOne) {
        return boardMapper.getBoardInfinite(cursorCreateDt, cursorBoardId, limitPlusOne);
    }

}
