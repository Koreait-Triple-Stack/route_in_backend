package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.attendance.AttendanceMonthReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    // 금일 출석 기록
    int insertToday(Integer userId); // 1이면 신규 0이면 이미 출석 완료
    int updatePopupShownToday(Integer userId);// 1이면 팝업 0이면 이미 팝업 띄움
    List<String> selectMonthDates(AttendanceMonthReqDto attendanceMonthReqDto); // 각 유저의 출석한 날짜 기록
    Integer selectPopupShownToday(Integer userId);
}
