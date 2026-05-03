/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package builders;

import entities.enums.ThreatLevel;
import entities.enums.Outcome;
import entities.enums.Rank;
import entities.enums.TechniqueType;
import entities.*;

/**
 * Builder для создания Mission (Builder Pattern)
 * @author zubbo
 */
public class MissionBuilder {
    private final MissionEntity mission;
    
    public MissionBuilder() {
        this.mission = new MissionEntity();
    }
    
    public MissionBuilder setMissionId(String id) {
        mission.setMissionId(id);
        return this;
    }
    
    public MissionBuilder setDate(String date) {
        mission.setDate(date);
        return this;
    }
    
    public MissionBuilder setLocation(String location) {
        mission.setLocation(location);
        return this;
    }
    
    public MissionBuilder setOutcome(Outcome outcome) {
        mission.setOutcome(outcome);
        return this;
    }
    
    public MissionBuilder setDamageCost(long cost) {
        mission.setDamageCost(cost);
        return this;
    }
    
    public MissionBuilder setComment(String comment) {
        mission.setComment(comment);
        return this;
    }
    
    public MissionBuilder setCurse(CurseEntity curse) {
        mission.setCurse(curse);
        return this;
    }
    
    public MissionBuilder setCurse(String name, ThreatLevel level) {
        mission.setCurse(new CurseEntity(name, level));
        return this;
    }
    
    public MissionBuilder addSorcerer(SorcererEntity sorcerer) {
        mission.addSorcerer(sorcerer);
        return this;
    }
    
    public MissionBuilder addSorcerer(String name, Rank rank) {
        mission.addSorcerer(new SorcererEntity(name, rank));
        return this;
    }
    
    public MissionBuilder addTechnique(TechniqueEntity technique) {
        mission.addTechnique(technique);
        return this;
    }
    
    public MissionBuilder addTechnique(String name, TechniqueType type, String ownerName, long damage) {
        TechniqueEntity technique = new TechniqueEntity(name, type, damage);
        mission.addTechnique(technique);
        return this;
    }
    
    public MissionBuilder setEconomicAssessment(EconomicAssessmentEntity assessment) {
        mission.setEconomicAssessment(assessment);
        return this;
    }
    
    public MissionBuilder setEnemyActivity(EnemyActivityEntity activity) {
        mission.setEnemyActivity(activity);
        return this;
    }
    
    public MissionBuilder setEnvironmentConditions(EnvironmentConditionsEntity conditions) {
        mission.setEnvironmentConditions(conditions);
        return this;
    }
    
    public MissionBuilder setCivilianImpact(CivilianImpactEntity impact) {
        mission.setCivilianImpact(impact);
        return this;
    }
    
    public MissionBuilder addTimelineEvent(OperationTimelineEntity event) {
        mission.addTimelineEvent(event);
        return this;
    }
    
    public MissionBuilder addMissionTag(String tag) {
        mission.addMissionTag(tag);
        return this;
    }
    
    public MissionBuilder addSupportUnit(String unit) {
        mission.addSupportUnit(unit);
        return this;
    }
    
    public MissionBuilder addRecommendation(String rec) {
        mission.addRecommendation(rec);
        return this;
    }
    
    public MissionBuilder addArtifact(String artifact) {
        mission.addArtifact(artifact);
        return this;
    }
    
    public MissionBuilder addEvacuationZone(String zone) {
        mission.addEvacuationZone(zone);
        return this;
    }
    
    public MissionBuilder addStatusEffect(String effect) {
        mission.addStatusEffect(effect);
        return this;
    }
    
    public MissionEntity build() {
        for (TechniqueEntity technique : mission.getTechniques()) {
            if (technique.getOwnerName() != null && !technique.getOwnerName().isEmpty()) {
                for (SorcererEntity sorcerer : mission.getSorcerers()) {
                    if (sorcerer.getName().equals(technique.getOwnerName())) {
                        technique.setOwner(sorcerer);
                        break;
                    }
                }
            }
        }
        return mission;
    }
}