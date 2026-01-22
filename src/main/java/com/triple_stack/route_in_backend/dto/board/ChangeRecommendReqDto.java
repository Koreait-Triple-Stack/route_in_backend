package com.triple_stack.route_in_backend.dto.board;

import com.triple_stack.route_in_backend.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRecommendReqDto {
    private Integer userId;
    private Integer boardId;
    private Boolean isRecommended;

    public Recommend toEntity() {
        return Recommend.builder()
                .boardId(boardId)
                .userId(userId)
                .build();
    }
}
