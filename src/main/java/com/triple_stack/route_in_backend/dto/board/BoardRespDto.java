package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class BoardRespDto {
    private Integer boardId;
    private Integer userId;

    private String type;
    private String title;
    private String content;
    private String tag;
    private String username;
    private String profileImg;
    private Integer recommendCnt;

    private LocalDateTime createDt;
    private LocalDateTime updateDt;

}
