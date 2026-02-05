package com.triple_stack.route_in_backend.websocket.presence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresenceReq {
    private Integer roomId;
}
