package com.triple_stack.route_in_backend.dto.board;

import com.triple_stack.route_in_backend.entity.Board;
import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBoardReqDto {
    private Integer userId;
    private String type;
    private String title;
    private String content;
    private List<String> tags;

    private Routine routine;

    public Board toEntity(Integer userId, List<String> tags) {
        return Board.builder()
                .userId(userId)
                .type(type)
                .title(title)
                .content(content)
                .tags(tags)
                .recommendCnt(0)
                .build();
    }
}
