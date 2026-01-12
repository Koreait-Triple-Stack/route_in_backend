package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

// 무한 스크롤
public class BoardInfiniteRespDto {
    private List<BoardRespDto> boardRespDtoList;
    // 이번 요청에서 가져온 게시글 목록을 담는 필드
    private boolean hasNext;
    // 다음 페이지가 더 있는지 담는 필드
    private BoardNextCursor boardNextCursor;
    // 다음 페이지를 가져올 때 필요한 값(cursorCreateDt, cursorBoardId)들을 담는 필드
    // 다음 데이터를 담는게 아니라 다음 데이터를 가져오기 위한 주소를 담는다.
}
