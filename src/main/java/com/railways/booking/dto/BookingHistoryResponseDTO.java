package com.railways.booking.dto;

import lombok.Data;

@Data
public class BookingHistoryResponseDTO {
    private Long bookingId;
    private String userName;
    private Long trainId;
    private String trainName;
    private java.sql.Date dateOfJourney;
    private int seatCount;
    private String seatList;
    private String departureTime;
}
