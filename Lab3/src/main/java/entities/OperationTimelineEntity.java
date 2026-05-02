/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.enums.TimelineEventType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "operation_timeline")
public class OperationTimelineEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimelineEventType type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private MissionEntity mission;

    public OperationTimelineEntity() {}

    public OperationTimelineEntity(LocalDateTime timestamp, TimelineEventType type, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public TimelineEventType getType() { return type; }
    public void setType(TimelineEventType type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}