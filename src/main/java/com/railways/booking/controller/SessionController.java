package com.railways.booking.controller;

import com.railways.booking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/setSession")
    void addSession(@RequestParam String userName,@RequestParam String isLoggedIn){
        sessionService.updateSession(userName,isLoggedIn);
    }

    @PostMapping("/deleteSession")
    void deleteSession(@RequestParam String userName){
        sessionService.deleteSession(userName);
    }
}
