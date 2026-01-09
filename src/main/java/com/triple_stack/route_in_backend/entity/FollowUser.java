package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FollowUser {
    private Integer userId;
    private String username;
    private String profileImg;
    private Integer birthYear;
}
