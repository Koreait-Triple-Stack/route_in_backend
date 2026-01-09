package triple_stack.route_in_backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninReqDto {
    private String provider;
    private String providerUserId;
}
