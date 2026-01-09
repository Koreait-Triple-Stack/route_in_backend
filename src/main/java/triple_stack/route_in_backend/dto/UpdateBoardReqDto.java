package triple_stack.route_in_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import triple_stack.route_in_backend.entity.Board;

@Data
@AllArgsConstructor

public class UpdateBoardReqDto {
    private String title;
    private String tag;
    private String content;
    private Integer boardId;
    private Integer userId;
    private Integer recommendCnt;

    public Board toEntity() {
        return Board.builder()
                .boardId(boardId)
                .tag(tag)
                .title(title)
                .content(content)
                .recommendCnt(recommendCnt)
                .build();
    }





























































}
