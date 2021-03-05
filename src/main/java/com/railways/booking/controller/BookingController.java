package com.railways.booking.controller;

import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;
import com.railways.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> startBooking(@RequestBody BookingRequestDTO requestDTO){
        return bookingService.doBooking(requestDTO);
    }
}
