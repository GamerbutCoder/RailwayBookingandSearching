package com.railways.booking.service.impl;

import com.railways.booking.client.ClientService;
import com.railways.booking.constant.TrainConstants;
import com.railways.booking.dto.SearchRequestDTO;
import com.railways.booking.dto.SearchResponseDTO;
import com.railways.booking.entity.SearchCompositeKey;
import com.railways.booking.entity.SeatAvilability;
import com.railways.booking.entity.Train;
import com.railways.booking.repository.SeatAvailabilityRepository;
import com.railways.booking.repository.TrainRepository;
import com.railways.booking.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<SearchResponseDTO> getTrains(SearchRequestDTO requestDTO) {

        java.sql.Date date = requestDTO.getDate();
        //System.out.println(date);

        String fl = requestDTO.getFromLocation();
        String tl = requestDTO.getToLocation();
        fl = fl.toLowerCase();
        tl = tl.toLowerCase();

        // TODO: put required field in search and remove the dependency of trainRepository
        List<Long> ll = clientService.getTrainIds(fl,tl); // list of train-ids
        System.out.println(ll);
        List<SearchResponseDTO> response = new ArrayList<>();
        for(Long l : ll){
            Optional<Train> optional = trainRepository.findById(l); //finding train details
            if(optional.isPresent()){ // if train exists


                SearchResponseDTO res = new SearchResponseDTO();
                res.setName(optional.get().getName());
                res.setDepartureTime(optional.get().getDepartureTime()); // need to be updated using distance and speed
                res.setTrainId(optional.get().getId());
                res.setStartLocation(requestDTO.getFromLocation());
                res.setEndLocation(requestDTO.getToLocation());
                res.setDate(date);


                SearchCompositeKey searchCompositeKey = new SearchCompositeKey();//making composite key for searching seat availability
                searchCompositeKey.setDate(date);
                searchCompositeKey.setId(optional.get().getId());

                Optional<SeatAvilability> optional1 = seatAvailabilityRepository.findById(searchCompositeKey); //checking if seat availability table has row

                if(!optional1.isPresent()){ // if doesn't exist we calculate and insert total seats
                    SeatAvilability seatAvilability = new SeatAvilability();
                    seatAvilability.setIdDate(searchCompositeKey);
                    Long totalSeats = (long)optional.get().getBogie()* TrainConstants.COMPARTMENTS_PER_BOGIE*TrainConstants.SEATS_PER_COMPARTMENT;
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

        return response;
    }
}
