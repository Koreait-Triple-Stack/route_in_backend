package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.user.follow.AddFollowReqDto;
//import com.triple_stack.route_in_backend.dto.user.follow.ChangeFollowReqDto;
import com.triple_stack.route_in_backend.dto.user.follow.DeleteFollowReqDto;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
//    // 현재 상태 받아서 토글
//    @PostMapping("/change")
//    public ResponseEntity<?> changeFollow(@RequestBody ChangeFollowReqDto changeFollowReqDto) {
//        return ResponseEntity.ok(followService.changeFollow(changeFollowReqDto));
//    }
//    // 두 유저 간의 팔로우 여부 boolean반환
//    @GetMapping("/status")
//    public ResponseEntity<?> getFollowStatus(@RequestParam Integer followUserId, @RequestParam Integer followingUserId) {
//    return ResponseEntity.ok(followService.getFollowerStatus(followUserId, followingUserId));
//    }


    @GetMapping("/follower/{userId}")
    public ResponseEntity<?> getFollowerUserList(@PathVariable Integer userId) {
        return ResponseEntity.ok(followService.getFollowerUserList(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getFollowingUserList(@PathVariable Integer userId) {
        return ResponseEntity.ok(followService.getFollowingUserList(userId));
    }
}
