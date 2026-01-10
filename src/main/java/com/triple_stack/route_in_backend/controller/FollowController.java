package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.user.follow.AddFollowReqDto;
import com.triple_stack.route_in_backend.dto.user.follow.DeleteFollowReqDto;
import com.triple_stack.route_in_backend.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/add")
    public ResponseEntity<?> addFollow(@RequestBody AddFollowReqDto addFollowReqDto) {
        return ResponseEntity.ok(followService.addFollow(addFollowReqDto));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFollow(@RequestBody DeleteFollowReqDto deleteFollowReqDto) {
        return ResponseEntity.ok(followService.deleteFollow(deleteFollowReqDto));
    }
}
