/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import entities.MissionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ParserServiceTest {
    
    private ParserService parserService;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    public void setUp() {
        parserService = new ParserService();
    }
    
    @Test
    public void testParseJsonFile() throws Exception {
        // given
        String jsonContent = """
            {
              "missionId": "TEST-001",
              "date": "2024-01-01",
              "location": "Токио",
              "outcome": "SUCCESS",
              "damageCost": 1000000,
              "curse": {
                "name": "Тестовое проклятие",
                "threatLevel": "HIGH"
              },
              "sorcerers": [
                {"name": "Тестовый маг", "rank": "GRADE_1"}
              ],
              "techniques": [
                {"name": "Тестовая техника", "type": "INNATE", "owner": "Тестовый маг", "damage": 500000}
              ]
            }
            """;
        
        Path jsonFile = tempDir.resolve("test-mission.json");
        Files.writeString(jsonFile, jsonContent);
        
        MockMultipartFile multipartFile = new MockMultipartFile(
            "file",
            "test-mission.json",
            "application/json",
            Files.readAllBytes(jsonFile)
        );
        
        // when
        MissionEntity mission = parserService.parseMissionFile(multipartFile);
        
        // then
        assertNotNull(mission);
        assertEquals("TEST-001", mission.getMissionId());
        assertEquals(1, mission.getSorcerers().size());
        assertEquals(1, mission.getTechniques().size());
    }
    
    @Test
    public void testParseYamlFile() throws Exception {
        // given
        String yamlContent = """
            missionId: TEST-002
            date: 2024-01-01
            location: Токио
            outcome: SUCCESS
            damageCost: 1000000
            curse:
              name: Тестовое проклятие
              threatLevel: HIGH
            sorcerers:
              - name: Тестовый маг
                rank: GRADE_1
            techniques:
              - name: Тестовая техника
                type: INNATE
                owner: Тестовый маг
                damage: 500000
            """;
        
        Path yamlFile = tempDir.resolve("test-mission.yaml");
        Files.writeString(yamlFile, yamlContent);
        
        MockMultipartFile multipartFile = new MockMultipartFile(
            "file",
            "test-mission.yaml",
            "application/yaml",
            Files.readAllBytes(yamlFile)
        );
        
        // when
        MissionEntity mission = parserService.parseMissionFile(multipartFile);
        
        // then
        assertNotNull(mission);
        assertNotNull(mission.getMissionId());
    }
    
    @Test
    public void testParseTxtFileLegacy() throws Exception {
        // given
        String txtContent = """
            missionId: TEST-003
            date: 2024-01-01
            location: Токио
            outcome: SUCCESS
            damageCost: 1000000
            curse.name: Тестовое проклятие
            curse.threatLevel: HIGH
            sorcerer[0].name: Тестовый маг
            sorcerer[0].rank: GRADE_1
            technique[0].name: Тестовая техника
            technique[0].type: INNATE
            technique[0].owner: Тестовый маг
            technique[0].damage: 500000
            """;
        
        Path txtFile = tempDir.resolve("test-mission.txt");
        Files.writeString(txtFile, txtContent);
        
        MockMultipartFile multipartFile = new MockMultipartFile(
            "file",
            "test-mission.txt",
            "text/plain",
            Files.readAllBytes(txtFile)
        );
        
        // when
        MissionEntity mission = parserService.parseMissionFile(multipartFile);
        
        // then
        assertNotNull(mission);
        assertEquals("TEST-003", mission.getMissionId());
    }
}
