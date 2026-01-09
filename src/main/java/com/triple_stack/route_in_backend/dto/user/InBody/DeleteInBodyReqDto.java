package com.triple_stack.route_in_backend.dto.user.InBody;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteInBodyReqDto {
    private Integer inBodyId;
    private Integer userId;
}
