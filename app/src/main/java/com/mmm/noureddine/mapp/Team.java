package com.mmm.noureddine.mapp;

public class Team {
    private int teamID;
    private String teamName;

    public Team() {
    }

    public Team(String teamName) {
        this.teamName = teamName;
    }
    public Team(int teamID, String teamName) {
        this.teamID = teamID;
        this.teamName = teamName;
    }

    public int getID() {
        return this.teamID;
    }

    public void setID(int id) {
        this.teamID = id;
    }

    public String getName() {
        return this.teamName;
    }

    public void setName(String teamName) {
        this.teamName = teamName;
    }

}