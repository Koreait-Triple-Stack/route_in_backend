package com.triple_stack.route_in_backend.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeUsernameReqDto {
    private Integer userId;
    private String username;
}
