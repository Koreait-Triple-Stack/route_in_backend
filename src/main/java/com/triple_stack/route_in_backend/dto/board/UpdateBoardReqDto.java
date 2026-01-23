package com.triple_stack.route_in_backend.dto.board;


import com.triple_stack.route_in_backend.entity.Course;
import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.triple_stack.route_in_backend.entity.Board;

import java.util.List;

@Data
@AllArgsConstructor

public class UpdateBoardReqDto {
    private Integer boardId;
    private Integer userId;
    private String title;
    private String content;
    private String type;
    private List<String> tags;

    private Course course;
    private List<Routine> routines;

    public Board toEntity() {
        return Board.builder()
                .boardId(boardId)
                .tags(tags)
                .title(title)
                .content(content)
                .build();
    }





























































}
