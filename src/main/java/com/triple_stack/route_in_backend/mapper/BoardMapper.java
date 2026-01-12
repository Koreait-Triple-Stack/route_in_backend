package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.board.BoardRespDto;
import com.triple_stack.route_in_backend.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper

//MyBatis가 DB(SQL)와 자바 코드를 연결해주는 인터페이스이다.
public interface BoardMapper {

    // 1. 게시글 생성
    int addBoard(Board board);

    // 2. 게시글 수정
    int updateBoard(Board board);

    // 3. 게시글 삭제
    int removeBoard(Integer boardId);

    // 4. 게시글 전체 목록 조회
    List<BoardRespDto> getBoardList();

    // 5. 무한 스크롤 조회
    List<BoardRespDto> getBoardInfinite(LocalDateTime cursorCreateDt, Integer cursorBoardId, Integer limitPlusOne);

    // 6. 게시글 boardId로 상세 조회
    Optional<BoardRespDto> getBoardByBoardId(Integer boardId);

    // 7. 검색 조회
    List<BoardRespDto> getBoardListByKeyword(String keyword);

    // 8. 게시글 userId로 상세 조회
    List<BoardRespDto> getBoardListByUserId(Integer userId);
}

// 1. 반환값을 1, 0으로 받기 위해 int
// 2. 수정된 행 수를 1, 0으로 반환하기 위해 int
// 3. 삭제된 행 수를 반환하기 위해 int
// 4. 여러개 조회니까 List
// 5.
// 6. 게시글이 없을 수도 있기 때문에 Optional
// 7. 여러건 나올 수 있기 때문에 List
// 8. 한 유저가 게시글을 여러개 쓸 수 있기 때문에 List