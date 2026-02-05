package com.triple_stack.route_in_backend.websocket.presence;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PresenceStore {
    private final ConcurrentHashMap<String, Integer> userIdBySession = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Integer, Set<String>> sessionsByUser = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Integer> activeRoomBySession = new ConcurrentHashMap<>();

    public void onConnect(Integer userId, String sessionId) {
        if (userId == null || sessionId == null) return;

        userIdBySession.put(sessionId, userId);
        sessionsByUser.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
    }

    public void onDisconnect(String sessionId) {
        if (sessionId == null) return;

        activeRoomBySession.remove(sessionId);

        Integer userId = userIdBySession.remove(sessionId);
        if (userId == null) return;

        Set<String> sessions = sessionsByUser.get(userId);
        if (sessions == null) return;

        sessions.remove(sessionId);
        if (sessions.isEmpty()) {
            sessionsByUser.remove(userId);
        }
    }

    public void setActiveRoom(String sessionId, Integer roomId) {
        if (sessionId == null) return;

        if (roomId == null) {
            activeRoomBySession.remove(sessionId);
        } else {
            activeRoomBySession.put(sessionId, roomId);
        }
    }

    public boolean isUserActiveInRoom(Integer userId, Integer roomId) {
        if (userId == null || roomId == null) return false;

        Set<String> sessions = sessionsByUser.get(userId);
        if (sessions == null || sessions.isEmpty()) return false;

        for (String sessionId : sessions) {
            Integer activeRoom = activeRoomBySession.get(sessionId);
            if (activeRoom != null && activeRoom.equals(roomId)) {
                return true;
            }
        }

        return false;
    }

    public Integer getActiveRoomBySession(String sessionId) {
        return activeRoomBySession.get(sessionId);
    }
}
