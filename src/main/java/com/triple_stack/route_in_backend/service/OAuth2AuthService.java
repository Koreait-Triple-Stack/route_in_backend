package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.entity.Routine;
import com.triple_stack.route_in_backend.repository.AddressRepository;
import com.triple_stack.route_in_backend.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.auth.SigninReqDto;
import com.triple_stack.route_in_backend.dto.user.auth.SignupReqDto;
import com.triple_stack.route_in_backend.entity.User;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.jwt.JwtUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OAuth2AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        Optional<User> foundUser = userRepository.getUserByUsername(signupReqDto.getUsername());
        if (foundUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        signupReqDto.setCurrentRun(List.of("0","0","0","0","0","0","0"));
        signupReqDto.setWeeklyRun(List.of("0","0","0","0","0","0","0"));

        Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("회원 가입에 실패했습니다.");
        }

        signupReqDto.getAddress().setUserId(optionalUser.get().getUserId());

        int addressResult = addressRepository.addAddress(signupReqDto.getAddress());
        if (addressResult != 1) {
            throw new RuntimeException("주소 추가에 실패했습니다.");
        }

        Routine routine = new Routine();
        routine.setUserId(optionalUser.get().getUserId());
        routine.setBoardId(0);
        routine.setMonday(new ArrayList<>());
        routine.setTuesday(new ArrayList<>());
        routine.setWednesday(new ArrayList<>());
        routine.setThursday(new ArrayList<>());
        routine.setFriday(new ArrayList<>());
        routine.setSaturday(new ArrayList<>());
        routine.setSunday(new ArrayList<>());
        int result = routineRepository.addRoutine(routine);
        if (result != 1) {
            throw new RuntimeException("회원 가입에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "회원가입에 성공했습니다.", null);
    }

    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> foundUser = userRepository.getUserByProviderAndProviderUserId(signinReqDto.getProvider(), signinReqDto.getProviderUserId());
        if (foundUser.isEmpty()) {
            throw new RuntimeException("로그인에 실패했습니다.");
        }

        if (!foundUser.get().isActive()) {
            throw new RuntimeException("탈퇴처리된 계정입니다.");
        }

        String accessToken = jwtUtils.generateAccessToken(foundUser.get().getUserId().toString());

        return new ApiRespDto<>("success", "로그인 성공", accessToken);
    }
}
