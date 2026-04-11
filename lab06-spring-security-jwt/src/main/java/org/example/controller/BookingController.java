package org.example.controller;

import org.example.dto.BookingUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.example.dto.BookingDto;
import org.example.dto.BookingsResponse;
import org.example.model.Booking;
import org.example.repository.BookingRepository;
import org.example.service.BookingService;
import org.example.service.UserDetailImpl;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    public BookingController(BookingRepository bookingRepository, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        System.out.println(userDetails.getUser());
        BookingDto booking = bookingService.getBookingById(id, userDetails.getUser());
        System.out.println(booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/all")
    public ResponseEntity<BookingsResponse> getBookings() {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        System.out.println(userDetails.getUser());
        List<Booking> bookings = bookingRepository.findByHotel(userDetails.getUser().getHotel());

        bookings.forEach(b-> System.out.println(b.getId()));

        return ResponseEntity.ok(new BookingsResponse(bookings));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBooking(@RequestBody BookingUpdateDto dto) {

        Booking booking = bookingService.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("booking not found"));


        booking.setArrivaldate(dto.getArrivaldate());
        booking.setStayingdate(dto.getStayingdate());

        bookingRepository.save(booking);

        return ResponseEntity.ok().build();
    }
}