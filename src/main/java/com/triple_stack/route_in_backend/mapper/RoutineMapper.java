package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Routine;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RoutineMapper {
    int addRoutine(Routine routine);
    int updateRoutine(Routine routine);
    Optional<Routine> getRoutineByUserId(Integer userId);
    Optional<Routine> getRoutineByBoardId(Integer boardId);
}
