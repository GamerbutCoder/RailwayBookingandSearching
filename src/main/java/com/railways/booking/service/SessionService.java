package com.railways.booking.service;

public interface SessionService {
    void updateSession(String userName,String sessionId,String isLoggedIn);
    void deleteSession(String sessionId);
}
