package com.mmm.noureddine.mapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "letsmimeDB.db";
    private static final String TABLE_PLAYER = "Player";
    private static final String PLAYER_ID = "playerID";
    private static final String PLAYER_PSEUDO = "playerPseudo";

    private static final String TABLE_TEAM = "Team";
    private static final String TEAM_ID = "teamID";
    private static final String TEAM_NAME = "teamName";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PLAYER = "CREATE TABLE "
                + TABLE_PLAYER + " ( "
                + PLAYER_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PLAYER_PSEUDO + " TEXT UNIQUE  NOT NULL" + " );";
        db.execSQL(CREATE_TABLE_PLAYER);


        String CREATE_TABLE_TEAM = "CREATE TABLE "
                + TABLE_TEAM + " ( "
                + TEAM_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + TEAM_NAME + " TEXT UNIQUE NOT NULL" + " );";
        db.execSQL(CREATE_TABLE_TEAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        // Creating tables again
        onCreate(db);
    }

    // PLAYER MANAGER
    public Player loadPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER,
                new String[]{PLAYER_ID, PLAYER_PSEUDO}, PLAYER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Player contact = new Player(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
// return shop
        return contact;
    }

    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(PLAYER_ID, player.getID()); // Player Name
        values.put(PLAYER_PSEUDO, player.getPlayerPseudo()); // Player Name

        // Inserting Row
        db.insert(TABLE_PLAYER, null, values);
        db.close(); // Closing database connection
    }

    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<Player>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setID(Integer.parseInt(cursor.getString(0)));
                player.setPlayerPseudo(cursor.getString(1));
// Adding contact to list
                playerList.add(player);
            } while (cursor.moveToNext());
        }
// return contact list
        return playerList;
    }

    // Getting players Count
    public int getPlayersCount() {
        String countQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, player.getPlayerPseudo());
// updating row
        return db.update(TABLE_PLAYER, values, PLAYER_ID + " = ?",
                new String[]{String.valueOf(player.getID())});
    }

    // Deleting a Player
    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, PLAYER_ID + " = ?",
                new String[]{String.valueOf(player.getID())});
        db.close();
    }


    // TEAM MANAGER

    public Team loadTeam(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEAM,
                new String[]{TEAM_ID, TEAM_NAME}, TEAM_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Team contact = new Team(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
// return shop
        return contact;
    }


    public void addTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(TEAM_ID, team.getID()); // Player Name
        values.put(TEAM_NAME, team.getName()); // Player Name

        // Inserting Row
        db.insert(TABLE_TEAM, null, values);
        db.close(); // Closing database connection
    }

    public List<Team> getAllTeams() {
        List<Team> teamsList = new ArrayList<Team>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TEAM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.setID(Integer.parseInt(cursor.getString(0)));
                team.setName(cursor.getString(1));
// Adding contact to list
                teamsList.add(team);
            } while (cursor.moveToNext());
        }
// return contact list
        return teamsList;
    }

    // Getting players Count
    public int getTeamsCount() {
        String countQuery = "SELECT * FROM " + TABLE_TEAM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEAM_ID, team.getName());
// updating row
        return db.update(TABLE_TEAM, values, TEAM_ID + " = ?",
                new String[]{String.valueOf(team.getID())});
    }

    // Deleting a Player
    public void deleteTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEAM, TEAM_ID + " = ?",
                new String[]{String.valueOf(team.getID())});
        db.close();
    }


    public Player findHandler(String playerPseudo) {
        return null;
    }

    public boolean deleteHandler(int playerID) {
        return false;
    }

    public boolean updateHandler(int playerID, String playerPseudo) {
        return false;
    }

}