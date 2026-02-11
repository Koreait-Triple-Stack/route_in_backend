package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.course.AddCourseReqDto;
import com.triple_stack.route_in_backend.dto.course.ChangeCourseFavoriteReqDto;
import com.triple_stack.route_in_backend.dto.course.UpdateCourseReqDto;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody AddCourseReqDto addCourseReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(courseService.addCourse(addCourseReqDto, principalUser));
    }

    @GetMapping("/get/board/{boardId}")
    public ResponseEntity<?> getCourseByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(courseService.getCourseByBoardId(boardId));
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<?> getCourseListByUserId(@PathVariable Integer userId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(courseService.getCourseListByUserId(userId, principalUser));
    }

    @GetMapping("/get/favorite/{userId}")
    public ResponseEntity<?> getCourseFavoriteByUserId(@PathVariable Integer userId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(courseService.getCourseFavoriteByUserId(userId, principalUser));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCourse(@RequestBody UpdateCourseReqDto updateCourseReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(courseService.updateCourse(updateCourseReqDto, principalUser));
    }

    @PostMapping("/change/favorite")
    public ResponseEntity<?> changeCourseFavorite(@RequestBody ChangeCourseFavoriteReqDto changeCourseFavoriteReqDto) {
        return ResponseEntity.ok(courseService.changeCourseFavorite(changeCourseFavoriteReqDto));
    }

    @GetMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseService.deleteCourse(courseId));
    }
}
