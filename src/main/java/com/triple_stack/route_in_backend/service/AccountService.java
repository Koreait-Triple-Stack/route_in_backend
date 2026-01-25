package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.user.account.*;
import com.triple_stack.route_in_backend.entity.Address;
import com.triple_stack.route_in_backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.entity.User;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    public ApiRespDto<?> getUserByUserId(Integer userId) {
        Optional<User> foundUser = userRepository.getUserByUserId(userId);
        if (foundUser.isEmpty()) {
            throw new RuntimeException("해당 유저가 존재하지 않습니다.");
        }

        return new ApiRespDto<>("success", "유저 조회 완료", foundUser.get());
    }

    public ApiRespDto<?> getUserByUsername(String username) {
        Optional<User> foundUser = userRepository.getUserByUsername(username);
        if (foundUser.isEmpty()) {
            throw new RuntimeException("해당 닉네임이 존재하지 않습니다.");
        }

        return new ApiRespDto<>("success", "유저 조회 완료", foundUser.get());
    }

    public ApiRespDto<?> isDuplicatedUsername(String username) {
        return new ApiRespDto<>("success", "중복 조회 완료", userRepository.getUserByUsername(username).isPresent());
    }

    public ApiRespDto<?> changeUsername(ChangeUsernameReqDto changeUsernameReqDto, PrincipalUser principalUser) {
        if (!changeUsernameReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<User> optionalUser = userRepository.getUserByUserId(changeUsernameReqDto.getUserId());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("존재하지 않은 회원정보입니다.");
        }
        Optional<User> foundUser = userRepository.getUserByUsername(changeUsernameReqDto.getUsername());
        if (foundUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자 이름입니다.");
        }
        User user = optionalUser.get();
        user.setUsername(changeUsernameReqDto.getUsername());

        int result = userRepository.changeUsername(user);
        if(result != 1) {
            throw new RuntimeException("사용자 이름 변경에 실패했습니다. 다시 시도해 주세요");
        }
        return new ApiRespDto<>("success", "사용자 이름이 변경되었습니다.", null);
    }

    public ApiRespDto<?> changeProfileImg(ChangeProfileImgReqDto changeProfileImgReqDto, PrincipalUser principalUser) {
        if (!changeProfileImgReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<User> optionalUser = userRepository.getUserByUserId(changeProfileImgReqDto.getUserId());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("존재하지 않은 회원정보입니다.");
        }

        User user = optionalUser.get();
        user.setProfileImg(changeProfileImgReqDto.getProfileImg());

        int result = userRepository.changeProfileImg(user);
        if(result != 1) {
            throw new RuntimeException("사용자 프로필 이미지 변경에 실패했습니다. 다시 시도해 주세요");
        }
        return new ApiRespDto<>("success", "사용자 프로필 이미지가 변경되었습니다.", null);
    }

    public ApiRespDto<?> saveOrUpdateAddress(ChangeAddressReqDto changeAddressReqDto, PrincipalUser principalUser) {
        if (changeAddressReqDto.getUserId() != principalUser.getUserId()) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<Address> addressOptional = addressRepository.getAddressByUserId(changeAddressReqDto.getUserId());

        Address address = Address.builder()
                .userId(changeAddressReqDto.getUserId())
                .zipCode(changeAddressReqDto.getZipCode())
                .baseAddress(changeAddressReqDto.getBaseAddress())
                .detailAddress(changeAddressReqDto.getDetailAddress())
                .build();

        int result;
        String message;

        if (addressOptional.isEmpty()) {
            result = addressRepository.addAddress(address);
            message = "주소 정보가 새롭게 등록되었습니다.";
        } else {
            result = addressRepository.changeAddress(address);
            message = "주소 정보가 성공적으로 수정되었습니다.";
        }

        if (result != 1) {
            throw new RuntimeException("주소 정보 저장 중 오류가 발생했습니다.");
        }

        return new ApiRespDto<>("success", message, null);
    }

    public ApiRespDto<?> changeHeightAndWeight(ChangeHeightAndWeightReqDto changeHeightAndWeightReqDto, PrincipalUser principalUser) {
        if (!changeHeightAndWeightReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<User> optionalUser = userRepository.getUserByUserId(changeHeightAndWeightReqDto.getUserId());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("존재하지 않은 회원정보입니다.");
        }

        User user = optionalUser.get();
        user.setHeight(changeHeightAndWeightReqDto.getHeight());
        user.setWeight(changeHeightAndWeightReqDto.getWeight());

        int result = userRepository.changeHeightAndWeight(user);
        if(result != 1) {
            throw new RuntimeException("사용자 신체정보 변경에 실패했습니다. 다시 시도해 주세요");
        }
        return new ApiRespDto<>("success", "사용자 신체정보가 변경되었습니다.", null);
    }

    public ApiRespDto<?> changeCurrentRun(ChangeCurrentRunReqDto changeCurrentRunReqDto, PrincipalUser principalUser) {
        if (!changeCurrentRunReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<User> optionalUser = userRepository.getUserByUserId(changeCurrentRunReqDto.getUserId());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("존재하지 않은 회원정보입니다.");
        }

        User user = optionalUser.get();
        user.setCurrentRun(changeCurrentRunReqDto.getCurrentRun());

        int result = userRepository.changeCurrentRun(user);
        if(result != 1) {
            throw new RuntimeException("사용자 러닝기록 변경에 실패했습니다. 다시 시도해 주세요" );
        }
        return new ApiRespDto<>("success", "사용자 러닝기록이 변경되었습니다.", null);
    }

    public ApiRespDto<?> changeWeeklyRun(ChangeWeeklyRunReqDto changeWeeklyRunReqDto, PrincipalUser principalUser) {
        if (!changeWeeklyRunReqDto.getUserId().equals(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        Optional<User> optionalUser = userRepository.getUserByUserId(changeWeeklyRunReqDto.getUserId());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("존재하지 않은 회원정보입니다.");
        }

        User user = optionalUser.get();
        user.setWeeklyRun(changeWeeklyRunReqDto.getWeeklyRun());

        int result = userRepository.changeWeeklyRun(user);
        if(result != 1) {
            throw new RuntimeException("사용자 주간기록 변경에 실패했습니다. 다시 시도해 주세요");
        }
        return new ApiRespDto<>("success", "사용자 주간기록이 변경되었습니다.", null);
    }


    public ApiRespDto<?> withdraw(PrincipalUser principalUser) {
        Optional<User> foundUser = userRepository.getUserByUserId(principalUser.getUserId());
        if (foundUser.isEmpty()) {
            throw new RuntimeException("회원 정보가 존재하지 않습니다.");

        }
        User user = foundUser.get();
        if (!user.isActive()) {
            return new ApiRespDto<>("success", "이미 탈퇴 처리된 계정입니다. 로그아웃합니다.", null);
        }

        int result = userRepository.withdraw(user.getUserId());
        if (result != 1) {
            throw new RuntimeException("탈퇴처리에 실패했습니다.");
        }
        return new ApiRespDto<>("success", "탈퇴처리가 완료되었습니다. 30일 이후 회원 정보가 삭제됩니다.", null);
    }

}
