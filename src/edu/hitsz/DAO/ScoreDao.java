package edu.hitsz.DAO;

import java.util.List;

public interface ScoreDao {
    // 查找某玩家的所有得分记录
    List<ScoreRecord> findByPlayer(String playerName);

    // 获取所有得分记录
    List<ScoreRecord> getAllScores();

    // 获取前 N 名最高分
    List<ScoreRecord> getTopN(int n);

    // 添加得分记录
    void doAdd(ScoreRecord record);

    // 删除指定记录（按 playerName + score + gameDate）
    void doDelete(ScoreRecord record);

    // 删除某玩家的所有记录
    void doDeleteByPlayer(String playerName);
}
