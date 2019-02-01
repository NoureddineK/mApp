package com.mmm.noureddine.mapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.mmm.noureddine.mapp.components.Join_Team_Player;
import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.components.Team;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 21;
    private static final String DATABASE_NAME = "letsmimeDB.db";
    private static final String TABLE_PLAYER = "Player";
    private static final String PLAYER_ID = "playerID";
    private static final String PLAYER_PSEUDO = "playerPseudo";
    private static final String PLAYER_TEAM = "playerTeam";
    private static final String PLAYER_IMAGE = "playerImage";

    private static final String TABLE_TEAM = "Team";
    private static final String TEAM_ID = "teamID";
    private static final String TEAM_NAME = "teamName";

    private static final String TABLE_JOIN = "TeamPlayer";
    private static final String JOIN_ID = "joinID";
    private static final String JOIN_PLAYER = "joinPlayer";
    private static final String JOIN_TEAM = "joinTeam";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PLAYER = "CREATE TABLE "
                + TABLE_PLAYER + " ( "
                + PLAYER_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PLAYER_PSEUDO + " TEXT UNIQUE  NOT NULL , "
                + PLAYER_TEAM + " TEXT , "
                + PLAYER_IMAGE + " BLOB " + " );";

        db.execSQL(CREATE_TABLE_PLAYER);


        String CREATE_TABLE_TEAM = "CREATE TABLE "
                + TABLE_TEAM + " ( "
                + TEAM_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + TEAM_NAME + " TEXT UNIQUE NOT NULL" + " );";
        db.execSQL(CREATE_TABLE_TEAM);


        String CREATE_TABLE_JOIN = "CREATE TABLE "
                + TABLE_JOIN + " ( "
                + JOIN_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + JOIN_TEAM + " TEXT NOT NULL ,"
                + JOIN_PLAYER + " TEXT NOT NULL"
                + " );";
        db.execSQL(CREATE_TABLE_JOIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOIN);
        // Creating tables again
        onCreate(db);
    }

    // PLAYER MANAGER
    public Player loadPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER,
                new String[]{PLAYER_ID, PLAYER_PSEUDO, PLAYER_TEAM, PLAYER_IMAGE}, PLAYER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Player contact = new Player(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getBlob(3));
// return Player
        return contact;
    }


    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // values.put(PLAYER_ID, player.getID()); // Player Name
        values.put(PLAYER_PSEUDO, player.getPlayerPseudo()); // Player Name
        values.put(PLAYER_TEAM, player.getPlayerTeam());
        values.put(PLAYER_IMAGE, player.getPlayerImage());
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
                player.setPlayerTeam(cursor.getString(2));
                player.setPlayerImage(cursor.getBlob(3));
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

    public void updatePlayerName(String name, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE " + TABLE_PLAYER + " SET " + PLAYER_PSEUDO
                + " = '" + newName + "' WHERE " + PLAYER_PSEUDO + " = '" + name + "';";
        db.execSQL(update);
        db.close();

    }

    public void updatePlayerImage(String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE " + TABLE_PLAYER + " SET " + PLAYER_IMAGE
                + " = '" + image + "' WHERE " + PLAYER_PSEUDO + " = '" + name + "';";
        db.execSQL(update);
        db.close();
    }

    public void updatePlayerTeam(String name, String team) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE " + TABLE_PLAYER + " SET " + PLAYER_TEAM
                + " = '" + team + "' WHERE " + PLAYER_PSEUDO + " = '" + name + "';";
        db.execSQL(update);
        db.close();
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


    public List<Player> getTeamPlayers() {
        List<Player> playerList = new ArrayList<Player>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Player player = new Player();
                Player player = loadPlayer(Integer.parseInt(cursor.getString(0)));
                //player.setID(Integer.parseInt(cursor.getString(0)));
                //player.setPlayerPseudo(cursor.getString(1));
                //player.setPlayerImage(cursor.getBlob(2));
// Adding contact to list
                playerList.add(player);
            } while (cursor.moveToNext());
        }
// return contact list
        return playerList;
    }

    public void addPlayerToTeam(String player, String team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // values.put(PLAYER_ID, player.getID()); // Player Name
        values.put(JOIN_PLAYER, player); // Player Name
        values.put(JOIN_TEAM, team);
        // Inserting Row
        db.insert(TABLE_JOIN, null, values);
        db.close(); // Closing database connection
    }


    public List<Join_Team_Player> getJoin_Team_Player() {
        List<Join_Team_Player> list = new ArrayList<Join_Team_Player>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Join_Team_Player join = new Join_Team_Player();
                join.setTeam(cursor.getString(1));
                join.setPlayer(cursor.getString(2));
// Adding contact to list
                list.add(join);
            } while (cursor.moveToNext());
        }
// return contact list
        return list;
    }

    public List<String> getJoin_Player(String team) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> listPlayers = new ArrayList<>();
        String selectQuery = "SELECT " + JOIN_PLAYER + " FROM " + TABLE_JOIN
               +" WHERE " + JOIN_TEAM + " = '" + team + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                listPlayers.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return listPlayers;
    }


    // Deleting a Player
    public void deleteJoinPlayer(String player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JOIN, JOIN_PLAYER + " = ?",
                new String[]{String.valueOf(player)});
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