package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Service
public class ImageServiceForNats {

    // сформировать клиент к брокеру сообщений
    // отправить сообщение

    String natsUrl = "nats://localhost:4222";
    String subject = "request.json";

    public String processImage(byte[] image) {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("service", "resize");
        requestMap.put("image", Base64.getEncoder().encodeToString(image));



    }


}