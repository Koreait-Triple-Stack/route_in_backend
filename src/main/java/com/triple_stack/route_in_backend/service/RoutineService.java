package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.routine.AddRoutineReqDto;
import com.triple_stack.route_in_backend.dto.user.routine.GetRoutineReqDto;
import com.triple_stack.route_in_backend.dto.user.routine.RemoveRoutineReqDto;
import com.triple_stack.route_in_backend.dto.user.routine.UpdateRoutineReqDto;
import com.triple_stack.route_in_backend.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutineService {
    @Autowired
    private RoutineRepository routineRepository;

    public ApiRespDto<?> addRoutine(AddRoutineReqDto addRoutineReqDto) {
        int result = routineRepository.addRoutine(addRoutineReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("운동 루틴 추가에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "운동 루틴 추가를 완료했습니다.", null);
    }

    public ApiRespDto<?> updateRoutine(UpdateRoutineReqDto updateRoutineReqDto) {
        int result = routineRepository.updateRoutine(updateRoutineReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("운동 루틴 수정에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "운동 루틴 수정을 완료했습니다.", null);
    }

    public ApiRespDto<?> getRoutine(GetRoutineReqDto getRoutineReqDto) {
        if (getRoutineReqDto.getUserId() == null && getRoutineReqDto.getBoardId() == null) {
            throw new RuntimeException("운동 루틴 조회에 실패했습니다.");
        }
        return new ApiRespDto<>("success", "운동 루틴 조회", routineRepository.getRoutine(getRoutineReqDto.getUserId(), getRoutineReqDto.getBoardId()));
    }

    public ApiRespDto<?> removeRoutine(RemoveRoutineReqDto removeRoutineReqDto) {
        int result = routineRepository.removeRoutine(removeRoutineReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("운동 루틴 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "운동 루틴 삭제를 완료했습니다.", null);
    }

    public ApiRespDto<?> deleteRoutineByRoutineId(Integer routineId) {
        int result = routineRepository.deleteRoutineByRoutineId(routineId);
        if (result != 1) {
            throw new RuntimeException("운동 루틴 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "운동 루틴 삭제를 완료했습니다.", null);
    }
}
