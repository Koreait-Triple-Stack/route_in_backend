package com.triple_stack.route_in_backend.dto.user.follow;

import com.triple_stack.route_in_backend.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DeleteFollowReqDto {
    private Integer followerUserId;
    private Integer followingUserId;

    public Follow toEntity() {
        return Follow.builder()
                .followerUserId(followerUserId)
                .followingUserId(followingUserId)
                .build();
    }
}
