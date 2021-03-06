package com.railways.booking.dto;

import lombok.Data;

@Data
public class AddingTrainsDTO {
    private Long trainId;
    private String name;
    private String startLocation;
    private String endLocation;
    private String departureTime; // hh:mm
    private Integer bogie;
}
