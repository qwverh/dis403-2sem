package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {


}