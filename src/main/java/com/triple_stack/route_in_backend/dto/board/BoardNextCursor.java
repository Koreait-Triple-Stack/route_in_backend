package com.triple_stack.route_in_backend.dto.board;


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
public class BoardNextCursor {
    private LocalDateTime cursorCreateDt;
    private Integer cursorBoardId;
    // private String type;
    // private Integer limitPlusOne;
    // private String region;
    // private Integer distance;
    // private List<String> tags;
}
