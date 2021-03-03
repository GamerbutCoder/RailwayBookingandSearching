package com.railways.booking.dto;

import lombok.Data;

@Data
public class SearchRequestDTO {
    private String fromLocation;
    private String toLocation;
    private java.sql.Date date;
}
