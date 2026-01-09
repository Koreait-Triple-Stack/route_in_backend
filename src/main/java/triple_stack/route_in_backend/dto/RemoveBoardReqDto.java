package triple_stack.route_in_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class RemoveBoardReqDto {
    private Integer boardId;
    private Integer userId;
}
