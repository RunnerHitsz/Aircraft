package edu.hitsz.DAO;

public class ScoreRecord {
    private String playerName;
    private int score;
    private String gameDate;
    private int killCount;
    private long survivalTime;

    // 构造函数
    public ScoreRecord(String playerName, int score, String gameDate, int killCount, long survivalTime) {
        this.playerName = playerName;
        this.score = score;
        this.gameDate = gameDate;
        this.killCount = killCount;
        this.survivalTime = survivalTime;
    }

    // Getter 和 Setter
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public long getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(long survivalTime) {
        this.survivalTime = survivalTime;
    }

    @Override
    public String toString() {
        return "ScoreRecord{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                ", gameDate='" + gameDate + '\'' +
                ", killCount=" + killCount +
                ", survivalTime=" + survivalTime +
                '}';
    }
}
