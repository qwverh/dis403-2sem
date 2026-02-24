package di.controller;


import di.annotation.Controller;
import di.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class IndexController {

    @GetMapping(value = "/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE html>");
        writer.write("<head></head>");
        writer.write("<body> <h1>страничка с ресурсом index</h1></body></html>");
//        response.getWriter().write("эта страница по ресурсу index");
    }
}
