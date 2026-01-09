package com.triple_stack.route_in_backend.dto.user.InBody;

import com.triple_stack.route_in_backend.entity.InBody;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AddInBodyReqDto {
    private Integer userId;
    private Float bodyWeight;
    private Float skeletalMuscleMass;
    private Float bodyFatMass;
    private LocalDate monthDt;

    public InBody toEntity() {
        return InBody.builder()
                .userId(userId)
                .bodyWeight(bodyWeight)
                .skeletalMuscleMass(skeletalMuscleMass)
                .bodyFatMass(bodyFatMass)
                .monthDt(monthDt)
                .build();
    }
}
