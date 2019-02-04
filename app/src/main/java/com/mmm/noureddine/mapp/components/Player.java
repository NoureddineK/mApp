package com.mmm.noureddine.mapp.components;

import java.io.Serializable;

public class Player implements Serializable {

    private int playerID;
    private String playerPseudo;
    private String playerTeam;
    private byte[] playerImage;
    private int score = 0;

    public Player() {
    }

    public Player(int playerID, String playerPseudo, byte[] image, String playerTeam) {
        this.playerID = playerID;
        this.playerPseudo = playerPseudo;
        this.playerImage = image;
        this.playerTeam = playerTeam;
    }

    public Player(String playerPseudo, byte[] image, String playerTeam) {
        this.playerPseudo = playerPseudo;
        this.playerTeam = playerTeam;
        this.playerImage = image;
    }

    public void setID(int playerID) {
        this.playerID = playerID;
    }

    public int getID() {
        return this.playerID;
    }

    public void setPlayerPseudo(String playerPseudo) {
        this.playerPseudo = playerPseudo;
    }

    public String getPlayerPseudo() {
        return this.playerPseudo;
    }

    public byte[] getPlayerImage() {
        return this.playerImage;
    }

    public void setPlayerImage(byte[] image) {
        this.playerImage = image;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        this.score++;
    }
}
