/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import entities.*;
import entities.enums.*;
import main.repository.MissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import main.repository.SorcererRepository;
import main.repository.TechniqueRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author zubbo
 */
@ExtendWith(MockitoExtension.class)
public class MissionServiceTest {
    
    @Mock
    private MissionRepository missionRepository;
    
    @Mock
    private SorcererRepository sorcererRepository;
    
    @Mock
    private TechniqueRepository techniqueRepository;
    
    @InjectMocks
    private MissionService missionService;
    
    private MissionEntity testMission;
    
    @BeforeEach
    public void setUp() {
        testMission = new MissionEntity();
        testMission.setMissionId("TEST-001");
        testMission.setDate("2024-01-01");
        testMission.setLocation("Токио");
        testMission.setOutcome(Outcome.SUCCESS);
        testMission.setDamageCost(1000000);
        testMission.setComment("Оригинальный комментарий");
        
        testMission.setMissionTags(new ArrayList<>());
        testMission.getMissionTags().add("тег1");
        testMission.getMissionTags().add("тег2");
        
        testMission.setSupportUnits(new ArrayList<>());
        testMission.getSupportUnits().add("юнит1");
        
        testMission.setCurse(new CurseEntity("Старое проклятие", ThreatLevel.HIGH));
        
        EconomicAssessmentEntity ea = new EconomicAssessmentEntity();
        ea.setTotalDamageCost(1000000);
        testMission.setEconomicAssessment(ea);
        
        // Маги
        SorcererEntity sorcerer = new SorcererEntity("Тестовый маг", Rank.GRADE_1);
        sorcerer.setId(1L);
        testMission.setSorcerers(new ArrayList<>());
        testMission.getSorcerers().add(sorcerer);
        
        // Техники
        TechniqueEntity technique = new TechniqueEntity("Тестовая техника", TechniqueType.INNATE, 500000);
        technique.setOwnerName("Тестовый маг");
        testMission.setTechniques(new ArrayList<>());
        testMission.getTechniques().add(technique);
    }
    
    @Test
    public void testSaveMission() {
        // given
        when(sorcererRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(sorcererRepository.save(any(SorcererEntity.class))).thenAnswer(invocation -> {
            SorcererEntity s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });
        when(techniqueRepository.save(any(TechniqueEntity.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });
        when(missionRepository.save(any(MissionEntity.class))).thenReturn(testMission);
        
        // when
        MissionEntity saved = missionService.saveMission(testMission);
        
        // then
        assertNotNull(saved);
        assertEquals("TEST-001", saved.getMissionId());
        verify(missionRepository, atLeastOnce()).save(any(MissionEntity.class));
    }
    
    @Test
    public void testFindById() {
        when(missionRepository.findById("TEST-001")).thenReturn(Optional.of(testMission));
        
        Optional<MissionEntity> found = missionService.findById("TEST-001");
        
        assertTrue(found.isPresent());
        assertEquals("TEST-001", found.get().getMissionId());
    }
    
    @Test
    public void testExists() {
        when(missionRepository.existsById("TEST-001")).thenReturn(true);
        
        boolean exists = missionService.exists("TEST-001");
        
        assertTrue(exists);
    }
    
    @Test
    public void testExistsNotFound() {
        when(missionRepository.existsById("NOT-EXISTS")).thenReturn(false);
        
        boolean exists = missionService.exists("NOT-EXISTS");
        
        assertFalse(exists);
    }
    
    @Test
    public void testDeleteMission() {
        doNothing().when(missionRepository).deleteById("TEST-001");
        
        missionService.deleteMission("TEST-001");
        
        verify(missionRepository, times(1)).deleteById("TEST-001");
    }
    
    @Test
    public void testPartialUpdateBasicFields() {
        when(missionRepository.findById("TEST-001")).thenReturn(Optional.of(testMission));
        when(missionRepository.save(any(MissionEntity.class))).thenReturn(testMission);
        
        Map<String, Object> updates = new HashMap<>();
        updates.put("location", "Осака");
        updates.put("comment", "Новый комментарий");
        
        MissionEntity updated = missionService.partialUpdate("TEST-001", updates);
        
        assertNotNull(updated);
        assertEquals("Осака", updated.getLocation());
        assertEquals("Новый комментарий", updated.getComment());
    }
    
    @Test
    public void testPartialUpdateNotFound() {
        // TODO: PATCH тест требует специальной настройки
    }
}