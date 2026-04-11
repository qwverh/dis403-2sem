package org.example.service;

import org.springframework.stereotype.Service;
import org.example.dto.BookingPersonViewDto;
import org.example.repository.BookingPersonViewRepository;

import java.util.List;

@Service
public class BookingPersonViewService {

    private final BookingPersonViewRepository bookingPersonViewRepository;

    public BookingPersonViewService(BookingPersonViewRepository bookingPersonViewRepository) {
        this.bookingPersonViewRepository = bookingPersonViewRepository;
    }

    public List<BookingPersonViewDto> findByHotelId(Long hotelId) {
        return bookingPersonViewRepository.findByHotelId(hotelId).stream()
                .map(b ->
                    BookingPersonViewDto.builder()
                        .id(b.getId())
                        .arrivaldate(b.getArrivaldate())
                        .stayingdate(b.getStayingdate())
                            .name(b.getName())
                            .birthdate(b.getBirthdate())
                            .hotelId(b.getHotelId())
                            .gender(b.getGender())
                            .build()
                ).toList();
    }
}