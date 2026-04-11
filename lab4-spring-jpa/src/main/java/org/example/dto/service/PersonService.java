package org.example.dto.service;


import org.springframework.stereotype.Service;
import org.example.dto.dto.PersonDto;
import org.example.dto.model.Person;
import org.example.dto.model.Phone;
import org.example.dto.repository.PersonRepository;
import org.example.dto.repository.PhoneRepository;

import java.util.List;

@Service
public class PersonService {

    private final PhoneRepository phoneRepository;
    private final PersonRepository personRepository;

    public PersonService(PhoneRepository phoneRepository, PersonRepository personRepository) {
        this.phoneRepository = phoneRepository;
        this.personRepository = personRepository;
    }

    public void create(PersonDto personDto) {

        Phone phone = new Phone();
        phone.setNumber(personDto.getPhoneNumber());
        phoneRepository.save(phone);

        Person person = new Person();
        person.setName(personDto.getName());
        person.setPhone(phone);
        personRepository.save(person);

    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}

