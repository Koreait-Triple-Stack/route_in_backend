package com.triple_stack.route_in_backend.dto.comment;

import com.triple_stack.route_in_backend.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddComment {
    private Integer boardId;
    private Integer userId;
    private Integer parentId;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .boardId(boardId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();
    }
}
