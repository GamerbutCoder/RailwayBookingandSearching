package com.railways.booking.repository;


import com.railways.booking.entity.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train,Long> {

}
