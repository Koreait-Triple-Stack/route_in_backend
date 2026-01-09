package triple_stack.route_in_backend.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UserRespDto {
    private Integer userId;
    private String username;
    private String provider;
    private String providerUserId;
    private String profileImg;
    private Integer gender;
    private String address;
    private Integer birthYear;
    private Integer height;
    private Integer weight;
    private List<Integer> currentRun;
    private List<Integer> weeklyRun;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
    private String status;
    private LocalDateTime withdrawDt;
    private LocalDateTime deleteDt;
    private String role;
}
