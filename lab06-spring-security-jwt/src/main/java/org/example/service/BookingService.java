package org.example.service;

import org.example.dto.BookingDto;
import org.springframework.stereotype.Service;
import org.example.model.Booking;
import org.example.model.User;
import org.example.repository.BookingRepository;

import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingDto getBookingById(Long bookingId, User user) {
        Booking b = bookingRepository.findByIdAndHotelId(bookingId, user.getHotel().getId());

        return BookingDto.builder()
                .id(b.getId())
                .arrivaldate(b.getArrivaldate())
                .stayingdate(b.getStayingdate())
                .departuredate(b.getDeparturedate())
                .personId(b.getPerson().getId())
                .name(b.getPerson().getName())
                .build();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }
}