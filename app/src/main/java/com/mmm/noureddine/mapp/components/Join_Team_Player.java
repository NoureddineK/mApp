package com.mmm.noureddine.mapp.components;

public class Join_Team_Player {
    private String player;
    private String team;


    public Join_Team_Player() {

    }


    public Join_Team_Player(String player, String team) {
        this.player = player;
        this.team = team;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }


}
