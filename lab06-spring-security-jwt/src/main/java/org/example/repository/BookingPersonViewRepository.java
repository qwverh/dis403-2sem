package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.model.BookingPersonView;

import java.util.List;

public interface BookingPersonViewRepository extends JpaRepository<BookingPersonView, Long> {
    List<BookingPersonView> findByHotelId(Long hotelId);
}