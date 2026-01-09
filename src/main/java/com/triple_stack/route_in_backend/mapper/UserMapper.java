package com.triple_stack.route_in_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.triple_stack.route_in_backend.entity.User;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByProviderAndProviderUserId(String provider, String providerUserId);
    int addUser(User user);
    int changeUsername(User user);
    int changeProfileImg(User user);
    int changeAddress(User user);
    int changeHeightAndWeight(User user);
    int changeCurrentRun(User user);
    int changeWeeklyRun(User user);
    int withdraw(Integer userId);
    void deleteUser();
}
