package com.railways.booking.controller;

import com.railways.booking.dto.AddingTrainsDTO;
import com.railways.booking.dto.SearchRequestDTO;
import com.railways.booking.dto.SearchResponseDTO;
import com.railways.booking.entity.Train;
import com.railways.booking.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SearchResponseDTO>> search(@RequestBody SearchRequestDTO requestDTO){
        return trainService.getTrains(requestDTO);
    }

    @PostMapping("/admin/addTrain")
    public void addTrain(@RequestBody AddingTrainsDTO newTrain){
        trainService.addTrain(newTrain);
    }

}
