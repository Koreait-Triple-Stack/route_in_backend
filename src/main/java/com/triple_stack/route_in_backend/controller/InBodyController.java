package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.user.InBody.AddInBodyReqDto;
import com.triple_stack.route_in_backend.dto.user.InBody.DeleteInBodyReqDto;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.InBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inbody")
public class InBodyController {
    @Autowired
    private InBodyService inBodyService;

    @PostMapping("/add")
    public ResponseEntity<?> addInBody(@RequestBody AddInBodyReqDto addInBodyReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(inBodyService.addInBody(addInBodyReqDto, principalUser));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteInBody(@RequestBody DeleteInBodyReqDto deleteInBodyReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(inBodyService.deleteInBody(deleteInBodyReqDto, principalUser));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getInBodyListByUserId(@PathVariable Integer userId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(inBodyService.getInBodyListByUserId(userId, principalUser));
    }
}
