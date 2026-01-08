package triple_stack.route_in_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import triple_stack.route_in_backend.entity.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByProviderAndProviderUserId(String provider, String providerUserId);
    int addUser(User user);
    int changeProfileImg(User user);
    int withdraw(Integer userId);
    void deleteUser();
}
