package com.railways.booking.service;


import com.railways.booking.dto.BookingHistoryRequestDTO;
import com.railways.booking.dto.BookingHistoryResponseDTO;
import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
     ResponseEntity<BookingResponseDTO> doBooking(BookingRequestDTO requestDTO);

     //get booking history
     List<BookingHistoryResponseDTO> getBookingHistory(BookingHistoryRequestDTO requestDTO);
}
