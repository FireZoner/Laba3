/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import dto.*;
import entities.MissionEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import main.service.*;
import report.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author zubbo
 */

@RestController
@RequestMapping("/api/missions")
@Tag(name = "Mission Controller", description = "Управление архивом миссий")
public class MissionController {
    
    private final MissionService missionService;
    private final ParserService parserService;
    
    public MissionController(MissionService missionService, ParserService parserService) {
        this.missionService = missionService;
        this.parserService = parserService;
    }
    
    @Operation(summary = "Получить список всех миссий")
    @GetMapping
    public ResponseEntity<List<MissionResponse>> getAllMissions() {
        List<MissionResponse> missions = missionService.findAllMissions().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(missions);
    }
    
    @Operation(summary = "Получить миссию по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Миссия найдена"),
        @ApiResponse(responseCode = "404", description = "Миссия не найдена")
    })
    @GetMapping("/{missionId}")
    public ResponseEntity<MissionResponse> getMission(@PathVariable String missionId) {
        return missionService.findById(missionId)
            .map(this::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Загрузить файл миссии")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Миссия успешно загружена"),
        @ApiResponse(responseCode = "400", description = "Ошибка парсинга файла"),
        @ApiResponse(responseCode = "409", description = "Миссия с таким ID уже существует")
    })
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
    
    @Operation(summary = "Сгенерировать отчёт по миссии")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Отчёт сгенерирован"),
        @ApiResponse(responseCode = "404", description = "Миссия не найдена")
    })
    @PostMapping("/{missionId}/report")
    public ResponseEntity<String> getMissionReport(
            @PathVariable String missionId,
            @RequestBody ReportRequest request) {

        return missionService.findById(missionId)
            .map(mission -> {
                String report = ReportFactory.generateReport(mission, request.getType());
                return ResponseEntity.ok(report);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Частичное обновление миссии")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Миссия обновлена"),
        @ApiResponse(responseCode = "404", description = "Миссия не найдена")
    })
    @PatchMapping("/{missionId}")
    public ResponseEntity<?> partialUpdate(
            @PathVariable String missionId,
            @RequestBody Map<String, Object> updates) {

        try {
            if (!missionService.exists(missionId)) {
                return ResponseEntity.notFound().build();
            }

            MissionEntity updated = missionService.partialUpdate(missionId, updates);
            return ResponseEntity.ok(toResponse(updated));

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error updating mission: " + e.getMessage());
        }
    }
    
    @Operation(summary = "Удалить миссию")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Миссия удалена"),
        @ApiResponse(responseCode = "404", description = "Миссия не найдена")
    })
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
