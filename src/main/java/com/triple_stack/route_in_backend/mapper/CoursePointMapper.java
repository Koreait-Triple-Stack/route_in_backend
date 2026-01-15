package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.CoursePoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursePointMapper {
    int addCoursePoint(CoursePoint coursePoint);
    int deleteCoursePoint(Integer courseId);
    List<CoursePoint> getCoursePointList(Integer courseId);
}
