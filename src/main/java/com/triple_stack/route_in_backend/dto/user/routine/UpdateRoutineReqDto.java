package com.triple_stack.route_in_backend.dto.user.routine;

import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateRoutineReqDto {
    private Integer routineId;
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;
    private List<String> sunday;

    public Routine toEntity() {
        return Routine.builder()
                .routineId(routineId)
                .monday(monday)
                .tuesday(tuesday)
                .wednesday(wednesday)
                .thursday(thursday)
                .friday(friday)
                .saturday(saturday)
                .sunday(sunday)
                .build();
    }
}
