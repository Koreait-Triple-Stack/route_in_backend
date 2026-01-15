package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.course.AddCourseReqDto;
import com.triple_stack.route_in_backend.dto.course.UpdateCourseReqDto;
import com.triple_stack.route_in_backend.entity.Course;
import com.triple_stack.route_in_backend.entity.CoursePoint;
import com.triple_stack.route_in_backend.repository.CoursePointRepository;
import com.triple_stack.route_in_backend.repository.CourseRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CoursePointRepository coursePointRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public ApiRespDto<?> addCourse(AddCourseReqDto addCourseReqDto, PrincipalUser principalUser) {
        if (addCourseReqDto.getUserId() != null && !addCourseReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<Course> optionalCourse = courseRepository.addCourse(addCourseReqDto.toEntity());
        if (optionalCourse.isEmpty()) {
            throw new RuntimeException("러닝 코스 추가에 실패했습니다.");
        }

        Integer courseId = optionalCourse.get().getCourseId();
        for (CoursePoint point : addCourseReqDto.getPoints()) {
            point.setCourseId(courseId);
            int result = coursePointRepository.addCoursePoint(point);
            if (result != 1) {
                throw new RuntimeException("러닝 코스 추가에 실패했습니다.");
            }
        }

        return new ApiRespDto<>("success", "러닝 코스 추가를 완료했습니다", null);
    }

    public ApiRespDto<?> getCourseByBoardId(Integer boardId) {
        Optional<Course> foundCourse = courseRepository.getCourseByBoardId(boardId);
        if (foundCourse.isEmpty()) {
            throw new RuntimeException("러닝 코스 조회에 실패했습니다");
        }

        Course course = foundCourse.get();
        List<CoursePoint> points = coursePointRepository.getCoursePointList(course.getCourseId());
        if (points.size() < 2) {
            throw new RuntimeException("러닝 코스 조회에 실패했습니다.");
        }
        course.setPoints(points);

        return new ApiRespDto<>("success", "러닝 코스 조회를 완료했습니다.", course);
    }

    public ApiRespDto<?> getCourseListByUserId(Integer userId, PrincipalUser principalUser) {
        if (!userId.equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        List<Course> courseList = courseRepository.getCourseListByUserId(userId);
        if (courseList.isEmpty()) {
            throw new RuntimeException("러닝 코스 조회에 실패했습니다");
        }

        for (Course course : courseList) {
            List<CoursePoint> points = coursePointRepository.getCoursePointList(course.getCourseId());
            if (points.size() < 2) {
                throw new RuntimeException("러닝 코스 조회에 실패했습니다.");
            }
            course.setPoints(points);
        }

        return new ApiRespDto<>("success", "러닝 코스 조회를 완료했습니다.", courseList);
    }

    public ApiRespDto<?> getCourseFavoriteByUserId(Integer userId, PrincipalUser principalUser) {
        if (!userId.equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<Course> foundCourse = courseRepository.getCourseFavoriteByUserId(userId);
        if (foundCourse.isEmpty()) {
            System.out.println(11);
            throw new RuntimeException("러닝 코스 조회에 실패했습니다");
        }

        Course course = foundCourse.get();
        List<CoursePoint> points = coursePointRepository.getCoursePointList(course.getCourseId());
        if (points.size() < 2) {
            throw new RuntimeException("러닝 코스 조회에 실패했습니다.");
        }
        course.setPoints(points);

        return new ApiRespDto<>("success", "러닝 코스 조회를 완료했습니다.", course);
    }

    @Transactional
    public ApiRespDto<?> updateCourse(UpdateCourseReqDto updateCourseReqDto, PrincipalUser principalUser) {
        if (updateCourseReqDto.getUserId() != null && !updateCourseReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }
        int courseResult = courseRepository.updateCourse(updateCourseReqDto.toEntity());
        if (courseResult != 1) {
            throw new RuntimeException("러닝 코스 수정에 실패했습니다.");
        }

        coursePointRepository.deleteCoursePoint(updateCourseReqDto.getCourseId());
        for (CoursePoint point : updateCourseReqDto.getPoints()) {
            int result = coursePointRepository.addCoursePoint(point);
            if (result != 1) {
                throw new RuntimeException("러닝 코스 수정에 실패했습니다");
            }
        }

        return new ApiRespDto<>("success", "러닝 코스 수정을 완료했습니다.", null);
    }
}
