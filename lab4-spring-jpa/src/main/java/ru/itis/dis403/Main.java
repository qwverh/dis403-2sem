package ru.itis.dis403;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.config.Config;
import ru.itis.dis403.model.Phone;
import ru.itis.dis403.repository.PhoneRepository;
import ru.itis.dis403.service.PhoneService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);


    }
}