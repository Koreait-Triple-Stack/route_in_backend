package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer userId;
    private String username;
    private String provider;
    private String providerUserId;
    private String profileImg;
    private String gender;
    private LocalDate birthDate;
    private Integer height;
    private Integer weight;
    private List<String> currentRun;
    private List<String> weeklyRun;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
    private String status;
    private LocalDateTime withdrawDt;
    private LocalDateTime deleteDt;
    private String role;
    private Integer followerCnt;
    private Integer followingCnt;

    private Address address;

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    public boolean isAdmin() {return "ROLE_ADMIN".equals(role);}
}
