/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.enums.Rank;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zubbo
 */
@Entity
@Table(name = "sorcerers")
public class SorcererEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank rank;

    @JsonIgnore
    @ManyToMany(mappedBy = "sorcerers")
    private List<MissionEntity> missions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TechniqueEntity> techniques = new ArrayList<>();

    public SorcererEntity() {}

    public SorcererEntity(String name, Rank rank) {
        this.name = name;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<MissionEntity> getMissions() {
        return missions;
    }

    public void setMissions(List<MissionEntity> missions) {
        this.missions = missions;
    }

    public List<TechniqueEntity> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<TechniqueEntity> techniques) {
        this.techniques = techniques;
    }

    public void addTechnique(TechniqueEntity technique) {
        this.techniques.add(technique);
        technique.setOwner(this);
    }
}
