package com.railways.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Train {
    @Id
    private Long id;
    private String name;
    private String startLocation;
    private String endLocation;
    private String departureTime; // hh:mm
    private Integer bogie;
}
