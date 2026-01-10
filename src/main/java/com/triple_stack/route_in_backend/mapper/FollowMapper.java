package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Follow;
import com.triple_stack.route_in_backend.entity.FollowUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FollowMapper {
    int addFollow(Follow follow);
    int deleteFollow(Follow follow);
    List<FollowUser> getFollowerUserList(Integer userId);
    List<FollowUser> getFollowingUserList(Integer userId);
    Optional<Follow> getFollowByFollowerUserIdAndFollowingUserId(Integer followerUserId, Integer followingUserId);
    int plusFollower(Integer userId);
    int plusFollowing(Integer userId);
    int minusFollower(Integer userId);
    int minusFollowing(Integer userId);
}
