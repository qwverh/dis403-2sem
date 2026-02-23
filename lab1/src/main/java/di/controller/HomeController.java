package di.controller;


import di.annotation.Controller;
import di.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping(value = "/home")
    public void home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write("эта страница по ресурсу home");
    }

}
