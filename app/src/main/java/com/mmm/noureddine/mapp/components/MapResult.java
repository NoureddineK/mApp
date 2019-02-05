package com.mmm.noureddine.mapp.components;

public class MapResult {

    private int resultID;
    private String playerName;
    private double latitude;
    private double longitude;
    private String date;



    private String teamName;
    private int score;


    public MapResult() {

    }

    public MapResult(int ID, double latitude, double longitude, String date, String playerName) {
        this.resultID = ID;
        this.playerName = playerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;

    }

    public MapResult(double latitude, double longitude, String date, String playerName) {
        this.playerName = playerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;

    }

    public MapResult(double latitude, double longitude, String playerName, String teamName, int score) {
        this.playerName = playerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.teamName = teamName;
        this.score = score;

    }


    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "MapResult{" +
                "resultID=" + resultID +
                ", playerName='" + playerName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", date='" + date + '\'' +
                ", teamName='" + teamName + '\'' +
                ", score=" + score +
                '}';
    }
}