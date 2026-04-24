package edu.hitsz.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreDaoImpl implements ScoreDao{

    private List<ScoreRecord> scores;
    private String dataFile;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ScoreDaoImpl(String difficulty) {
        // 创建难度对应的文件夹
        String dirPath = "scores/" + difficulty;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();  // 创建文件夹
        }

        this.dataFile = dirPath + "/scores.json";
        loadFromJsonFile();

        if (scores == null || scores.isEmpty()) {
            scores = new ArrayList<>();
        }
    }

    // 保存到 JSON 文件
    private void saveToJsonFile() {
        try (Writer writer = new FileWriter(dataFile, StandardCharsets.UTF_8)) {
            gson.toJson(scores, writer);
            System.out.println("数据已保存到: " + dataFile);
        } catch (IOException e) {
            System.err.println("保存失败: " + e.getMessage());
        }
    }

    // 从 JSON 文件加载
    private void loadFromJsonFile() {
        File file = new File(dataFile);
        if (file.exists()) {
            try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
                Type type = new TypeToken<List<ScoreRecord>>(){}.getType();
                scores = gson.fromJson(reader, type);
                if (scores == null) scores = new ArrayList<>();
                System.out.println("加载数据成功，共 " + scores.size() + " 条记录");
            } catch (IOException e) {
                System.err.println("加载失败: " + e.getMessage());
                scores = new ArrayList<>();
            }
        } else {
            scores = new ArrayList<>();
        }
    }

    @Override
    public List<ScoreRecord> findByPlayer(String playerName) {
        List<ScoreRecord> result = new ArrayList<>();
        for (ScoreRecord item : scores) {
            if (item.getPlayerName().equals(playerName)) {
                result.add(item);
            }
        }
        if (result.isEmpty()) {
            System.out.println("未找到玩家 [" + playerName + "] 的得分记录");
        } else {
            System.out.println("找到玩家 [" + playerName + "] 的 " + result.size() + " 条记录");
        }
        return result;
    }

    // 获取所有得分记录
    @Override
    public List<ScoreRecord> getAllScores() {
        System.out.println("获取所有得分记录，共 " + scores.size() + " 条");
        return scores;
    }

    // 获取前 N 名最高分
    @Override
    public List<ScoreRecord> getTopN(int n) {
        // 按分数降序排序
        List<ScoreRecord> sorted = new ArrayList<>(scores);
        sorted.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        List<ScoreRecord> topN = sorted.stream().limit(n).collect(Collectors.toList());
        System.out.println("获取前 " + n + " 名最高分");
        return topN;
    }

    // 添加得分记录
    @Override
    public void doAdd(ScoreRecord record) {
        scores.add(record);
        saveToJsonFile();
        System.out.println("添加得分记录: 玩家 [" + record.getPlayerName() +
                "], 得分 [" + record.getScore() +
                "], 时间 [" + record.getGameDate() + "]");
    }

    // 删除指定记录（按 playerName + score + gameDate）
    @Override
    public void doDelete(ScoreRecord record) {
        // 使用迭代器安全删除
        Iterator<ScoreRecord> iterator = scores.iterator();
        while (iterator.hasNext()) {
            ScoreRecord item = iterator.next();
            if (item.getPlayerName().equals(record.getPlayerName()) &&
                    item.getScore() == record.getScore() &&
                    item.getGameDate().equals(record.getGameDate())) {
                iterator.remove();  // 安全删除
                saveToJsonFile();
                System.out.println("删除得分记录: 玩家 [" + record.getPlayerName() +
                        "], 得分 [" + record.getScore() +
                        "], 时间 [" + record.getGameDate() + "]");
                return;
            }
        }
        System.out.println("未找到要删除的得分记录!");
    }

    // 删除某玩家的所有记录
    @Override
    public void doDeleteByPlayer(String playerName) {
        int beforeSize = scores.size();
        scores.removeIf(item -> item.getPlayerName().equals(playerName));
        int deletedCount = beforeSize - scores.size();
        saveToJsonFile();
        if (deletedCount > 0) {
            System.out.println("删除玩家 [" + playerName + "] 的所有记录，共 " + deletedCount + " 条");
        } else {
            System.out.println("未找到玩家 [" + playerName + "] 的得分记录");
        }
    }
}
