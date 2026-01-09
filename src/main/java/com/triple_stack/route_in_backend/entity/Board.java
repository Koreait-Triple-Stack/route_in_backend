package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
    private Integer boardId;
    private Integer userId;

    private String type;
    private String title;
    private String content;
    private String tag;

    private Integer recommendCnt;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;



}
