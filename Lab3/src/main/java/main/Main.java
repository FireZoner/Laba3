/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.*;

/**
 *
 * @author zubbo
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MissionAnalyzerGUI gui = new MissionAnalyzerGUI();
            gui.setVisible(true);
        });
    }
}