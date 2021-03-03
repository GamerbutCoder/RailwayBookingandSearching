package com.railways.booking.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SeatAvilability {
    @EmbeddedId
    private SearchCompositeKey idDate;
    private Long totalSeats;
}
