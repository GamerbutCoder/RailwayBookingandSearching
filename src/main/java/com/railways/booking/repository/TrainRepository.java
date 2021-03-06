package com.railways.booking.repository;


import com.railways.booking.entity.Train;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainRepository extends CrudRepository<Train,Long> {
    @Query(value ="select * from train where train_id = ?1" ,nativeQuery = true)
    Train getTrainByTrainId(Long trainId);
}
