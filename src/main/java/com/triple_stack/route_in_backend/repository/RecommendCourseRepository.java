package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.RecommendCourse;
import com.triple_stack.route_in_backend.mapper.RecommendCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class RecommendCourseRepository {
    @Autowired
    private RecommendCourseMapper recommendCourseMapper;

    public int addCourse(RecommendCourse recommendCourse) {
        return recommendCourseMapper.addCourse(recommendCourse);
    }

    public Optional<RecommendCourse> getCourseByDate(LocalDate localDate) {
        return recommendCourseMapper.getCourseByDate(localDate);
    }
}
