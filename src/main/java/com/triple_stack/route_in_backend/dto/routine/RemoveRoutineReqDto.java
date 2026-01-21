package com.triple_stack.route_in_backend.dto.routine;

import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RemoveRoutineReqDto {
    private Integer userId;
    private String weekday;

    public Routine toEntity() {
        return Routine.builder()
                .userId(userId)
                .weekday(weekday)
                .build();
    }
}
