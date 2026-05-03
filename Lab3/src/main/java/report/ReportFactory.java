/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package report;

import entities.MissionEntity;

/**
 *
 * @author zubbo
 */

public class ReportFactory {
    
    public static String generateReport(MissionEntity mission, ReportType type) {
        if (mission == null) {
            return "No mission data available";
        }
        
        MissionReport visitor;
        
        switch (type) {
            case SUMMARY:
                visitor = new SummaryReport();
                break;
            case RISK:
                visitor = new RiskReport();
                break;
            case STATISTICS:
                visitor = new StatisticsReport();
                break;
            case DETAILED:
            default:
                visitor = new DetailedReport();
                break;
        }
        
        return visitor.visit(mission);
    }
}