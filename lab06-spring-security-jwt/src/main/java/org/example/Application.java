package org.example;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("user"));
        System.out.println(encoder.encode("admin"));


        ApplicationContext context = SpringApplication.run(Application.class, args);





    }

}