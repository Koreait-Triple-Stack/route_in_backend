package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Routine;
import com.triple_stack.route_in_backend.mapper.RoutineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<Routine> getRoutine(Integer userId, Integer boardId) {
        return routineMapper.getRoutine(userId, boardId);
    }

    public int removeRoutine(Routine routine) { return routineMapper.removeRoutine(routine); }

    public int deleteRoutineByRoutineId(Integer routineId) { return routineMapper.deleteRoutineByRoutineId(routineId); }
}
