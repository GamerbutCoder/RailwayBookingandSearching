package com.railways.booking.controller;

import com.railways.booking.dto.SearchRequestDTO;
import com.railways.booking.dto.SearchResponseDTO;
import com.railways.booking.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class TrainController {
    @Autowired
    private TrainService trainService;

    @PostMapping("/search")
    public List<SearchResponseDTO> search(@RequestBody SearchRequestDTO requestDTO){
        return trainService.getTrains(requestDTO);
    }
}
