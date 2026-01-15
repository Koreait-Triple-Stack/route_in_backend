package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.CoursePoint;
import com.triple_stack.route_in_backend.mapper.CoursePointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoursePointRepository {
    @Autowired
    private CoursePointMapper coursePointMapper;

    public int addCoursePoint(CoursePoint coursePoint) {
        return coursePointMapper.addCoursePoint(coursePoint);
    }

    public int deleteCoursePoint(Integer courseId) {
        return coursePointMapper.deleteCoursePoint(courseId);
    }

    public List<CoursePoint> getCoursePointList(Integer courseId) {
        return coursePointMapper.getCoursePointList(courseId);
    }
}
