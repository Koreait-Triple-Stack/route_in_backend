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


        @Transactional
    // 로그인 자동 출석 기능(저장)
        public void autoCheckToday(PrincipalUser principalUser) {
            if(principalUser == null) return; // 로그인 안된 상태면 null
            attendanceRepository.insertToday(principalUser.getUserId()); // 당일 출석
        }
//    public boolean autoCheckToday(PrincipalUser principalUser){
//        if (principalUser == null) return false;
//
//        Integer userId = principalUser.getUserId();
//        attendanceRepository.insertToday(userId);
//
//        Integer shown = attendanceRepository.selectPopupShownToday(userId);
//        return shown != null && shown == 0;
//    }
//    @Transactional
//    public void markPopupShownToday(PrincipalUser principalUser) {
//        if (principalUser == null) return;
//        attendanceRepository.updatePopupShownToday(principalUser.getUserId());
//    }
    // 달력 표시(조회)
    public ApiRespDto<?> getMonthDates(String ym, PrincipalUser principalUser) {
        if(principalUser == null) throw new RuntimeException("로그인이 필요합니다."); // 로그인 필수

        AttendanceMonthReqDto attendanceMonthReqDto = new AttendanceMonthReqDto();
        attendanceMonthReqDto.setUserId(principalUser.getUserId());
        attendanceMonthReqDto.setYm(ym);

        List<String> dates = attendanceRepository.selectMonthDates(attendanceMonthReqDto);
        return new ApiRespDto<>("success", "출석 월 조회", dates);
    }
}
