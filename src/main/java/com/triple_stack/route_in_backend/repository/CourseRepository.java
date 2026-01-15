package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Course;
import com.triple_stack.route_in_backend.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    @Autowired
    private CourseMapper courseMapper;

    public Optional<Course> addCourse(Course course) {
        try {
            int result =  courseMapper.addCourse(course);
            if (result != 1) {
                throw new RuntimeException("러닝 코스 추가에 실패했습니다.");
            }
        } catch (RuntimeException e) {
            return Optional.empty();
        }
        return Optional.of(course);
    }

    public Optional<Course> getCourseByBoardId(Integer boardId){
        return courseMapper.getCourseByBoardId(boardId);
    }

    public List<Course> getCourseListByUserId(Integer userId) {
        return courseMapper.getCourseListByUserId(userId);
    }

    public Optional<Course> getCourseFavoriteByUserId(Integer userId) {
        return courseMapper.getCourseFavoriteByUserId(userId);
    }

    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    public int changeCourseFavorite(Integer userId, Integer courseId) {
        return courseMapper.changeCourseFavorite(userId, courseId);
    }
}
