package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Routine;
import com.triple_stack.route_in_backend.mapper.RoutineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoutineRepository {
    @Autowired
    private RoutineMapper routineMapper;

    public int addRoutine(Routine routine) {
        return routineMapper.addRoutine(routine);
    }

    public int updateRoutine(Routine routine) {
        return routineMapper.updateRoutine(routine);
    }

    public Optional<Routine> getRoutineByUserId(Integer userId) {
        return routineMapper.getRoutineByUserId(userId);
    }

    public Optional<Routine> getRoutineByBoardId(Integer boardId) {
        return routineMapper.getRoutineByBoardId(boardId);
    }
}
