package ru.itis.dis403.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dis403.dto.PersonDto;
import ru.itis.dis403.model.Person;
import ru.itis.dis403.service.PersonService;

import java.util.List;

@Controller
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons/create")
    public String index() {
        return "create_person";
    }

    @PostMapping("/persons/create")
    public String create(PersonDto personDto) {
        personService.create(personDto);
        return "redirect:/persons";
    }

    @GetMapping("/persons")
    public String persons(Model model) {
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "persons";
    }
}