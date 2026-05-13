package edu.hitsz.ui;

import edu.hitsz.application.Difficulty;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static edu.hitsz.application.Main.startGame;

public class DifficultySelectionPanel {
    private JButton 简单模式Button;
    private JButton 普通模式Button;
    private JButton 困难模式Button;
    private JPanel LeftSpace;
    private JPanel RightSpace;
    private JPanel MainPanel;

    public DifficultySelectionPanel() {
        简单模式Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.EASY);
            }
        });
        普通模式Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.NORMAL);
            }
        });
        困难模式Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.HARD);
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

}
