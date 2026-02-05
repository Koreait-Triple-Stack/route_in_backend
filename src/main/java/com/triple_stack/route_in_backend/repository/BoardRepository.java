package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.board.BoardDetailRespDto;
import com.triple_stack.route_in_backend.dto.board.BoardInfiniteParam;
import com.triple_stack.route_in_backend.dto.board.BoardRespDto;
import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    public Optional<Board> addBoard(Board board) {
        try {
            int result = boardMapper.addBoard(board);
            if (result != 1) {
                throw new RuntimeException("게시물 추가 실패");
            }
            return Optional.of(board);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int updateBoard(Board board) {
        return boardMapper.updateBoard(board);
    }

    public int removeBoard(Integer boardId) {
        return boardMapper.removeBoard(boardId);
    }

    public List<BoardRespDto> getBoardList() {
        return boardMapper.getBoardList();
    }

    public List<Board> getBoardByRecommendCnt() {
        return boardMapper.getBoardByRecommendCnt();
    }

    public List<BoardRespDto> getBoardInfinite(BoardInfiniteParam param) {
        return boardMapper.getBoardInfinite(param);
    }

    public Optional<BoardDetailRespDto> getBoardByBoardId(Integer boardId) {
        return boardMapper.getBoardByBoardId(boardId);
    }

    public List<BoardRespDto> getBoardListByKeyword(String keyword) {
        return boardMapper.getBoardListByKeyword(keyword);
    }

    public List<BoardRespDto> getBoardListByUserId(Integer userId) {
        return boardMapper.getBoardListByUserId(userId);
    }

    public int plusRecommend(Integer boardId) {
        return boardMapper.plusRecommend(boardId);
    }

    public int minusRecommend(Integer boardId) {
        return boardMapper.minusRecommend(boardId);
    }
}