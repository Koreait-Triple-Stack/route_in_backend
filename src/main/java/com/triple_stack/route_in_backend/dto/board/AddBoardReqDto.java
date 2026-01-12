package com.triple_stack.route_in_backend.dto.board;

import com.triple_stack.route_in_backend.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

// 게시글 추가 요청을 받을 때 쓰는 전용 박스 (요청 DTO)
// 게시글을 추가할 때 프론트가 보내는 JSON값들을 컨트롤러/서비스에서 한번에 받기 편하게 AddBoardReqDto로 묶어둔다.
public class AddBoardReqDto {
    private Integer userId;
    private String type;
    private String title;
    private String content;
    private List<String> tags;


    // 프론트가 보낸 값(DTO)을 그대로 안쓰고 서비스가 검증, 가공해서 확정한 값으로 Board를 만들기 위해 만듦
    // Board객체를 Builder패턴으로 만든다.
    public Board toEntity(Integer userId, List<String> tags) {
        return Board.builder()
                .userId(userId)
                .type(type)
                .title(title)
                .content(content)
                .tags(tags)
                .recommendCnt(0)
                .build();
    }
    // .userId(userId): 게시글의 작성자 ID를 설정한다.
    // .tags(tags): 게시글 태그를 Board에 넣는다.
    // .recommendCnt(0): 추천 수를 새 글 생성 시점에는 0으로 초기화하는 규칙
}
