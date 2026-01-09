package com.triple_stack.route_in_backend.controller.user;

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

//    @GetMapping("/userId")
//    public ResponseEntity<?> getPrincipal(@AuthenticationPrincipal PrincipalUser principalUser) {
//        return ResponseEntity.ok(new ApiRespDto<>("success", "회원 조회 완료", principalUser));
//    }

    @PostMapping("/change/username")
    public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameReqDto changeUsernameReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeUsername(changeUsernameReqDto, principalUser));
    }

    @PostMapping("/change/profileImg")
    public ResponseEntity<?> changeProfileImg(@RequestBody ChangeProfileImgReqDto changeProfileImgReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeProfileImg(changeProfileImgReqDto, principalUser));
    }

    @PostMapping("/change/address")
    public ResponseEntity<?> changeAddress(@RequestBody ChangeAddressReqDto changeAddressReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeAddress(changeAddressReqDto, principalUser));
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
