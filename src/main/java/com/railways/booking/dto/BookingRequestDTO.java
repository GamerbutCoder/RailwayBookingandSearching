package com.railways.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDTO {
    private Long trainId;
    private java.sql.Date dateOfJourney;
    private int seatCount;
    private List<PassengerDTO> passengers;
    private String userID;
}
