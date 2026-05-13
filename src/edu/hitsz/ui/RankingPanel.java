package edu.hitsz.ui;

import edu.hitsz.DAO.ScoreDao;
import edu.hitsz.DAO.ScoreDaoImpl;
import edu.hitsz.DAO.ScoreRecord;
import edu.hitsz.application.Difficulty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RankingPanel {
    private JPanel MainPanel;
    private JButton DeltePanel;
    private JPanel TopPanel;
    private JPanel ButtonPanel;
    private JScrollPane EasyScorllPane;
    private JTable table1;
    private JLabel 难度label;
    private JButton BacktoDifSelect;
    private JButton MedianShift;
    private JButton EasyShift;
    private JButton HardShift;
    private JPanel DifShiftPanel;

    private DefaultTableModel tableModel;
    private ScoreDao scoreDao;
    private Difficulty currentDifficulty;  // 当前显示的难度
    private String playerName;
    private int currentScore;

    public RankingPanel(Difficulty defaultDifficulty, String playerName, int currentScore) {
        this.currentDifficulty = defaultDifficulty;
        this.playerName = playerName;
        this.currentScore = currentScore;

        // 根据传入的难度初始化 DAO
        this.scoreDao = new ScoreDaoImpl(currentDifficulty.getName());

        // 更新难度标签
        难度label.setText("Mode: " + currentDifficulty.getName());

        // 初始化表格
        String[] columns = {"名次", "玩家", "得分", "时间"};
        tableModel = new DefaultTableModel(columns, 0);
        table1.setModel(tableModel);
        EasyScorllPane.setViewportView(table1);

        // 加载数据
        refreshTable();

        // 删除按钮事件
        DeltePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });

        // 返回按钮事件
        BacktoDifSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToDifficultySelection();
            }
        });

        //  简单难度按钮
        EasyShift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchDifficulty(Difficulty.EASY);
            }
        });

        //  普通难度按钮
        MedianShift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchDifficulty(Difficulty.NORMAL);
            }
        });

        //  困难难度按钮
        HardShift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchDifficulty(Difficulty.HARD);
            }
        });
    }

    // 切换难度的方法
    private void switchDifficulty(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
        this.scoreDao = new ScoreDaoImpl(difficulty.getName());

        // 更新标题
        难度label.setText("Mode: " + difficulty.getName());

        // 刷新表格
        refreshTable();
    }

    private void backToDifficultySelection() {
        // 关闭当前排行榜窗口
        SwingUtilities.getWindowAncestor(MainPanel).dispose();
        // 重新打开难度选择界面
        edu.hitsz.application.Main.main(new String[0]);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<ScoreRecord> list = scoreDao.getTopN(10);
        for (int i = 0; i < list.size(); i++) {
            ScoreRecord r = list.get(i);
            tableModel.addRow(new Object[]{i + 1, r.getPlayerName(), r.getScore(), r.getGameDate()});
        }
    }

    private void deleteSelectedRow() {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(MainPanel, "请先选中要删除的行");
            return;
        }

        String name = (String) tableModel.getValueAt(row, 1);
        int score = (int) tableModel.getValueAt(row, 2);
        String date = (String) tableModel.getValueAt(row, 3);

        scoreDao.doDelete(new ScoreRecord(name, score, date, 0, 0));
        refreshTable();
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}