package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.mapper.AttendanceMapper;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/month")
    public ResponseEntity<?> getMonth(@RequestParam String ym, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(attendanceService.getMonthDates(ym, principalUser));
    }
}
