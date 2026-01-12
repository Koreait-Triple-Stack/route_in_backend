package com.triple_stack.route_in_backend.dto.board;

import com.triple_stack.route_in_backend.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinusRecommendReqDto {
    private Integer boardId;
    private Integer userId;

    public Recommend toEntity() {
        return Recommend.builder()
                .boardId(boardId)
                .userId(userId)
                .build();
    }
}
