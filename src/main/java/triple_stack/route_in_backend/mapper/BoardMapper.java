package triple_stack.route_in_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import triple_stack.route_in_backend.dto.BoardRespDto;
import triple_stack.route_in_backend.entity.Board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // 추가
    int addBoard(Board board);
    // 삭제
    int removeBoard(Integer boardId);
    // 수정
    int updateBoard(Board board);
    // 목록
    List<BoardRespDto>getBoardList();
    // 키워드 검색
    List<BoardRespDto>getBoardListByKeyword(String keyword);
    // 무한 스크롤
    List<BoardRespDto> getBoardInfinite(LocalDateTime cursorCreateDt, Integer cursorBoardId, Integer limitPlusOne);
    // boardId로 단건 조회
    Optional<BoardRespDto>getBoardByBoardId(Integer boardId);
    //



}
