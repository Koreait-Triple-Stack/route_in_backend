package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.board.BoardInfiniteParam;
import com.triple_stack.route_in_backend.dto.board.BoardRespDto;
import com.triple_stack.route_in_backend.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper

public interface BoardMapper {
    int addBoard(Board board);
    int updateBoard(Board board);
    int removeBoard(Integer boardId);
    List<BoardRespDto> getBoardList();
    List<BoardRespDto> getBoardInfinite(BoardInfiniteParam param);
    Optional<BoardRespDto> getBoardByBoardId(Integer boardId);
    List<BoardRespDto> getBoardListByKeyword(String keyword);
    List<BoardRespDto> getBoardListByUserId(Integer userId);
    int plusRecommend(Integer boardId);
    int minusRecommend(Integer boardId);
}