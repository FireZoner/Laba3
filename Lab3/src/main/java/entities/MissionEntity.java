/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enums.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "missions")
public class MissionEntity {
    
    @Id
    @Column(name = "mission_id", nullable = false, length = 100)
    private String missionId;

    @Column(name = "mission_date", nullable = false)
    private String date;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Outcome outcome;

    @Column(name = "damage_cost")
    private long damageCost;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Embedded
    private CurseEntity curse;

    @Embedded
    private EconomicAssessmentEntity economicAssessment;

    @Embedded
    private EnemyActivityEntity enemyActivity;

    @Embedded
    private EnvironmentConditionsEntity environmentConditions;

    @Embedded
    private CivilianImpactEntity civilianImpact;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OperationTimelineEntity> operationTimeline = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "mission_sorcerers",
        joinColumns = @JoinColumn(name = "mission_id"),
        inverseJoinColumns = @JoinColumn(name = "sorcerer_id")
    )
    private List<SorcererEntity> sorcerers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "mission_techniques",
        joinColumns = @JoinColumn(name = "mission_id"),
        inverseJoinColumns = @JoinColumn(name = "technique_id")
    )
    private List<TechniqueEntity> techniques = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_tags", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "tag")
    private List<String> missionTags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_support_units", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "unit")
    private List<String> supportUnits = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_recommendations", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "recommendation")
    private List<String> recommendations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_artifacts", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "artifact")
    private List<String> artifactsRecovered = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_evacuation_zones", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "zone")
    private List<String> evacuationZones = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_status_effects", joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "effect")
    private List<String> statusEffects = new ArrayList<>();

    public MissionEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public long getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(long damageCost) {
        this.damageCost = damageCost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CurseEntity getCurse() {
        return curse;
    }

    public void setCurse(CurseEntity curse) {
        this.curse = curse;
    }

    public EconomicAssessmentEntity getEconomicAssessment() {
        return economicAssessment;
    }

    public void setEconomicAssessment(EconomicAssessmentEntity economicAssessment) {
        this.economicAssessment = economicAssessment;
    }

    public EnemyActivityEntity getEnemyActivity() {
        return enemyActivity;
    }

    public void setEnemyActivity(EnemyActivityEntity enemyActivity) {
        this.enemyActivity = enemyActivity;
    }

    public EnvironmentConditionsEntity getEnvironmentConditions() {
        return environmentConditions;
    }

    public void setEnvironmentConditions(EnvironmentConditionsEntity environmentConditions) {
        this.environmentConditions = environmentConditions;
    }

    public CivilianImpactEntity getCivilianImpact() {
        return civilianImpact;
    }

    public void setCivilianImpact(CivilianImpactEntity civilianImpact) {
        this.civilianImpact = civilianImpact;
    }

    public List<OperationTimelineEntity> getOperationTimeline() {
        return operationTimeline;
    }

    public void setOperationTimeline(List<OperationTimelineEntity> operationTimeline) {
        this.operationTimeline = operationTimeline;
    }

    public void addTimelineEvent(OperationTimelineEntity event) {
        this.operationTimeline.add(event);
    }

    public List<SorcererEntity> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(List<SorcererEntity> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public void addSorcerer(SorcererEntity sorcerer) {
        this.sorcerers.add(sorcerer);
        sorcerer.getMissions().add(this);
    }

    public List<TechniqueEntity> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<TechniqueEntity> techniques) {
        this.techniques = techniques;
    }

    public void addTechnique(TechniqueEntity technique) {
        this.techniques.add(technique);
        technique.getMissions().add(this);
    }

    public List<String> getMissionTags() {
        return missionTags;
    }

    public void setMissionTags(List<String> missionTags) {
        this.missionTags = missionTags;
    }

    public void addMissionTag(String tag) {
        this.missionTags.add(tag);
    }

    public List<String> getSupportUnits() {
        return supportUnits;
    }

    public void setSupportUnits(List<String> supportUnits) {
        this.supportUnits = supportUnits;
    }

    public void addSupportUnit(String unit) {
        this.supportUnits.add(unit);
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public void addRecommendation(String recommendation) {
        this.recommendations.add(recommendation);
    }

    public List<String> getArtifactsRecovered() {
        return artifactsRecovered;
    }

    public void setArtifactsRecovered(List<String> artifactsRecovered) {
        this.artifactsRecovered = artifactsRecovered;
    }

    public void addArtifact(String artifact) {
        this.artifactsRecovered.add(artifact);
    }

    public List<String> getEvacuationZones() {
        return evacuationZones;
    }

    public void setEvacuationZones(List<String> evacuationZones) {
        this.evacuationZones = evacuationZones;
    }

    public void addEvacuationZone(String zone) {
        this.evacuationZones.add(zone);
    }

    public List<String> getStatusEffects() {
        return statusEffects;
    }

    public void setStatusEffects(List<String> statusEffects) {
        this.statusEffects = statusEffects;
    }

    public void addStatusEffect(String effect) {
        this.statusEffects.add(effect);
    }
}