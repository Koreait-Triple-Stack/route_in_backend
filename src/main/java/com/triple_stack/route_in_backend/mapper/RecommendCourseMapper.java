package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.RecommendCourse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Optional;

@Mapper
public interface RecommendCourseMapper {
    int addCourse(RecommendCourse recommendCourse);
    Optional<RecommendCourse> getCourseByDate(LocalDate localDate);
}
