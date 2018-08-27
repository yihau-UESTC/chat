package com.uestc.yihau.chat.server.module.player.dao.entity;


public class Player {

    private long playerId;

    private String playerName;

    private String password;

    private int level;

    private int exp;

    public Player() {
    }

    public Player(long playerId, String playerName, String passward, int level, int exp) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.password = passward;
        this.level = level;
        this.exp = exp;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passward) {
        this.password = passward;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                '}';
    }
}
