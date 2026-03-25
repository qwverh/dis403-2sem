package ru.itis.dis403.service;


import org.springframework.stereotype.Service;
import ru.itis.dis403.dto.PersonDto;
import ru.itis.dis403.model.Person;
import ru.itis.dis403.model.Phone;
import ru.itis.dis403.repository.PersonRepository;
import ru.itis.dis403.repository.PhoneRepository;

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

