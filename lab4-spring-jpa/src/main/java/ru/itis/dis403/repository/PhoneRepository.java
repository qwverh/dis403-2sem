package ru.itis.dis403.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import ru.itis.dis403.model.Phone;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Query("select p from Phone p where p.number like :num ")
    List<Phone> getPhoneLike(@Param("num") String num);

    List<Phone> findByNumberLike(String num);


}


