package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class InBody {
    private Integer inBodyId;
    private Integer userId;
    private Float bodyWeight;
    private Float skeletalMuscleMass;
    private Float bodyFatMass;
    private LocalDate monthDt;
}
