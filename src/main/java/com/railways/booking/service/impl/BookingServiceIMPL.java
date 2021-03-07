package com.railways.booking.service.impl;

import com.railways.booking.constant.TrainConstants;
import com.railways.booking.controller.SessionController;
import com.railways.booking.dto.BookingHistoryRequestDTO;
import com.railways.booking.dto.BookingHistoryResponseDTO;
import com.railways.booking.entity.SearchCompositeKey;
import com.railways.booking.entity.SeatAvilability;
import com.railways.booking.entity.Sessions;
import com.railways.booking.entity.Train;
import com.railways.booking.dto.BookingRequestDTO;
import com.railways.booking.dto.BookingResponseDTO;
import com.railways.booking.entity.*;
import com.railways.booking.repository.BookingHistoryRepository;
import com.railways.booking.repository.SeatAvailabilityRepository;
import com.railways.booking.repository.SessionRepository;
import com.railways.booking.repository.TrainRepository;
import com.railways.booking.service.BookingService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookingServiceIMPL implements BookingService {




    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    private String generateSeatNumbers(Long id,Long totalSeats,Long reqSeats){
        Train optionalTrain = trainRepository.getTrainByTrainId(id);
        if(optionalTrain!=null){
            int totalSeatsInTrain = optionalTrain.getBogie()* TrainConstants.SEATS_PER_COMPARTMENT* TrainConstants.COMPARTMENTS_PER_BOGIE;
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
            String optional =sessionRepository.findbysessionid(s).toString();
            //Optional<Sessions> optional = sessionRepository.findById(s);
            if(optional!=null){
                if("true".equals(optional)){
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
                    Train train = trainRepository.getTrainByTrainId(requestDTO.getTrainId());
                    response.setTrainName(train.getName());
                    response.setDepartureTime(train.getDepartureTime());
                    response.setPassengers(requestDTO.getPassengers());
                    int randomNum = ThreadLocalRandom.current().nextInt(1, 6);
                    double fare = 250.75 * randomNum;
                    response.setFare(fare);
                    //store the booking history in db
                    String sessionId = requestDTO.getUserID();
                    createBookingHistory(response,seatNumbers,sessionId);
                    ResponseEntity<BookingResponseDTO> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
                    return responseEntity;
                }

            }
        }

        ResponseEntity<BookingResponseDTO> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        // TODO: ResponseEntity in controller
        return responseEntity;
    }



    @Transactional
    public void createBookingHistory(BookingResponseDTO bookingResponseDTO,String seatList,String sessionId){
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setTrainId(bookingResponseDTO.getTrainId());
        bookingHistory.setDateOfJourney(bookingResponseDTO.getDateOfJourney());
        bookingHistory.setTrainName(bookingResponseDTO.getTrainName());
        bookingHistory.setDepartureTime(bookingResponseDTO.getDepartureTime());
        bookingHistory.setSeatList(seatList);
        bookingHistory.setSeatCount(bookingResponseDTO.getSeatCount());

        String userName = sessionRepository.findUserNameBySessionId(sessionId);

        bookingHistory.setUserName(userName);

        BookingHistory savedBookingHistory = bookingHistoryRepository.save(bookingHistory);

        return;

    }

    @Override
    public List<BookingHistoryResponseDTO> getBookingHistory(BookingHistoryRequestDTO requestDTO){
        String sessionId = requestDTO.getSessionID();
        String userName = sessionRepository.findUserNameBySessionId(sessionId);

        List<BookingHistory> bookingHistoryList = bookingHistoryRepository.getBookingHistoryByUserName(userName);

        List<BookingHistoryResponseDTO> bookingHistoryResponseDTOList = new ArrayList<>();

        for (BookingHistory bookingHistory : bookingHistoryList) {
            BookingHistoryResponseDTO bookingHistoryResponseDTO = new BookingHistoryResponseDTO();
            BeanUtils.copyProperties(bookingHistory, bookingHistoryResponseDTO);
            bookingHistoryResponseDTOList.add(bookingHistoryResponseDTO);
        }

        return bookingHistoryResponseDTOList;
    }
}
