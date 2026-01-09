package com.triple_stack.route_in_backend.dto.user.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.triple_stack.route_in_backend.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
public class SignupReqDto {
    private String username;
    private String provider;
    private String providerUserId;
    private Integer gender;
    private String address;
    private Integer birthYear;
    private Integer height;
    private Integer weight;
    private List<String> currentRun;
    private List<String> weeklyRun;

    public User toEntity() {
        return User.builder()
                .username(username)
                .provider(provider)
                .providerUserId(providerUserId)
                .gender(gender)
                .address(address)
                .birthYear(birthYear)
                .height(height)
                .weight(weight)
                .currentRun(currentRun)
                .weeklyRun(weeklyRun)
                .build();
    }
}
