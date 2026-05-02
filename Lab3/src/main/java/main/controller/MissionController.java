/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zubbo
 */

@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Mission Archive API is working!";
    }
}
