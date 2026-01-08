package triple_stack.route_in_backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
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
}
