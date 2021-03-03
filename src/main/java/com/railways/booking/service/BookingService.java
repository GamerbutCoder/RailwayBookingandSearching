package com.railways.booking.service;


import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;

public interface BookingService {
     BookingResponseDTO doBooking(BookingRequestDTO requestDTO);
}
