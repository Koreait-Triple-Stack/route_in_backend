package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.user.routine.UpdateRoutineReqDto;
import com.triple_stack.route_in_backend.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    @Autowired
    private RoutineService routineService;

    @PostMapping("/update")
    public ResponseEntity<?> updateRoutine(@RequestBody UpdateRoutineReqDto updateRoutineReqDto) {
        return ResponseEntity.ok(routineService.updateRoutine(updateRoutineReqDto));
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<?> getRoutineByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(routineService.getRoutineByUserId(userId));
    }

    @GetMapping("/get/board/{boardId}")
    public ResponseEntity<?> getRoutineByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(routineService.getRoutineByBoardId(boardId));
    }
}
