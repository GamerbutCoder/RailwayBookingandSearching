package com.railways.booking.service.impl;

import com.railways.booking.constant.TrainConstants;
import com.railways.booking.controller.SessionController;
import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;
import com.railways.booking.entity.SearchCompositeKey;
import com.railways.booking.entity.SeatAvilability;
import com.railways.booking.entity.Sessions;
import com.railways.booking.entity.Train;
import com.railways.booking.repository.SeatAvailabilityRepository;
import com.railways.booking.repository.SessionRepository;
import com.railways.booking.repository.TrainRepository;
import com.railways.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
public class BookingServiceIMPL implements BookingService {

    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TrainRepository trainRepository;

    private String generateSeatNumbers(Long id,Long totalSeats,Long reqSeats){
        Optional<Train> optionalTrain = trainRepository.findById(id);
        if(optionalTrain.isPresent()){
            int totalSeatsInTrain = optionalTrain.get().getBogie()* TrainConstants.SEATS_PER_COMPARTMENT* TrainConstants.COMPARTMENTS_PER_BOGIE;
            int seatsInBogie = TrainConstants.COMPARTMENTS_PER_BOGIE*TrainConstants.SEATS_PER_COMPARTMENT;
            int bookedSeats = (int)(totalSeatsInTrain - totalSeats);
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < reqSeats; i++) {
                bookedSeats++;
                sb.append('S');
                sb.append((bookedSeats/seatsInBogie)+1);
                sb.append('-');
                sb.append(bookedSeats%seatsInBogie);
                sb.append(' ');
            }
            System.out.println(sb.toString());
            return sb.toString();
        }
        return "Seats Unable to book";
    }

    private boolean validateUserID(String s){
        if(s!=null || s!=""){
            Optional<Sessions> optional = sessionRepository.findById(s);
            if(optional.isPresent()){
                if("true".equals(optional.get().getIsLoggedIn())){
                    return true;
                }
                else return false;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public ResponseEntity<BookingResponseDTO> doBooking(BookingRequestDTO requestDTO) {
        if(validateUserID(requestDTO.getUserID())){
            SearchCompositeKey searchCompositeKey = new SearchCompositeKey();//making composite key for searching seat availability
            searchCompositeKey.setDate(requestDTO.getDateOfJourney());
            searchCompositeKey.setId(requestDTO.getTrainId());
            Optional<SeatAvilability> optional = seatAvailabilityRepository.findById(searchCompositeKey);

            if(optional.isPresent()){
                SeatAvilability seatAvilability = optional.get();
                Long totalSeats = seatAvilability.getTotalSeats();
                long reqSeatCount = (long)requestDTO.getSeatCount();
                Long afterUpdate = totalSeats-reqSeatCount;
                if(afterUpdate >=0){
                    seatAvilability.setTotalSeats(afterUpdate);
                    seatAvailabilityRepository.save(seatAvilability);
                    String seatNumbers = generateSeatNumbers(requestDTO.getTrainId(),totalSeats,reqSeatCount);
                    BookingResponseDTO response = new BookingResponseDTO();
                    response.setSeatCount((int)reqSeatCount);
                    response.setSeatList(Arrays.asList(seatNumbers.split(" ")));
                    response.setDateOfJourney(requestDTO.getDateOfJourney());
                    response.setTrainId(requestDTO.getTrainId());
                    Train train = trainRepository.findById(requestDTO.getTrainId()).get();
                    response.setTrainName(train.getName());
                    response.setDepartureTime(train.getDepartureTime());
                    response.setPassengers(requestDTO.getPassengers());
                    //store the booking history in db
                    ResponseEntity<BookingResponseDTO> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
                    return responseEntity;
                }

            }
        }

        ResponseEntity<BookingResponseDTO> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        // TODO: ResponseEntity in controller
        return responseEntity;
    }
}
