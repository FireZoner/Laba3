/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import entities.*;
import entities.enums.*;
import main.repository.MissionRepository;
import main.repository.SorcererRepository;
import main.repository.TechniqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

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
        
        SorcererEntity sorcerer = new SorcererEntity("Тестовый маг", Rank.GRADE_1);
        testMission.setSorcerers(new ArrayList<>());
        testMission.getSorcerers().add(sorcerer);
        
        TechniqueEntity technique = new TechniqueEntity("Тестовая техника", TechniqueType.INNATE, 500000);
        technique.setOwnerName("Тестовый маг");
        testMission.setTechniques(new ArrayList<>());
        testMission.getTechniques().add(technique);
    }
    
    @Test
    public void testSaveMission() {
        when(missionRepository.save(any(MissionEntity.class))).thenReturn(testMission);
        
        MissionEntity saved = missionService.saveMission(testMission);
        
        assertNotNull(saved);
        assertEquals("TEST-001", saved.getMissionId());
        verify(missionRepository, times(1)).save(any(MissionEntity.class));
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
}