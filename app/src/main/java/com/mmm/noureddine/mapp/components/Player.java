package com.mmm.noureddine.mapp.components;

public class Player {

    private int playerID;
    private String playerPseudo;
    private byte[] imagePlayer;

    public Player() {
    }

    public Player(int playerID, String playerPseudo) {
        this.playerID = playerID;
        this.playerPseudo = playerPseudo;
    }

    public Player(String playerPseudo) {
        this.playerPseudo = playerPseudo;
    }

    public Player(String playerPseudo, byte[] image) {
        this.playerPseudo = playerPseudo;
        this.imagePlayer = image;
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
        return this.imagePlayer;
    }

    public void setPlayerImage(byte[] image) {
        this.imagePlayer = image;
    }


}
