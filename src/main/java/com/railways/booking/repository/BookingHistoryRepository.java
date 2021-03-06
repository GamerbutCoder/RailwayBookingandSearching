package com.railways.booking.repository;

import com.railways.booking.entity.BookingHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingHistoryRepository extends CrudRepository<BookingHistory, Long> {

    @Query(value="Select * from booking_history where user_name=?1",nativeQuery = true)
    public List<BookingHistory> getBookingHistoryByUserName(String user_name);

}
