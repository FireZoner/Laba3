/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.service;

import entities.*;
import main.repository.MissionRepository;
import main.repository.SorcererRepository;
import main.repository.TechniqueRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author zubbo
 */
@Service
public class MissionService {
    private final MissionRepository missionRepository;
    private final SorcererRepository sorcererRepository;
    private final TechniqueRepository techniqueRepository;
    
    public MissionService(MissionRepository missionRepository,
                          SorcererRepository sorcererRepository,
                          TechniqueRepository techniqueRepository) {
        this.missionRepository = missionRepository;
        this.sorcererRepository = sorcererRepository;
        this.techniqueRepository = techniqueRepository;
    }
    
    @Transactional
    public MissionEntity saveMission(MissionEntity mission) {
        if (mission.getSorcerers() != null) {
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                SorcererEntity s = mission.getSorcerers().get(i);
                Optional<SorcererEntity> existing = sorcererRepository.findByName(s.getName());
                if (existing.isPresent()) {
                    mission.getSorcerers().set(i, existing.get());
                } else {
                    sorcererRepository.save(s);
                }
            }
        }
        
        if (mission.getTechniques() != null) {
            for (TechniqueEntity t : mission.getTechniques()) {
                if (t.getOwner() != null && t.getOwner().getId() == null) {
                    Optional<SorcererEntity> owner = sorcererRepository.findByName(t.getOwner().getName());
                    owner.ifPresent(t::setOwner);
                }
                techniqueRepository.save(t);
            }
        }
        
        return missionRepository.save(mission);
    }
    
    public Optional<MissionEntity> findById(String missionId) {
        return missionRepository.findById(missionId);
    }
    
    public List<MissionEntity> findAllMissions() {
        return missionRepository.findAll();
    }

    @Transactional
    public void deleteMission(String missionId) {
        missionRepository.deleteById(missionId);
    }
    
    public boolean exists(String missionId) {
        return missionRepository.existsById(missionId);
    }
}
