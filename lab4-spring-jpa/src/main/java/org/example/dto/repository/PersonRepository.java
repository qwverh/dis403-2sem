package org.example.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.dto.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
