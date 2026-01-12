package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

// board_tb의 컬럼(값을 담는 칸)들을 담을 수 있게 만들어준다.
public class Board {
    private Integer boardId;
    private Integer userId;

    private String type;
    private String title;
    private String content;

    private List<String> tags;

    private Integer recommendCnt;

    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}
