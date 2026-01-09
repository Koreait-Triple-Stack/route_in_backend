package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.triple_stack.route_in_backend.entity.Board;
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

    public Board toEntity(Integer userId, String tagJson) {
        return Board.builder()
                .userId(userId)
                .type(type)
                .title(title)
                .content(content)
                .tag(tagJson)
                .recommendCnt(0)
                .build();
    }
}
