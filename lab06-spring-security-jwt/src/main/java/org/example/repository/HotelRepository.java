package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.model.Hotel;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
}