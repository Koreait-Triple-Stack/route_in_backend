package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Routine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoutineMapper {
    int addRoutine(Routine routine);
    List<Routine> updateRoutine(Integer userId);
    List<Routine> getRoutine(Integer userId , Integer boardId);
    int removeRoutine(Routine routine);
    int deleteRoutineByRoutineId(Integer routineId);
    int changeChecked(Integer courseId);
}
