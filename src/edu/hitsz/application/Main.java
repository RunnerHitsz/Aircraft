package edu.hitsz.application;

import edu.hitsz.ui.DifficultySelectionPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    // 存储用户选择的难度
    private static Difficulty selectedDifficulty = Difficulty.NORMAL;
    private static String playerName = "玩家";

    public static void main(String[] args) {

        // ✅ 启动难度选择界面（等待用户选择）
        showDifficultySelection();
    }

    private static void showDifficultySelection() {
        JFrame frame = new JFrame("选择难度");
        DifficultySelectionPanel panel = new DifficultySelectionPanel();

        frame.setContentPane(panel.getMainPanel());
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void startGame(Difficulty difficulty) {
        // 关闭难度选择窗口
        for (Frame frame : Frame.getFrames()) {
            if (frame.getTitle().equals("选择难度")) {
                frame.dispose();
                break;
            }
        }

        // 输入玩家名称
        String playerName = JOptionPane.showInputDialog("请输入玩家名称:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "玩家";
        }

        System.out.println("欢迎玩家: " + playerName);
        System.out.println("游戏难度: " + difficulty.getName());

        // 启动游戏窗口
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War - " + playerName);
        Game game = new Game(playerName, difficulty);
        frame.add(game);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.action();

        // 等待游戏结束
        new Thread(() -> {
            while (!game.isGameOver()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


