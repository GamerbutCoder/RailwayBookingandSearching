package com.railways.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Sessions {
    @Id
    private String userName;
    private String sessionID;
    private String isLoggedIn;
}
