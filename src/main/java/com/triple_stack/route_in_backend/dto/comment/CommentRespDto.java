package com.triple_stack.route_in_backend.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRespDto {
    private Integer commentId;
    private Integer parentId;
    private Integer userId;
    private String username;
    private String profileImg;
    private String content;
    private Boolean isDeleted;
    private LocalDateTime createDt;

    private List<CommentRespDto> commentRespDtoList = new ArrayList<>();
}
