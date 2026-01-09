package triple_stack.route_in_backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import triple_stack.route_in_backend.entity.User;
import triple_stack.route_in_backend.mapper.UserMapper;

import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public Optional<User> getUserByUserId(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    public Optional<User> getUserByProviderAndProviderUserId(String provider, String providerUserId) {
        return userMapper.getUserByProviderAndProviderUserId(provider, providerUserId);
    }

    public Optional<User> addUser(User user) {
        try {
            int result = userMapper.addUser(user);
            if (result != 1) {
                throw new RuntimeException("회원정보 추가에 실패했습니다.");
            }
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public int changeUsername(User user) {
        return userMapper.changeUsername(user);
    }

    public int changeProfileImg(User user) {
        return userMapper.changeProfileImg(user);
    }

    public int changeAddress(User user) {
        return userMapper.changeAddress(user);
    }

    public int changeHeightAndWeight(User user) {
        return userMapper.changeHeightAndWeight(user);
    }

    public int changeCurrentRun(User user) {
        return userMapper.changeCurrentRun(user);
    }

    public int changeWeeklyRun(User user) {
        return userMapper.changeWeeklyRun(user);
    }

    public int withdraw(Integer userId) {
        return userMapper.withdraw(userId);
    }

    public void deleteUser() {
        userMapper.deleteUser();
    }
}
