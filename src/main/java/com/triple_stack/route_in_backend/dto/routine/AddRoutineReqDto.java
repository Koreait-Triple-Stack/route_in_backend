package com.triple_stack.route_in_backend.dto.routine;

import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddRoutineReqDto {
    private Integer userId;
    private String weekday;
    private String exercise;
    private Boolean checked;

    public Routine toEntity() {
        return Routine.builder()
                .userId(userId)
                .weekday(weekday)
                .exercise(exercise)
                .checked(checked)
                .build();
    }
}
