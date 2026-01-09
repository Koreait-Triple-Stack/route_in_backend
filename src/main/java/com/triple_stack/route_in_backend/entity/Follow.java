package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Follow {
    private Integer followId;
    private Integer followerUserId;
    private Integer followingUserId;
    private LocalDateTime createDt;
}
