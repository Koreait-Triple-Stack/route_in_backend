package com.triple_stack.route_in_backend.dto.user.routine;

import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateRoutineReqDto {
    private Integer routineId;
    private Integer userId;
    private String weekday;
    private String exercise;
    private Boolean checked;

    public Routine toEntity() {
        return Routine.builder()
                .routineId(routineId)
                .userId(userId)
                .weekday(weekday)
                .exercise(exercise)
                .checked(checked)
                .build();
    }
}
