/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author zubbo
 */
@Embeddable
public class EconomicAssessmentEntity {
    
    @Column(name = "economic_total_damage_cost")
    private long totalDamageCost;
    
    @Column(name = "economic_infrastructure_damage")
    private long infrastructureDamage;
    
    @Column(name = "economic_commercial_damage")
    private long commercialDamage;
    
    @Column(name = "economic_transport_damage")
    private long transportDamage;
    
    @Column(name = "economic_recovery_estimate_days")
    private int recoveryEstimateDays;
    
    @Column(name = "economic_insurance_covered")
    private boolean insuranceCovered;
    
    public EconomicAssessmentEntity() {}

    public long getTotalDamageCost() { return totalDamageCost; }
    public void setTotalDamageCost(long totalDamageCost) { this.totalDamageCost = totalDamageCost; }
    
    public long getInfrastructureDamage() { return infrastructureDamage; }
    public void setInfrastructureDamage(long infrastructureDamage) { this.infrastructureDamage = infrastructureDamage; }
    
    public long getCommercialDamage() { return commercialDamage; }
    public void setCommercialDamage(long commercialDamage) { this.commercialDamage = commercialDamage; }
    
    public long getTransportDamage() { return transportDamage; }
    public void setTransportDamage(long transportDamage) { this.transportDamage = transportDamage; }
    
    public int getRecoveryEstimateDays() { return recoveryEstimateDays; }
    public void setRecoveryEstimateDays(int recoveryEstimateDays) { this.recoveryEstimateDays = recoveryEstimateDays; }
    
    public boolean isInsuranceCovered() { return insuranceCovered; }
    public void setInsuranceCovered(boolean insuranceCovered) { this.insuranceCovered = insuranceCovered; }
}
