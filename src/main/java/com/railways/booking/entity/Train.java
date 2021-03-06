package com.railways.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long trainId;
    private String name;
    private String startLocation;
    private String endLocation;
    private String departureTime; // hh:mm
    private Integer bogie;
}
