package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Follow;
import com.triple_stack.route_in_backend.entity.FollowUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    int addFollow(Follow follow);
    int deleteFollow(Follow follow);
    List<FollowUser> getFollowUserByFollowerUserId(Integer followerUserId);
    List<FollowUser> getFollowUserByFollowingUserId(Integer followingUserId);
}
