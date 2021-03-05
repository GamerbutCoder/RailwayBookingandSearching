package com.railways.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingResponseDTO {
    private Long trainId;
    private String trainName;
    private java.sql.Date dateOfJourney;
    private int seatCount;
    private List<String> seatList;
    private String departureTime;
    private List<PassengerDTO> passengers;
    private double fare;

    //fare need to be added
}
