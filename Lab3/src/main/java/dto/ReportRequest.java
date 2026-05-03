/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import report.*;

/**
 *
 * @author zubbo
 */
public class ReportRequest {
    private ReportType type;
    
    public ReportRequest() {}
    
    public ReportRequest(ReportType type) {
        this.type = type;
    }
    
    public ReportType getType() {
        return type;
    }
    
    public void setType(ReportType type) {
        this.type = type;
    }
}
