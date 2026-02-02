package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.attendance.AttendanceMonthReqDto;
import com.triple_stack.route_in_backend.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendanceRepository {

    @Autowired
    private AttendanceMapper attendanceMapper;

    // 특정 월에 출석한 날짜 가져오기
    public List<String> selectMonthDates(AttendanceMonthReqDto attendanceMonthReqDto) {
        return attendanceMapper.selectMonthDates(attendanceMonthReqDto);
    }
    // 당일 출석 row없으면 생성
    public int insertToday(Integer userId) {
        return attendanceMapper.insertToday(userId);
    }
    // 팝업 봤다고 표시
        public int updatePopupShownToday(Integer userId) {
        return attendanceMapper.updatePopupShownToday(userId);
    }
    // 당일 popup_shown값 조회하기
    public Integer selectPopupShownToday(Integer userId) {
        return attendanceMapper.selectPopupShownToday(userId);
    }
}
