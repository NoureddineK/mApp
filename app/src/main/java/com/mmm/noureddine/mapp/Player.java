package com.mmm.noureddine.mapp;

public class Player {

    private int playerID;
    private String playerPseudo;

    public Player() {
    }

    public Player(int playerID, String playerPseudo) {
        this.playerID = playerID;
        this.playerPseudo = playerPseudo;
    }

    public Player(String playerPseudo) {
        this.playerPseudo = playerPseudo;
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
}
