package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.triple_stack.route_in_backend.entity.Board;

import java.util.List;

@Data
@AllArgsConstructor
public class AddBoardReqDto {
    private String type;
    private String title;
    private String content;
    private List<String> tags;

    public Board toEntity(Integer userId, String tag) {
        return Board.builder()
                .userId(userId)
                .type(type)
                .title(title)
                .content(content)
                .tag(tag)
                .recommendCnt(0)
                .build();
    }


}
