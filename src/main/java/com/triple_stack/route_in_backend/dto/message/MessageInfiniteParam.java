package com.triple_stack.route_in_backend.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfiniteParam {
    private Integer roomId;
    private Integer limit;
    private Integer limitPlusOne;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cursorCreateDt;
    private Integer cursorMessageId;

    public int getLimitPlusOne() {
        int l = (limit == null ? 20 : limit);
        l = Math.max(1, Math.min(l, 20));
        return l + 1;
    }
}
