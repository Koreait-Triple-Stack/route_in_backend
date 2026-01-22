package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.follow.AddFollowReqDto;
import com.triple_stack.route_in_backend.dto.user.follow.DeleteFollowReqDto;
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

    @Transactional
    public ApiRespDto<?> addFollow(AddFollowReqDto addFollowReqDto) {
        boolean isFollowing = followRepository
                .isFollowing(addFollowReqDto.getFollowerUserId(), addFollowReqDto.getFollowingUserId());
        if (isFollowing) {
            throw new RuntimeException("이미 팔로우가 완료된 요청입니다.");
        }

        int followResult = followRepository.addFollow(addFollowReqDto.toEntity());
        if (followResult != 1) {
            throw new RuntimeException("팔로우 추가에 실패했습니다.");
        }

        int followerResult = followRepository.plusFollower(addFollowReqDto.getFollowerUserId());
        if (followerResult != 1) {
            throw new RuntimeException("팔로우 추가에 실패했습니다.");
        }

        int followingResult = followRepository.plusFollowing(addFollowReqDto.getFollowingUserId());
        if (followingResult != 1) {
            throw new RuntimeException("팔로우 추가에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "팔로우 추가를 완료했습니다.", null);
    }

    @Transactional
    public ApiRespDto<?> deleteFollow(DeleteFollowReqDto deleteFollowReqDto) {
        boolean isFollowing = followRepository
                .isFollowing(deleteFollowReqDto.getFollowerUserId(), deleteFollowReqDto.getFollowingUserId());
        if (!isFollowing) {
            throw new RuntimeException("이미 팔로우 상태가 아닙니다.");
        }

        int followResult = followRepository.deleteFollow(deleteFollowReqDto.toEntity());
        if (followResult != 1) {
            throw new RuntimeException("팔로우 삭제에 실패했습니다.");
        }

        int followerResult = followRepository.minusFollower(deleteFollowReqDto.getFollowerUserId());
        if (followerResult != 1) {
            throw new RuntimeException("팔로우 삭제에 실패했습니다.");
        }

        int followingResult = followRepository.minusFollowing(deleteFollowReqDto.getFollowingUserId());
        if (followingResult != 1) {
            throw new RuntimeException("팔로우 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "팔로우 삭제를 완료했습니다.", null);
    }

    public ApiRespDto<?> getFollowerUserList(Integer userId) {
        return new ApiRespDto<>("success", "팔로워 조회 완료", followRepository.getFollowerUserList(userId));
    }

    public ApiRespDto<?> getFollowingUserList(Integer userId) {
        return new ApiRespDto<>("success", "팔로잉 조회 완료", followRepository.getFollowingUserList(userId));
    }
}
