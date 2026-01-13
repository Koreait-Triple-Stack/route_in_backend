package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.account.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.service.AccountService;

@RestController
@RequestMapping("/user/account")
public class UserAccountController {
    @Autowired
    private AccountService userAccountService;

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal(@AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(new ApiRespDto<>("success", "회원 조회 완료", principalUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userAccountService.getUserByUserId(userId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String username) {
        return ResponseEntity.ok(userAccountService.getUserByUsername(username));
    }

    @PostMapping("/address")
    public ResponseEntity<?> saveAddress(@RequestBody ChangeAddressReqDto changeAddressReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.saveOrUpdateAddress(changeAddressReqDto, principalUser));
    }

    @PostMapping("/change/username")
    public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameReqDto changeUsernameReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeUsername(changeUsernameReqDto, principalUser));
    }

    @PostMapping("/change/profileImg")
    public ResponseEntity<?> changeProfileImg(@RequestBody ChangeProfileImgReqDto changeProfileImgReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeProfileImg(changeProfileImgReqDto, principalUser));
    }

    @PostMapping("/change/bodyInfo")
    public ResponseEntity<?> changeHeightAndWeight(@RequestBody ChangeHeightAndWeightReqDto changeHeightAndWeightReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeHeightAndWeight(changeHeightAndWeightReqDto, principalUser));
    }

    @PostMapping("/change/currentRun")
    public ResponseEntity<?> changeCurrentRun(@RequestBody ChangeCurrentRunReqDto changeCurrentRunReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeCurrentRun(changeCurrentRunReqDto, principalUser));
    }

    @PostMapping("/change/weeklyRun")
    public ResponseEntity<?> changeWeeklyRun(@RequestBody ChangeWeeklyRunReqDto changeWeeklyRunReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeWeeklyRun(changeWeeklyRunReqDto, principalUser));
    }

    @PostMapping("withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.withdraw(principalUser));
    }
}
