/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MissionControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testGetMissionNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/api/missions/NOT-EXISTS", 
            String.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
