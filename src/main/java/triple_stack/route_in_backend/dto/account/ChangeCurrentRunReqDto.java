package triple_stack.route_in_backend.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChangeCurrentRunReqDto {
    private Integer userId;
    private List<Integer> currentRun;
}
