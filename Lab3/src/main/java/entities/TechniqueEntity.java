/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.enums.TechniqueType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "techniques")
public class TechniqueEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechniqueType type;

    private long damage;
    
    @Transient
    private String ownerName;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private SorcererEntity owner;

    @JsonIgnore
    @ManyToMany(mappedBy = "techniques")
    private List<MissionEntity> missions = new ArrayList<>();
    
    public TechniqueEntity() {}

    public TechniqueEntity(String name, TechniqueType type, long damage) {
        this.name = name;
        this.type = type;
        this.damage = damage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public TechniqueType getType() { return type; }
    public void setType(TechniqueType type) { this.type = type; }
    
    public long getDamage() { return damage; }
    public void setDamage(long damage) { this.damage = damage; }
    
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    
    public SorcererEntity getOwner() { return owner; }
    public void setOwner(SorcererEntity owner) { this.owner = owner; }
    
    public List<MissionEntity> getMissions() { return missions; }
    public void setMissions(List<MissionEntity> missions) { this.missions = missions; }
}
