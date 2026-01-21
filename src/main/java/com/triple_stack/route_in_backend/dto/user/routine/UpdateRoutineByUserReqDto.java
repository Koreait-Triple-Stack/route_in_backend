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
    private Boolean checked;
    private Integer courseId;
}
