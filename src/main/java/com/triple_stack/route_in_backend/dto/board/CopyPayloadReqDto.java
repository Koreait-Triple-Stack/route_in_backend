package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyPayloadReqDto {
    private Integer boardId;
    private Integer userId;
    private String type;
}
