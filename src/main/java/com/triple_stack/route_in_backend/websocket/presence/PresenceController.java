package com.triple_stack.route_in_backend.websocket.presence;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PresenceController {
    private final PresenceStore presenceStore;

    @MessageMapping("/presence/active")
    public void setActiveRoom(
            PresenceReq req,
            Principal principal,
            @Header("simpSessionId") String sessionId
    ) {
        if (principal == null) return;

        Integer userId;
        try {
            userId = Integer.parseInt(principal.getName());
        } catch (Exception e) {
            return;
        }

        presenceStore.setActiveRoom(sessionId, req != null ? req.getRoomId() : null);
    }
}
