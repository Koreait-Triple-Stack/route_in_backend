package com.triple_stack.route_in_backend.dto.user.routine;

import com.triple_stack.route_in_backend.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateRoutineByUserReqDto {
    private List<Routine> addRoutines;
    private List<Integer> deleteIds;
//    private Integer userId;
//    private String weekday;
//    private String exercise;
//    private Boolean checked;
//
//    public Routine toEntity() {
//        return Routine.builder()
//                .userId(userId)
//                .weekday(weekday)
//                .exercise(exercise)
//                .checked(checked)
//                .build();
//    }
}
