package com.railways.booking.service.impl;

import com.railways.booking.entity.Sessions;
import com.railways.booking.repository.SessionRepository;
import com.railways.booking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
@CrossOrigin
@Service
public class SessionServiceIMPL implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public void updateSession(String userName, String sessionId,String isLoggedIn) {
            Sessions sessions = new Sessions();
            sessions.setSessionID(sessionId);
            sessions.setIsLoggedIn(isLoggedIn);
            sessions.setUserName(userName);
            sessionRepository.save(sessions);
    }

    @Override
    @Transactional
    public void deleteSession(String uID) {
        try{
            sessionRepository.deleteSession(uID);
        }
        catch (Exception e){

        }
    }
}
