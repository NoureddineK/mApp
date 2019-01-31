package com.mmm.noureddine.mapp.components;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int teamID;
    private String teamName;
    private List<Player> playerList;

    public Team() {
    }

    public Team(String teamName) {
        this.teamName = teamName;
        this.playerList = new ArrayList<Player>();

    }

    public Team(int teamID, String teamName) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.playerList = new ArrayList<Player>();
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

    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public Player getPlayerByName(String name) {
        if (playerList != null) {
            if (!playerList.isEmpty()) {
                for (Player p : this.playerList) {
                    if(p.getPlayerPseudo().matches(name)){
                        return p;
                    }
                }
            }
        }
        return null;
    }

    public boolean exist(Player player){
        return this.playerList.contains(player);
    }


    public void addPlayer(Player player){
        this.playerList.add(player);
    }


}