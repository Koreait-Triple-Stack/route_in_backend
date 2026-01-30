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

    public List<String> selectMonthDates(AttendanceMonthReqDto attendanceMonthReqDto) {
        return attendanceMapper.selectMonthDates(attendanceMonthReqDto);
    }
    public int insertToday(Integer userId) {
        return attendanceMapper.insertToday(userId);
    }
//        public int updatePopupShownToday(Integer userId) {
//        return attendanceMapper.updatePopupShownToday(userId);
//    }
    public Integer selectPopupShownToday(Integer userId) {
        return attendanceMapper.selectPopupShownToday(userId);
    }
}
