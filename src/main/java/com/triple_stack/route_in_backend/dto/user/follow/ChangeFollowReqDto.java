//package com.triple_stack.route_in_backend.dto.user.follow;
//
//
//import com.triple_stack.route_in_backend.entity.Follow;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ChangeFollowReqDto {
//    private Integer followingUserId;
//    private Integer followerId;
//    private Boolean isFollowing;
//
//    private Follow toEntity() {
//        return Follow.builder()
//                .followerUserId(followerId)
//                .followingUserId(followingUserId)
//                .build();
//    }
//
//}
