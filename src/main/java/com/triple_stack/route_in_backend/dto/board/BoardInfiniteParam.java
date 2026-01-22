package com.triple_stack.route_in_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInfiniteParam {
    private String type;
    private String sort;
    private List<String> tags;
    private Integer limit;
    private Integer limitPlusOne;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cursorCreateDt;
    private Integer cursorBoardId;

    public int getLimitPlusOne() {
        int l = (limit == null ? 5 : limit);
        l = Math.max(1, Math.min(l, 5));
        return l + 1;
    }

    public boolean hasCursor() {
        return cursorCreateDt != null && cursorBoardId != null;
    }
}
