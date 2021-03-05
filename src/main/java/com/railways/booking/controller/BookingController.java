package com.railways.booking.controller;

import com.railways.booking.dto.BookingHistoryRequestDTO;
import com.railways.booking.dto.BookingHistoryResponseDTO;
import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;
import com.railways.booking.entity.BookingHistory;
import com.railways.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> startBooking(@RequestBody BookingRequestDTO requestDTO){
        return bookingService.doBooking(requestDTO);
    }

    @PostMapping("/bookingHistory")
    public List<BookingHistoryResponseDTO> getHistory(@RequestBody BookingHistoryRequestDTO requestDTO){
        System.out.println(requestDTO);
        List<BookingHistoryResponseDTO> list = new ArrayList<>();
        return bookingService.getBookingHistory(requestDTO);
//        for (int i = 0; i < 10; i++) {
//            BookingHistoryResponseDTO responseDTO = new BookingHistoryResponseDTO();
//            responseDTO.setBookingId((long)i);
//            responseDTO.setDateOfJourney(new java.sql.Date(2021,03,04));
//            responseDTO.setDepartureTime("10:00");
//            responseDTO.setSeatCount(4);
//            responseDTO.setSeatList("s1,s2,s3,s4");
//            responseDTO.setTrainId((long)100);
//            responseDTO.setTrainName("mumbai express "+i);
//            list.add(responseDTO);
//        }
//        return list;
    }
}
