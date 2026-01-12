package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommend {
    private Integer recommendId;
    private Integer boardId;
    private Integer userId;
    private LocalDateTime createDt;
}
