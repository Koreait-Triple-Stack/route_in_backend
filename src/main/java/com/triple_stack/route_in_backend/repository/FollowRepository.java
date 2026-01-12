package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Follow;
import com.triple_stack.route_in_backend.entity.User;
import com.triple_stack.route_in_backend.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public List<User> getFollowerUserList(Integer userId) {
        return followMapper.getFollowerUserList(userId);
    }

    public List<User> getFollowingUserList(Integer userId) {
        return followMapper.getFollowingUserList(userId);
    }

    public Optional<Follow> getFollowByFollowerUserIdAndFollowingUserId(Integer followerUserId, Integer followingUserId){
        return followMapper.getFollowByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId);
    }

    public int plusFollower(Integer userId) {
        return followMapper.plusFollower(userId);
    }

    public int plusFollowing(Integer userId) {
        return followMapper.plusFollowing(userId);
    }

    public int minusFollower(Integer userId) {
        return followMapper.minusFollower(userId);
    }

    public int minusFollowing(Integer userId) {
        return followMapper.minusFollowing(userId);
    }
}
