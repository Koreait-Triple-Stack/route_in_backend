package com.triple_stack.route_in_backend.websocket.config;

import com.triple_stack.route_in_backend.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketAuthConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private final JwtUtils jwtUtils;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> auth = accessor.getNativeHeader("Authorization");
                    if (auth != null && !auth.isEmpty()) {
                        String raw = auth.get(0);
                        String token = raw.startsWith("Bearer ") ? raw.substring(7) : raw;

                        Integer userId = Integer.parseInt(jwtUtils.getUserIdAsString(token));

                        // ✅ convertAndSendToUser("22", ...) 와 매칭되게 Principal name을 userId 문자열로
                        Principal principal = () -> String.valueOf(userId);
                        accessor.setUser(principal);
                    }
                }

                return message;
            }
        });
    }
}
