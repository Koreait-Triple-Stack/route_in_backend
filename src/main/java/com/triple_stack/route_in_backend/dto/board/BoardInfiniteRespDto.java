package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class BoardInfiniteRespDto {
    private List<BoardRespDto> boardRespDtoList;
    private boolean hasNext;
//    private BoardNextCursor boardNextCursor;


}
