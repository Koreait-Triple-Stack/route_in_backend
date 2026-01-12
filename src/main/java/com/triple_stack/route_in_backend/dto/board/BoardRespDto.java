package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor

// 게시글을 조회해서 프론트로 보내줄 응답용 DTO
public class BoardRespDto {
    private Integer boardId;
    private Integer userId;

    private String type;
    private String title;
    private String content;

    private List<String> tags;
    private Integer recommendCnt;

    private String username;
    private String profileImg;

    private LocalDateTime createDt;
    private LocalDateTime updateDt;

}

