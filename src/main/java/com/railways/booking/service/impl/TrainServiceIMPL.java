package com.railways.booking.service.impl;

import com.railways.booking.client.ClientService;
import com.railways.booking.constant.TrainConstants;
import com.railways.booking.dto.AddingTrainsDTO;
import com.railways.booking.dto.SearchRequestDTO;
import com.railways.booking.dto.SearchResponseDTO;
import com.railways.booking.entity.SearchCompositeKey;
import com.railways.booking.entity.SeatAvilability;
import com.railways.booking.entity.Train;
import com.railways.booking.repository.SeatAvailabilityRepository;
import com.railways.booking.repository.TrainRepository;
import com.railways.booking.service.TrainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TrainServiceIMPL implements TrainService {
    @Autowired
    private ClientService clientService;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;


    @Override
    @Transactional
    public ResponseEntity<List<SearchResponseDTO>> getTrains(SearchRequestDTO requestDTO) {
        if(requestDTO.getDate()==null || requestDTO.getToLocation()==null || requestDTO.getFromLocation()==null){
            ResponseEntity<List<SearchResponseDTO>> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        java.sql.Date date = requestDTO.getDate();

        String fl = requestDTO.getFromLocation();
        String tl = requestDTO.getToLocation();
        fl = fl.toLowerCase();
        tl = tl.toLowerCase();

        // TODO: put required field in search and remove the dependency of trainRepository
        List<Long> ll = clientService.getTrainIds(fl,tl); // list of train-ids
        System.out.println(ll);
        List<SearchResponseDTO> response = new ArrayList<>();
        for(Long l : ll){
            Train optional = trainRepository.getTrainByTrainId(l); //finding train details
            if(optional!=null){ // if train exists


                SearchResponseDTO res = new SearchResponseDTO();
                res.setName(optional.getName());
                res.setDepartureTime(optional.getDepartureTime()); // need to be updated using distance and speed
                res.setTrainId(optional.getTrainId());
                res.setStartLocation(requestDTO.getFromLocation());
                res.setEndLocation(requestDTO.getToLocation());
                res.setDate(date);


                SearchCompositeKey searchCompositeKey = new SearchCompositeKey();//making composite key for searching seat availability
                searchCompositeKey.setDate(date);
                searchCompositeKey.setId(optional.getTrainId());

                Optional<SeatAvilability> optional1 = seatAvailabilityRepository.findById(searchCompositeKey); //checking if seat availability table has row

                if(!optional1.isPresent()){ // if doesn't exist we calculate and insert total seats
                    SeatAvilability seatAvilability = new SeatAvilability();
                    seatAvilability.setIdDate(searchCompositeKey);
                    Long totalSeats = (long)optional.getBogie()* TrainConstants.COMPARTMENTS_PER_BOGIE*TrainConstants.SEATS_PER_COMPARTMENT;
                    res.setTotalSeats(totalSeats);
                    seatAvilability.setTotalSeats(totalSeats);
                    seatAvailabilityRepository.save(seatAvilability);

                }
                else{
                    res.setTotalSeats(optional1.get().getTotalSeats());
                }

                response.add(res);



            }
        }
        ResponseEntity<List<SearchResponseDTO>> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public void addTrain(AddingTrainsDTO req) {
        Train newTrain = new Train();
       // BeanUtils.copyProperties(newTrain,req);

        //newTrain.setId(id);
        newTrain.setBogie(req.getBogie());
        newTrain.setDepartureTime(req.getDepartureTime());
        newTrain.setName(req.getName());
        newTrain.setEndLocation(req.getEndLocation());
        newTrain.setStartLocation(req.getStartLocation());
        newTrain.setTrainId(req.getTrainId());

        trainRepository.save(newTrain);
    }
}
