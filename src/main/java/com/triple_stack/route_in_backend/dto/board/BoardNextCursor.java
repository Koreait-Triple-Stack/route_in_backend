package com.triple_stack.route_in_backend.dto.board;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

// 무한 스크롤에서 다음 데이터를 어디서부터 가져올지를 프론트에 알려주기 위해 만든 DTO
public class BoardNextCursor {
    private LocalDateTime cursorCreateDt;
    // 다음 페이지 시작 기준이 되는 마지막 글의 작성시간
    // 기준이 되는 게시글의 작성 시간을 담는다. 이 시간보다 더 이전글부터 가져오기
    private Integer cursorBoardId;
    // 같은 작성시간을 가진 글들 사이에서 순서를 확정하기 위한 보조 기준값(보통 PK)입니다.
    // 작성 시간이 같은 글이 여러개면, boardId가 더 작은 것부터 이어서 가져오기 위함.
}
