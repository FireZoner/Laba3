/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.service;

import entities.*;
import entities.enums.*;
import java.time.LocalDateTime;
import main.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    
    @Transactional
    public MissionEntity partialUpdate(String missionId, Map<String, Object> updates) {
        MissionEntity mission = missionRepository.findById(missionId)
            .orElseThrow(() -> new RuntimeException("Mission not found: " + missionId));

        updates.forEach((key, value) -> {
            if (value == null) return;

            switch (key) {
                case "date" -> mission.setDate((String) value);
                case "location" -> mission.setLocation((String) value);
                case "outcome" -> mission.setOutcome(Outcome.valueOf((String) value));
                case "damageCost" -> mission.setDamageCost(((Number) value).longValue());
                case "comment" -> mission.setComment((String) value);
                case "curse" -> {
                    Map<String, Object> curseMap = (Map<String, Object>) value;
                    CurseEntity curse = mission.getCurse();
                    if (curse == null) {
                        curse = new CurseEntity();
                    }
                    if (curseMap.containsKey("name")) {
                        curse.setName((String) curseMap.get("name"));
                    }
                    if (curseMap.containsKey("threatLevel")) {
                        curse.setThreatLevel(ThreatLevel.valueOf((String) curseMap.get("threatLevel")));
                    }
                    mission.setCurse(curse);
                }
                case "economicAssessment" -> {
                    Map<String, Object> eaMap = (Map<String, Object>) value;
                    EconomicAssessmentEntity ea = mission.getEconomicAssessment();
                    if (ea == null) {
                        ea = new EconomicAssessmentEntity();
                    }
                    if (eaMap.containsKey("totalDamageCost")) {
                        ea.setTotalDamageCost(((Number) eaMap.get("totalDamageCost")).longValue());
                    }
                    if (eaMap.containsKey("infrastructureDamage")) {
                        ea.setInfrastructureDamage(((Number) eaMap.get("infrastructureDamage")).longValue());
                    }
                    if (eaMap.containsKey("commercialDamage")) {
                        ea.setCommercialDamage(((Number) eaMap.get("commercialDamage")).longValue());
                    }
                    if (eaMap.containsKey("transportDamage")) {
                        ea.setTransportDamage(((Number) eaMap.get("transportDamage")).longValue());
                    }
                    if (eaMap.containsKey("recoveryEstimateDays")) {
                        ea.setRecoveryEstimateDays(((Number) eaMap.get("recoveryEstimateDays")).intValue());
                    }
                    if (eaMap.containsKey("insuranceCovered")) {
                        ea.setInsuranceCovered((Boolean) eaMap.get("insuranceCovered"));
                    }
                    mission.setEconomicAssessment(ea);
                }
                case "enemyActivity" -> {
                    Map<String, Object> enemyMap = (Map<String, Object>) value;
                    EnemyActivityEntity enemy = mission.getEnemyActivity();
                    if (enemy == null) {
                        enemy = new EnemyActivityEntity();
                    }
                    if (enemyMap.containsKey("behaviorType")) {
                        enemy.setBehaviorType(BehaviorType.valueOf((String) enemyMap.get("behaviorType")));
                    }
                    if (enemyMap.containsKey("targetPriority")) {
                        enemy.setTargetPriority((String) enemyMap.get("targetPriority"));
                    }
                    if (enemyMap.containsKey("mobility")) {
                        enemy.setMobility(Mobility.valueOf((String) enemyMap.get("mobility")));
                    }
                    if (enemyMap.containsKey("escalationRisk")) {
                        enemy.setEscalationRisk(EscalationRisk.valueOf((String) enemyMap.get("escalationRisk")));
                    }
                    if (enemyMap.containsKey("attackPatterns")) {
                        List<String> patterns = (List<String>) enemyMap.get("attackPatterns");
                        enemy.setAttackPatterns(patterns);
                    }
                    mission.setEnemyActivity(enemy);
                }
                case "environmentConditions" -> {
                    Map<String, Object> envMap = (Map<String, Object>) value;
                    EnvironmentConditionsEntity env = mission.getEnvironmentConditions();
                    if (env == null) {
                        env = new EnvironmentConditionsEntity();
                    }
                    if (envMap.containsKey("weather")) {
                        env.setWeather(Weather.valueOf((String) envMap.get("weather")));
                    }
                    if (envMap.containsKey("timeOfDay")) {
                        env.setTimeOfDay(TimeOfDay.valueOf((String) envMap.get("timeOfDay")));
                    }
                    if (envMap.containsKey("visibility")) {
                        env.setVisibility(Visibility.valueOf((String) envMap.get("visibility")));
                    }
                    if (envMap.containsKey("cursedEnergyDensity")) {
                        env.setCursedEnergyDensity(((Number) envMap.get("cursedEnergyDensity")).intValue());
                    }
                    mission.setEnvironmentConditions(env);
                }
                case "civilianImpact" -> {
                    Map<String, Object> ciMap = (Map<String, Object>) value;
                    CivilianImpactEntity ci = mission.getCivilianImpact();
                    if (ci == null) {
                        ci = new CivilianImpactEntity();
                    }
                    if (ciMap.containsKey("evacuated")) {
                        ci.setEvacuated(((Number) ciMap.get("evacuated")).intValue());
                    }
                    if (ciMap.containsKey("injured")) {
                        ci.setInjured(((Number) ciMap.get("injured")).intValue());
                    }
                    if (ciMap.containsKey("missing")) {
                        ci.setMissing(((Number) ciMap.get("missing")).intValue());
                    }
                    if (ciMap.containsKey("publicExposureRisk")) {
                        ci.setPublicExposureRisk(PublicExposureRisk.valueOf((String) ciMap.get("publicExposureRisk")));
                    }
                    mission.setCivilianImpact(ci);
                }
                case "addMissionTags" -> {
                    List<String> addTags = (List<String>) value;
                    mission.getMissionTags().addAll(addTags);
                }
                case "removeMissionTags" -> {
                    List<String> removeTags = (List<String>) value;
                    mission.getMissionTags().removeAll(removeTags);
                }
                case "addSupportUnits" -> {
                    List<String> addUnits = (List<String>) value;
                    mission.getSupportUnits().addAll(addUnits);
                }
                case "removeSupportUnits" -> {
                    List<String> removeUnits = (List<String>) value;
                    mission.getSupportUnits().removeAll(removeUnits);
                }
                case "addRecommendations" -> {
                    List<String> addRecs = (List<String>) value;
                    mission.getRecommendations().addAll(addRecs);
                }
                case "removeRecommendations" -> {
                    List<String> removeRecs = (List<String>) value;
                    mission.getRecommendations().removeAll(removeRecs);
                }
                case "addArtifacts" -> {
                    List<String> addArts = (List<String>) value;
                    mission.getArtifactsRecovered().addAll(addArts);
                }
                case "removeArtifacts" -> {
                    List<String> removeArts = (List<String>) value;
                    mission.getArtifactsRecovered().removeAll(removeArts);
                }
                case "addEvacuationZones" -> {
                    List<String> addZones = (List<String>) value;
                    mission.getEvacuationZones().addAll(addZones);
                }
                case "removeEvacuationZones" -> {
                    List<String> removeZones = (List<String>) value;
                    mission.getEvacuationZones().removeAll(removeZones);
                }
                case "addStatusEffects" -> {
                    List<String> addEffects = (List<String>) value;
                    mission.getStatusEffects().addAll(addEffects);
                }
                case "removeStatusEffects" -> {
                    List<String> removeEffects = (List<String>) value;
                    mission.getStatusEffects().removeAll(removeEffects);
                }
            }
        });
        mission.setUpdatedAt(LocalDateTime.now());
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
