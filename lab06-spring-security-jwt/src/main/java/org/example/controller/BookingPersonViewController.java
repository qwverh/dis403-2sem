package org.example.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.example.dto.BookingPersonViewDto;
import org.example.dto.BookingsViewResponse;
import org.example.service.BookingPersonViewService;
import org.example.service.UserDetailImpl;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingPersonViewController {

    private final BookingPersonViewService bookingPersonViewService;

    public BookingPersonViewController(BookingPersonViewService bookingPersonViewService) {
        this.bookingPersonViewService = bookingPersonViewService;
    }

    @GetMapping("/allview")
    public ResponseEntity<BookingsViewResponse> getBookings() {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        List<BookingPersonViewDto> bookings = bookingPersonViewService.findByHotelId(userDetails.getUser().getHotel().getId());

        bookings.forEach(b-> System.out.println(b.getId()));

        return ResponseEntity.ok(new BookingsViewResponse(bookings));
    }



}