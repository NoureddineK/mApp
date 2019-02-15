package com.mmm.noureddine.mapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import com.mmm.noureddine.mapp.components.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 45;
    private static final String DATABASE_NAME = "letsmimeDB.db";

    private static final String TABLE_PLAYER = "Player";
    private static final String PLAYER_ID = "playerID";
    private static final String PLAYER_PSEUDO = "playerPseudo";
    private static final String PLAYER_TEAM = "playerTeam";
    private static final String PLAYER_IMAGE = "playerImage";
    private static final String FKTEAM = "fk_team";

    private static final String TABLE_TEAM = "Team";
    private static final String TEAM_ID = "teamID";
    private static final String TEAM_NAME = "teamName";

    private static final String TABLE_RESULT = "Result";
    private static final String RESULT_ID = "resultID";
    private static final String RESULT_PLAYER_ID = "resultPlayerID";
    private static final String RESULT_TEAM_ID = "resultTeamID";
    private static final String RESULT_LAT = "latitude";
    private static final String RESULT_LONG = "longitude";
    private static final String RESULT_DATE = "date";
    private static final String RESULT_SCORE = "score";
    private static final String FKPLAYER = "fk_player";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_TEAM = "CREATE TABLE "
                + TABLE_TEAM + " ( "
                + TEAM_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + TEAM_NAME + " TEXT UNIQUE NOT NULL" + " );";
        db.execSQL(CREATE_TABLE_TEAM);

        String CREATE_TABLE_PLAYER = "CREATE TABLE "
                + TABLE_PLAYER
                + " ( "
                + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PLAYER_PSEUDO + " TEXT UNIQUE NOT NULL , "
                + PLAYER_IMAGE + " BLOB, "
                + PLAYER_TEAM + " INTEGER NOT NULL,  "
                + " CONSTRAINT " + FKTEAM
                + " FOREIGN KEY ( " + PLAYER_TEAM + ") "
                + " REFERENCES " + TABLE_TEAM + "(" + TEAM_ID + ")"
                + " );";
        db.execSQL(CREATE_TABLE_PLAYER);

        String CREATE_TABLE_RESULT = "CREATE TABLE "
                + TABLE_RESULT
                + " ( "
                + RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + RESULT_LAT + " LONG, "
                + RESULT_LONG + " LONG, "
                + RESULT_DATE + " TEXT, "
                + RESULT_SCORE + " INTEGER,"
                + RESULT_PLAYER_ID + " INTEGER NOT NULL,  "
                + RESULT_TEAM_ID + " INTEGER NOT NULL,  "
                + " CONSTRAINT " + FKPLAYER
                + " FOREIGN KEY ( " + RESULT_PLAYER_ID + ") "
                + " REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + "), "

                + " CONSTRAINT " + FKTEAM
                + " FOREIGN KEY ( " + RESULT_TEAM_ID + ") "
                + " REFERENCES " + TABLE_TEAM + "(" + TEAM_ID + ")"
                + " );";

        db.execSQL(CREATE_TABLE_RESULT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        onCreate(db);
    }

    /*
    Gestion des Joueurs
     */
    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_PSEUDO, player.getPlayerPseudo());
        values.put(PLAYER_IMAGE, player.getPlayerImage());
        values.put(PLAYER_TEAM, (player.getPlayerTeam()));
        db.insert(TABLE_PLAYER, null, values);
        db.close();
    }

    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        String selectQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setID(Integer.parseInt(cursor.getString(0)));
                player.setPlayerPseudo(cursor.getString(1));
                player.setPlayerImage(cursor.getBlob(2));
                player.setPlayerTeam(cursor.getString(3));
                playerList.add(player);
            } while (cursor.moveToNext());
        }
        return playerList;
    }

    public int updatePlayerImage(String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_IMAGE, image);
        return db.update(TABLE_PLAYER, values, PLAYER_PSEUDO + " =? ",
                new String[]{name});
    }


    public int getPlayerID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " SELECT " + PLAYER_ID + " FROM " + TABLE_PLAYER + " WHERE " + PLAYER_PSEUDO + " = '" + name + "';";
        Integer playerID = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                playerID = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return playerID;
    }

    public String getPlayerName(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " SELECT " + PLAYER_PSEUDO + " FROM " + TABLE_PLAYER + " WHERE " + PLAYER_ID + " = " + id + ";";
        String name = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return name;
    }

    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, PLAYER_ID + " = ?",
                new String[]{String.valueOf(player.getID())});
        db.close();
    }


    /*
    Gestion des Equipes
     */

    public void addTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEAM_NAME, team.getName());
        db.insert(TABLE_TEAM, null, values);
        db.close();
    }

    public List<Team> getAllTeams() {
        List<Team> teamsList = new ArrayList<Team>();
        String selectQuery = "SELECT * FROM " + TABLE_TEAM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.setID(Integer.parseInt(cursor.getString(0)));
                team.setName(cursor.getString(1));
                teamsList.add(team);
            } while (cursor.moveToNext());
        }
        return teamsList;
    }

    public void deleteTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEAM, TEAM_ID + " = ?",
                new String[]{String.valueOf(team.getID())});
        db.close();
    }


    public int getTeamID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " SELECT " + TEAM_ID + " FROM " + TABLE_TEAM + " WHERE " + TEAM_NAME + " = '" + name + "';";
        Integer teamID = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                teamID = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return teamID;
    }

    public String getTeamName(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " SELECT " + TEAM_NAME + " FROM " + TABLE_TEAM + " WHERE " + TEAM_ID + " = " + id + ";";
        String name = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return name;
    }


    public HashMap<String, List<Player>> getPresentPlayerTeams() {
        SQLiteDatabase db = this.getWritableDatabase();
        HashMap<String, List<Player>> teamHashMap = new HashMap<>();
        List<Player> players = getAllPlayers();
        List<String> teams = new ArrayList<>();
        for (Player p : players) {
            teams.add(p.getPlayerTeam());
        }
        for (String s : teams) {
            List<Player> list = new ArrayList<>();
            for (Player p : players) {
                if (s.matches(p.getPlayerTeam())) {
                    list.add(p);
                }
            }
            teamHashMap.put(s, list);
        }
        return teamHashMap;
    }

    /*
    Gestion des Resultats
     */

    public void addResult(double lat, double lon, String playerName, String teamName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        shortDateFormat.format(new Date());
        values.put(RESULT_LAT, lat);
        values.put(RESULT_LONG, lon);
        values.put(RESULT_DATE, shortDateFormat.format(new Date()));
        values.put(RESULT_SCORE, score);
        values.put(RESULT_PLAYER_ID, getPlayerID(playerName));
        values.put(RESULT_TEAM_ID, getTeamID(teamName));
        db.insert(TABLE_RESULT, null, values);
        db.close();
    }

    public List<MapResult> getAllResult() {
        List<MapResult> resultList = new ArrayList<MapResult>();
        String selectQuery = "SELECT * FROM " + TABLE_RESULT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MapResult res = new MapResult();
                res.setResultID(Integer.parseInt(cursor.getString(0)));
                res.setLatitude(Double.parseDouble(cursor.getString(1)));
                res.setLongitude(Double.parseDouble(cursor.getString(2)));
                res.setDate(cursor.getString(3));
                res.setScore(Integer.parseInt(cursor.getString(4)));
                res.setPlayerName(getPlayerName(Integer.parseInt(cursor.getString(5))));
                res.setTeamName(getTeamName(Integer.parseInt(cursor.getString(6))));
                resultList.add(res);
            } while (cursor.moveToNext());
        }
        return resultList;
    }

    public void deleteResult(MapResult mapResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESULT, RESULT_ID + " = ?",
                new String[]{String.valueOf(mapResult.getResultID())});
        db.close();
    }
}