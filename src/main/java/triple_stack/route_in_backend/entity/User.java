package triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
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

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    public boolean isAdmin() {return "ROLE_ADMIN".equals(role);}
}
