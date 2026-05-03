/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enums.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zubbo
 */
@Embeddable
public class EnemyActivityEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "behavior_type")
    private BehaviorType behaviorType;
    
    @Column(name = "target_priority")
    private String targetPriority;
    
    @Enumerated(EnumType.STRING)
    private Mobility mobility;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "escalation_risk")
    private EscalationRisk escalationRisk;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "enemy_attack_patterns", joinColumns = @JoinColumn(name = "enemy_activity_id"))
    @Column(name = "pattern")
    private List<String> attackPatterns = new ArrayList<>();
    
    public EnemyActivityEntity() {
    }
    
    public BehaviorType getBehaviorType() { return behaviorType; }
    public void setBehaviorType(BehaviorType behaviorType) { this.behaviorType = behaviorType; }
    
    public String getTargetPriority() { return targetPriority; }
    public void setTargetPriority(String targetPriority) { this.targetPriority = targetPriority; }
    
    public Mobility getMobility() { return mobility; }
    public void setMobility(Mobility mobility) { this.mobility = mobility; }
    
    public EscalationRisk getEscalationRisk() { return escalationRisk; }
    public void setEscalationRisk(EscalationRisk escalationRisk) { this.escalationRisk = escalationRisk; }
    
    public List<String> getAttackPatterns() { return attackPatterns; }
    public void setAttackPatterns(List<String> attackPatterns) { this.attackPatterns = attackPatterns; }
    
    public void addAttackPattern(String attackPattern) {
        this.attackPatterns.add(attackPattern);
    }
}
