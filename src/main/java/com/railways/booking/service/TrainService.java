package com.railways.booking.service;

import com.railways.booking.dto.AddingTrainsDTO;
import com.railways.booking.dto.SearchRequestDTO;
import com.railways.booking.dto.SearchResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrainService {
    ResponseEntity<List<SearchResponseDTO>> getTrains(SearchRequestDTO requestDTO);
    void addTrain(AddingTrainsDTO req);
}
