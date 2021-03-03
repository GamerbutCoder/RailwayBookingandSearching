package com.railways.booking.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class SearchCompositeKey implements Serializable {
    private Long id;
    private java.sql.Date date;
}
