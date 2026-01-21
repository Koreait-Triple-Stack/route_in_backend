package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.routine.AddRoutineReqDto;
import com.triple_stack.route_in_backend.dto.routine.GetRoutineReqDto;
import com.triple_stack.route_in_backend.dto.routine.RemoveRoutineReqDto;
import com.triple_stack.route_in_backend.dto.routine.UpdateRoutineByUserReqDto;
import com.triple_stack.route_in_backend.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    @Autowired
    private RoutineService routineService;

    @PostMapping("/add")
    public ResponseEntity<?> addRoutine(@RequestBody AddRoutineReqDto addRoutineReqDto) {
        return ResponseEntity.ok(routineService.addRoutine(addRoutineReqDto));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRoutine(@RequestBody UpdateRoutineByUserReqDto updateRoutineByUserReqDto) {
        return ResponseEntity.ok(routineService.updateRoutine(updateRoutineByUserReqDto));
    }

    @PostMapping("/get")
    public ResponseEntity<?> getRoutine(@RequestBody GetRoutineReqDto getRoutineReqDto) {
        return ResponseEntity.ok(routineService.getRoutine(getRoutineReqDto));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeRoutine(@RequestBody RemoveRoutineReqDto removeRoutineReqDto) {
        return ResponseEntity.ok(routineService.removeRoutine(removeRoutineReqDto));
    }

    @GetMapping("/delete/{routineId}")
    public ResponseEntity<?> deleteRoutineByRoutineId(@PathVariable Integer routineId) {
        return ResponseEntity.ok(routineService.deleteRoutineByRoutineId(routineId));
    }
}
