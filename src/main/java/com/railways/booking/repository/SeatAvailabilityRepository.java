package com.railways.booking.repository;

import com.railways.booking.entity.SearchCompositeKey;
import com.railways.booking.entity.SeatAvilability;
import org.springframework.data.repository.CrudRepository;

public interface SeatAvailabilityRepository extends CrudRepository<SeatAvilability, SearchCompositeKey> {

}
