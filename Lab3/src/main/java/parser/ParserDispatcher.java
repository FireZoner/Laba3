/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parser;

import entities.*;
import builders.MissionBuilder;
import java.io.*;
import java.util.*;

/**
 * @author zubbo
 */
public class ParserDispatcher {
    private final List<ParserStrategy> strategies;
    
    public ParserDispatcher() {
        this.strategies = new ArrayList<>();
    }
    
    public void registerStrategy(ParserStrategy strategy) {
        strategies.add(strategy);
    }
    
    public void registerStrategies(List<ParserStrategy> strategies) {
        this.strategies.addAll(strategies);
    }
    
    public MissionEntity parse(File file) throws IOException {
        ParserStrategy selectedStrategy = strategies.stream()
            .filter(s -> s.supports(file))
            .findFirst()
            .orElseThrow(() -> new UnsupportedOperationException(
                "No parser found for file: " + file.getName()
            ));
        
        MissionBuilder builder = new MissionBuilder();
        return selectedStrategy.parse(file, builder);
    }
    
    public List<MissionEntity> parseBatch(List<File> files) throws IOException {
        List<MissionEntity> missions = new ArrayList<>();
        for (File file : files) {
            try {
                missions.add(parse(file));
            } catch (IOException e) {
                System.out.println("Error parsing " + file.getName() + ": " + e.getMessage());
            }
        }
        return missions;
    }
}
