package com.triple_stack.route_in_backend.security.model;

import com.triple_stack.route_in_backend.entity.Address;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class PrincipalUser implements UserDetails {
    private Integer userId;
    private String username;
    private String profileImg;
    private String gender;
    private Integer birthYear;
    private Integer height;
    private Integer weight;
    private List<String> currentRun;
    private List<String> weeklyRun;
    private String status;
    private String role;
    private Integer followerCnt;
    private Integer followingCnt;

    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return "";
    }
}
