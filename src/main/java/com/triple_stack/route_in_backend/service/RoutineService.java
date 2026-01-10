package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.routine.UpdateRoutineReqDto;
import com.triple_stack.route_in_backend.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutineService {
    @Autowired
    private RoutineRepository routineRepository;

    public ApiRespDto<?> updateRoutine(UpdateRoutineReqDto updateRoutineReqDto) {
        int result = routineRepository.updateRoutine(updateRoutineReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("운동 루틴 수정에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "운동 루틴 수정을 완료했습니다.", null);
    }

    public ApiRespDto<?> getRoutineByUserId(Integer userId) {
        return new ApiRespDto<>("success", "운동 루틴 조회", routineRepository.getRoutineByUserId(userId));
    }

    public ApiRespDto<?> getRoutineByBoardId(Integer boardId) {
        return new ApiRespDto<>("success", "운동 루틴 조회", routineRepository.getRoutineByBoardId(boardId));
    }
}
