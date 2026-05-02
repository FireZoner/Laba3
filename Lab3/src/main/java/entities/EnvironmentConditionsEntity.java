/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 *
 * @author zubbo
 */

@Embeddable
public class EnvironmentConditionsEntity {
    
    @Enumerated(EnumType.STRING)
    private Weather weather;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "time_of_day")
    private TimeOfDay timeOfDay;
    
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    
    @Column(name = "cursed_energy_density")
    private int cursedEnergyDensity;
    
    public EnvironmentConditionsEntity() {}
    
    public Weather getWeather() { return weather; }
    public void setWeather(Weather weather) { this.weather = weather; }
    
    public TimeOfDay getTimeOfDay() { return timeOfDay; }
    public void setTimeOfDay(TimeOfDay timeOfDay) { this.timeOfDay = timeOfDay; }
    
    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }
    
    public int getCursedEnergyDensity() { return cursedEnergyDensity; }
    public void setCursedEnergyDensity(int cursedEnergyDensity) { this.cursedEnergyDensity = cursedEnergyDensity; }
}
