/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *
 * @author zubbo
 */

@SpringBootApplication
@EntityScan(basePackages = {"entities"})
public class Laba3Application {
    public static void main(String[] args) {
        SpringApplication.run(Laba3Application.class, args);
    }
}