package com.triple_stack.route_in_backend.dto.board;


import lombok.AllArgsConstructor;
import lombok.Data;
import com.triple_stack.route_in_backend.entity.Board;

import java.util.List;

@Data
@AllArgsConstructor

public class UpdateBoardReqDto {
    private String title;
    private List<String> tags;
    private String content;
    private Integer boardId;
    private Integer userId;
    private Integer recommendCnt;

    public Board toEntity(List<String> tags) {
        return Board.builder()
                .boardId(boardId)
                .tags(this.tags)
                .title(title)
                .content(content)
                .recommendCnt(recommendCnt)
                .build();
    }





























































}
