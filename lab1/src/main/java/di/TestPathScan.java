package di;

import di.config.Context;
import di.config.PathScan;

import java.util.List;

public class TestPathScan {
    public static void main(String[] args) {

/*
        List<Class<?>> classes = PathScan.find("ru.itis.dis403.lab2_1.di");
        classes.forEach(System.out::println);
*/
        new Context();

    }
}