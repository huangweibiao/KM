package com.example.km;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Knowledge Management System Application
 */
@SpringBootApplication
public class KmApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmApplication.class, args);
        System.out.println("=====================================");
        System.out.println("Knowledge Management System Started!");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("=====================================");
    }
}
