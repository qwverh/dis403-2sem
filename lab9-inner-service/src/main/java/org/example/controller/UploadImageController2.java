package org.example.controller;

import org.example.service.ImageService;
import org.example.service.ImageServiceForNats;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadImageController2 {

    private final ImageServiceForNats imageService;

    public UploadImageController2(ImageServiceForNats imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/uploadimg2", consumes="multipart/form-data")
    public String uploadImg(Model model, @RequestParam(value = "image", required = false) MultipartFile file) {
        model.addAttribute("imgs2", new ArrayList<String>());

        try {
            if (file != null) {
                System.out.println("получили картинку");
                imageService.processImage(file.getBytes());
            } else {
                System.out.println("нет изображения");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "uploadresult";
    }



}