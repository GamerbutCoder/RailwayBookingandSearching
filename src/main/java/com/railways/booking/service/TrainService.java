package com.railways.booking.service;

import com.railways.booking.dto.SearchRequestDTO;
import com.railways.booking.dto.SearchResponseDTO;

import java.util.List;

public interface TrainService {
    List<SearchResponseDTO> getTrains(SearchRequestDTO requestDTO);
}
