/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import entities.enums.Outcome;

/**
 *
 * @author zubbo
 */
public class MissionResponse {
    private final String missionId;
    private final String date;
    private final String location;
    private final Outcome outcome;
    private final long damageCost;
    private final int sorcerersCount;
    private final int techniquesCount;
    
    public MissionResponse(MissionResponseBuilder builder) {
        this.missionId = builder.missionId;
        this.date = builder.date;
        this.location = builder.location;
        this.outcome = builder.outcome;
        this.damageCost = builder.damageCost;
        this.sorcerersCount = builder.sorcerersCount;
        this.techniquesCount = builder.techniquesCount;
    }
    
    public String getMissionId() { return missionId; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public Outcome getOutcome() { return outcome; }
    public long getDamageCost() { return damageCost; }
    public int getSorcerersCount() { return sorcerersCount; }
    public int getTechniquesCount() { return techniquesCount; }
    
    public static class MissionResponseBuilder {
        private String missionId;
        private String date;
        private String location;
        private Outcome outcome;
        private long damageCost;
        private int sorcerersCount;
        private int techniquesCount;
        
        public MissionResponseBuilder setMissionId(String missionId) { this.missionId = missionId; return this; }
        public MissionResponseBuilder setDate(String date) { this.date = date; return this; }
        public MissionResponseBuilder setLocation(String location) { this.location = location; return this; }
        public MissionResponseBuilder setOutcome(Outcome outcome) { this.outcome = outcome; return this; }
        public MissionResponseBuilder setDamageCost(long damageCost) { this.damageCost = damageCost; return this; }
        public MissionResponseBuilder setSorcerersCount(int sorcerersCount) { this.sorcerersCount = sorcerersCount; return this; }
        public MissionResponseBuilder setTechniquesCount(int techniquesCount) { this.techniquesCount = techniquesCount; return this; }
        
        public MissionResponse build() {
            return new MissionResponse(this);
        }
    }
}
