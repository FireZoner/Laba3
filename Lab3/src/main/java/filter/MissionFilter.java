/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import model.Mission;

/**
 *
 * @author zubbo
 */

public interface MissionFilter {
    MissionFilter setNext(MissionFilter nextFilter);

    void filter(Mission mission) throws Exception;
}