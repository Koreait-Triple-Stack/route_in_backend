package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Follow;
import com.triple_stack.route_in_backend.entity.FollowUser;
import com.triple_stack.route_in_backend.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowRepository {
    @Autowired
    private FollowMapper followMapper;

    public int addFollow(Follow follow) {
        return followMapper.addFollow(follow);
    }

    public int deleteFollow(Follow follow) {
        return followMapper.deleteFollow(follow);
    }

    public List<FollowUser> getFollowUserByFollowerUserId(Integer followerUserId) {
        return followMapper.getFollowUserByFollowerUserId(followerUserId);
    }

    public List<FollowUser> getFollowUserByFollowingUserId(Integer followingUserId) {
        return followMapper.getFollowUserByFollowingUserId(followingUserId);
    }
}
