package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.follow.AddFollowReqDto;
//import com.triple_stack.route_in_backend.dto.user.follow.ChangeFollowReqDto;
import com.triple_stack.route_in_backend.dto.user.follow.ChangeFollowReqDto;
import com.triple_stack.route_in_backend.dto.user.follow.DeleteFollowReqDto;
import com.triple_stack.route_in_backend.entity.Follow;
import com.triple_stack.route_in_backend.entity.Notification;
import com.triple_stack.route_in_backend.repository.FollowRepository;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private NotificationUtils notificationUtils;
    @Autowired
    private UserRepository userRepository;

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

        // 팔로워 수 증가: 팔로우 당하는 사람
        int followerResult = followRepository.plusFollower(addFollowReqDto.getFollowingUserId());
        if (followerResult != 1) {
            throw new RuntimeException("팔로우 추가에 실패했습니다.");
        }
        // 팔로잉 수 : 팔로우 하는 사람
        int followingResult = followRepository.plusFollowing(addFollowReqDto.getFollowerUserId());
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
        // 팔로워 수 감소: 언팔로우 당하는 유저
        int followerResult = followRepository.minusFollower(deleteFollowReqDto.getFollowingUserId());
        if (followerResult != 1) {
            throw new RuntimeException("팔로우 삭제에 실패했습니다.");
        }
        // 팔로잉 수 감소: 언팔로우 하는 유저
        int followingResult = followRepository.minusFollowing(deleteFollowReqDto.getFollowerUserId());
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


    private void validatePrincipal(Integer followerUserId, PrincipalUser principalUser) {
        if (principalUser == null) throw new RuntimeException("로그인이 필요합니다.");
        if (followerUserId == null) throw new RuntimeException("followerUserId가 필요합니다.");

        boolean isOwner = followerUserId.equals(principalUser.getUserId());
        boolean isAdmin = "ROLE_ADMIN".equals(principalUser.getRole());

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("잘못된 접근입니다.");
        }
    }


    @Transactional
    public ApiRespDto<?> changeFollow(ChangeFollowReqDto dto, PrincipalUser principalUser) {
        if(dto.getFollowerUserId() == null) {
            dto.setFollowerUserId(principalUser != null ? principalUser.getUserId() : null);
        }
        validatePrincipal(dto.getFollowerUserId(), principalUser);

        if(dto.getFollowingUserId() == null) throw new RuntimeException("followingUserId가 필요합니다.");
        if(dto.getIsFollowing() == null) throw new RuntimeException("isFollowing 값이 필요합니다.");
        if(dto.getFollowerUserId().equals(dto.getFollowingUserId())) {
            throw new RuntimeException("본인은 팔로우 할 수 없습니다.");
        }
        boolean exists = followRepository.isFollowing(dto.getFollowerUserId(), dto.getFollowingUserId());

        if (dto.getIsFollowing()) {
            // 현재 팔로우 중 => 언팔로우
            if (!exists) throw new RuntimeException("이미 팔로우 상태가 아닙니다.");

            deleteFollow(DeleteFollowReqDto.builder()
                    .followerUserId(dto.getFollowerUserId())
                    .followingUserId(dto.getFollowingUserId())
                    .build());

            return new ApiRespDto<>("success", "팔로우 취소 완료", null);
        }

        // 현재 팔로우 아님 => 팔로우
        if (exists) throw new RuntimeException("이미 팔로우가 완료된 요청입니다.");

        addFollow(new AddFollowReqDto(dto.getFollowerUserId(), dto.getFollowingUserId()));
        List<Notification> notifications = new ArrayList<>();
        String profileImg = userRepository.getUserByUserId(principalUser.getUserId()).get().getProfileImg();
        notifications.add(Notification.builder()
                .userId(dto.getFollowingUserId())
                .title("새 팔로우")
                .message(principalUser.getUsername() + "님이 팔로우하였습니다.")
                .path("/user/"+principalUser.getUserId())
                .profileImg(profileImg).build());
        notificationUtils.sendAndAddNotification(notifications);
        return new ApiRespDto<>("success", "팔로우 완료", null);
    }

    public ApiRespDto<?> getFollowStatus(Integer followerUserId, Integer followingUserId, PrincipalUser principalUser) {
        validatePrincipal(followerUserId, principalUser);
        if (followingUserId == null) throw new RuntimeException("followingUserId가 필요합니다.");

        boolean isFollowing = followRepository.isFollowing(followerUserId, followingUserId);
        return new ApiRespDto<>("success", "팔로우 상태 조회 완료", isFollowing);
    }
}



