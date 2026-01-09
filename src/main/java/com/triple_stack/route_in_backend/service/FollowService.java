package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.follow.AddFollowReqDto;
import com.triple_stack.route_in_backend.entity.Follow;
import com.triple_stack.route_in_backend.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    // @Transactional
    // public ApiRespDto<?> addFollow(AddFollowReqDto addFollowReqDto) {
    //     Optional<Follow> foundFollow = followRepository
    //             .getFollowByFollowerUserIdAndFollowingUserId(addFollowReqDto.getFollowerUserId(), addFollowReqDto.getFollowingUserId());
    //     if (foundFollow.isPresent()) {
    //         throw new RuntimeException("이미 팔로우가 완료된 요청입니다.");
    //     }
    //
    //     int followResult = followRepository.addFollow(addFollowReqDto.toEntity());
    //     if (followResult != 1) {
    //         throw new RuntimeException("팔로우 추가에 실패했습니다.");
    //     }
    //
    //     int userResult =
    // }
}
