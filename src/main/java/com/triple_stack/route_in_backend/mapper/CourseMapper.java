package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CourseMapper {
    int addCourse(Course course);
    Optional<Course> getCourseByBoardId(Integer boardId);
    List<Course> getCourseListByUserId(Integer userId);
    Optional<Course> getCourseFavoriteByUserId(Integer userId);
    int updateCourse(Course course);
    int changeCourseFavorite(Integer userId, Integer courseId);
}
