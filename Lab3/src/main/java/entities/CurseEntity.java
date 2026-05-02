/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enums.ThreatLevel;
import jakarta.persistence.*;

/**
 *
 * @author zubbo
 */
@Embeddable
public class CurseEntity {
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private ThreatLevel threatLevel;
    
    public CurseEntity() {}
    
    public CurseEntity(String name, ThreatLevel threatLevel) {
        this.name = name;
        this.threatLevel = threatLevel;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ThreatLevel getThreatLevel() {
        return threatLevel;
    }
    
    public void setThreatLevel(ThreatLevel threatLevel) {
        this.threatLevel = threatLevel;
    }
}