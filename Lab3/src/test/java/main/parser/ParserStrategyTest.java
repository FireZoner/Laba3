/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.parser;

import parser.*;
import entities.MissionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ParserStrategyTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    public void testJsonParser() throws Exception {
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
        
        ParserDispatcher dispatcher = new ParserDispatcher();
        dispatcher.registerStrategy(new JsonParserStrategy());
        
        // when
        MissionEntity mission = dispatcher.parse(jsonFile.toFile());
        
        // then
        assertNotNull(mission);
        assertEquals("TEST-001", mission.getMissionId());
        assertEquals("Токио", mission.getLocation());
        assertEquals(1, mission.getSorcerers().size());
        assertEquals(1, mission.getTechniques().size());
    }
    
    @Test
    public void testYamlParser() throws Exception {
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
        
        ParserDispatcher dispatcher = new ParserDispatcher();
        dispatcher.registerStrategy(new YamlParserStrategy());
        
        // when
        MissionEntity mission = dispatcher.parse(yamlFile.toFile());
        
        // then
        assertNotNull(mission);
        assertNotNull(mission.getMissionId());
    }
    
    @Test
    public void testLegacyTxtParser() throws Exception {
        // given
        String txtContent = """
            missionId: TEST-004
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
        
        Path txtFile = tempDir.resolve("test-mission-legacy.txt");
        Files.writeString(txtFile, txtContent);
        
        ParserDispatcher dispatcher = new ParserDispatcher();
        dispatcher.registerStrategy(new LegacyTxtParserStrategy());
        
        // when
        MissionEntity mission = dispatcher.parse(txtFile.toFile());
        
        // then
        assertNotNull(mission);
        assertEquals("TEST-004", mission.getMissionId());
        assertEquals(1, mission.getSorcerers().size());
        assertEquals(1, mission.getTechniques().size());
    }
}