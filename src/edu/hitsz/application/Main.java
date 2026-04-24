package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 1. 输入玩家名称
        String playerName = JOptionPane.showInputDialog("请输入玩家名称:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "玩家";
        }

        // 2. 选择难度
        String[] difficultyOptions = {"简单", "普通", "困难"};
        int choice = JOptionPane.showOptionDialog(null,
                "请选择游戏难度",
                "难度选择",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficultyOptions,
                difficultyOptions[1]);

        Difficulty difficulty;
        switch (choice) {
            case 0:
                difficulty = Difficulty.EASY;
                break;
            case 2:
                difficulty = Difficulty.HARD;
                break;
            default:
                difficulty = Difficulty.NORMAL;
                break;
        }

        System.out.println("欢迎玩家: " + playerName);
        System.out.println("游戏难度: " + difficulty.getName());

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game(playerName, difficulty);
        frame.add(game);
        frame.setVisible(true);
        game.action();

        // 游戏结束后显示排行榜
        game.showRanking();
        game.showPlayerHistory();

    }
}
