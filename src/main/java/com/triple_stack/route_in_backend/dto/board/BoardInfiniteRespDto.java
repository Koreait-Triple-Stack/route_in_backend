package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInfiniteRespDto {
    private List<BoardRespDto> boardRespDtoList;
    private boolean hasNext;
    private Integer nextCursorBoardId;
    private LocalDateTime nextCursorCreateDt;
}
