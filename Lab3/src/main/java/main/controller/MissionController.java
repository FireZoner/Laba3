/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import dto.*;
import entities.MissionEntity;
import main.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author zubbo
 */

@RestController
@RequestMapping("/api/missions")
public class MissionController {
    
    private final MissionService missionService;
    private final ParserService parserService;
    
    public MissionController(MissionService missionService, ParserService parserService) {
        this.missionService = missionService;
        this.parserService = parserService;
    }
    
    @GetMapping
    public ResponseEntity<List<MissionResponse>> getAllMissions() {
        List<MissionResponse> missions = missionService.findAllMissions().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(missions);
    }
    
    @GetMapping("/{missionId}")
    public ResponseEntity<MissionResponse> getMission(@PathVariable String missionId) {
        return missionService.findById(missionId)
            .map(this::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMission(@RequestParam("file") MultipartFile file) {
        try {
            MissionEntity mission = parserService.parseMissionFile(file);
            
            if (missionService.exists(mission.getMissionId())) {
                return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Mission with ID " + mission.getMissionId() + " already exists");
            }
            
            MissionEntity saved = missionService.saveMission(mission);
            
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(saved));
            
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error parsing file: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable String missionId) {
        if (!missionService.exists(missionId)) {
            return ResponseEntity.notFound().build();
        }
        missionService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }
    
    private MissionResponse toResponse(MissionEntity mission) {
        return new MissionResponse.MissionResponseBuilder()
            .setMissionId(mission.getMissionId())
            .setDate(mission.getDate())
            .setLocation(mission.getLocation())
            .setOutcome(mission.getOutcome())
            .setDamageCost(mission.getDamageCost())
            .setSorcerersCount(mission.getSorcerers() != null ? mission.getSorcerers().size() : 0)
            .setTechniquesCount(mission.getTechniques() != null ? mission.getTechniques().size() : 0)
            .build();
    }
}
