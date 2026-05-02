/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enums.PublicExposureRisk;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 *
 * @author zubbo
 */

@Embeddable
public class CivilianImpactEntity {
    
    private int evacuated;
    private int injured;
    private int missing;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "public_exposure_risk")
    private PublicExposureRisk publicExposureRisk;
    
    public CivilianImpactEntity() {
    }
    
    public int getEvacuated() { return evacuated; }
    public void setEvacuated(int evacuated) { this.evacuated = evacuated; }
    
    public int getInjured() { return injured; }
    public void setInjured(int injured) { this.injured = injured; }
    
    public int getMissing() { return missing; }
    public void setMissing(int missing) { this.missing = missing; }
    
    public PublicExposureRisk getPublicExposureRisk() { return publicExposureRisk; }
    public void setPublicExposureRisk(PublicExposureRisk publicExposureRisk) { this.publicExposureRisk = publicExposureRisk; }
}