package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.InBody.AddInBodyReqDto;
import com.triple_stack.route_in_backend.dto.user.InBody.DeleteInBodyReqDto;
import com.triple_stack.route_in_backend.entity.InBody;
import com.triple_stack.route_in_backend.repository.InBodyRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InBodyService {
    @Autowired
    private InBodyRepository inBodyRepository;

    public ApiRespDto<?> addInBody(AddInBodyReqDto addInBodyReqDto, PrincipalUser principalUser) {
        if (!addInBodyReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<InBody> foundInBody = inBodyRepository.getInBodyByUserIdAndMonthDt(addInBodyReqDto.getUserId(), addInBodyReqDto.getMonthDt());
        if (foundInBody.isPresent()) {
            throw new RuntimeException("이미 인바디를 등록한 달입니다.");
        }

        int result = inBodyRepository.addInBody(addInBodyReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("인바디 등록에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "인바디 등록에 성공했습니다.", null);
    }

    public ApiRespDto<?> deleteInBody(DeleteInBodyReqDto deleteInBodyReqDto, PrincipalUser principalUser) {
        if (!deleteInBodyReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<InBody> foundInBody = inBodyRepository.getInBodyByInBodyId(deleteInBodyReqDto.getInBodyId());
        if (foundInBody.isEmpty()) {
            throw new RuntimeException("존재하지 않는 인바디입니다.");
        }

        int result = inBodyRepository.deleteInBody(deleteInBodyReqDto.getInBodyId());
        if (result != 1) {
            throw new RuntimeException("인바디 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "인바디 삭제에 성공했습니다.", null);
    }

    public ApiRespDto<?> getInBodyListByUserId(Integer userId, PrincipalUser principalUser) {
        if (!userId.equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        return new ApiRespDto<>("success", "인바디 리스트 조회 완료", inBodyRepository.getInBodyListByUserId(userId));
    }
}

