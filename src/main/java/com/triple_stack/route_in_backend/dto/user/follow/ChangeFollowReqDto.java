package com.triple_stack.route_in_backend.dto.user.follow;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeFollowReqDto {
    private Integer followingUserId; // 상대
    private Integer followerUserId; // 나
    private Boolean isFollowing;    // 현재 상태

}
