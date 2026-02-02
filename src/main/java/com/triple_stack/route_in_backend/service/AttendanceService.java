package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.attendance.AttendanceMonthReqDto;
import com.triple_stack.route_in_backend.repository.AttendanceRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // 로그인 할때 팝업을 띄울지 결정
    @Transactional
    public boolean autoCheckToday(PrincipalUser principalUser){
        if (principalUser == null) return false;

        Integer userId = principalUser.getUserId();
        attendanceRepository.insertToday(userId);

        Integer shown = attendanceRepository.selectPopupShownToday(userId);
        return shown == 0;
    }

    // 오늘 팝업을 봤다고 DB에 표시하는 메서드
    @Transactional
    public void markPopupShownToday(PrincipalUser principalUser) {
        if (principalUser == null) return;
        attendanceRepository.updatePopupShownToday(principalUser.getUserId());
    }

    public ApiRespDto<?> getMonthDates(String ym, PrincipalUser principalUser) {
        if(principalUser == null) throw new RuntimeException("로그인이 필요합니다.");

        AttendanceMonthReqDto attendanceMonthReqDto = new AttendanceMonthReqDto();
        attendanceMonthReqDto.setUserId(principalUser.getUserId());
        attendanceMonthReqDto.setYm(ym);

        List<String> dates = attendanceRepository.selectMonthDates(attendanceMonthReqDto);
        return new ApiRespDto<>("success", "출석 월 조회", dates);
    }
}
