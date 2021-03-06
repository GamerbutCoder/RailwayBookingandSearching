package com.railways.booking.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class BookingHistory {
    @Id
    @GenericGenerator(name ="booking_id_seq",strategy ="increment")
    @GeneratedValue(generator = "booking_id_seq", strategy = GenerationType.AUTO)
    private Long bookingId;
    private String userName;
    private Long trainId;
    private String trainName;
    private java.sql.Date dateOfJourney;
    private Integer seatCount;
    private String seatList;
    private String departureTime;
}
