/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import entities.MissionEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import parser.*;
import java.io.File;

/**
 *
 * @author zubbo
 */
@Service
public class ParserService {
    
    private final ParserDispatcher dispatcher;
    
    public ParserService() {
        this.dispatcher = new ParserDispatcher();
        dispatcher.registerStrategy(new JsonParserStrategy());
        dispatcher.registerStrategy(new YamlParserStrategy());
        dispatcher.registerStrategy(new XmlParserStrategy());
        dispatcher.registerStrategy(new LegacyTxtParserStrategy());
        dispatcher.registerStrategy(new TxtParserStrategy());
        dispatcher.registerStrategy(new EmptyParserStrategy());
    }
    
    public MissionEntity parseMissionFile(MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("mission_", "_" + file.getOriginalFilename());
        file.transferTo(tempFile);
        
        try {
            MissionEntity mission = dispatcher.parse(tempFile);
            return mission;
        } finally {
            tempFile.delete();
        }
    }
}