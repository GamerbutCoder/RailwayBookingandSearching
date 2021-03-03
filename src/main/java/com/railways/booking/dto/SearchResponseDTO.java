package com.railways.booking.dto;

import lombok.Data;

@Data
public class SearchResponseDTO {
    private Long trainId;
    private String name;
    private String startLocation;
    private String endLocation;
    private String departureTime;
    private Long totalSeats;
    private java.sql.Date date;
}
