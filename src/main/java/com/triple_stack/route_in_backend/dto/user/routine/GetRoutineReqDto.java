package com.triple_stack.route_in_backend.dto.user.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetRoutineReqDto {
    private Integer userId;
    private Integer boardId;
}
