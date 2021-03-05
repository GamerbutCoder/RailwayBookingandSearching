package com.railways.booking.service;


import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;
import org.springframework.http.ResponseEntity;

public interface BookingService {
     ResponseEntity<BookingResponseDTO> doBooking(BookingRequestDTO requestDTO);
}
