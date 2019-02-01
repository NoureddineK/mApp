package com.mmm.noureddine.mapp.activities;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.Join_Team_Player;
import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.components.Team;
import com.mmm.noureddine.mapp.db.DBHandler;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.Bind;
import butterknife.OnClick;

public class Roll_DiceActivity extends AppCompatActivity {
    public static final Random RANDOM = new Random();
    private Button rollDices;
    private ImageView imageView1;
    private Chronometer chronometer;
    private Button start_chrono;
    private Button stop_chrono;
    private Button restart_chrono;
    private TextView team_id;
    private TextView player_id;
    private ImageView finger_good_view;
    private ImageView finger_bad_view;
    private List<Player> playerList;
    private List<Team> teamList;

    private HashMap<String, List<String>> data;
    private List<String> listMime;

    private Button mime;
    private Boolean sessionEnd;
    private int cptSession = 0;
    private int cptTeam = 0;
    private int index = 0;
    private String topicName = "";
    List<String> teams;
    @Bind(R.id.openWikiBtn)
    Button openWikiB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll__dice);

        rollDices = (Button) findViewById(R.id.rollDices);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        start_chrono = (Button) findViewById(R.id.start_chrono);
        stop_chrono = (Button) findViewById(R.id.stop_chrono);
        restart_chrono = (Button) findViewById(R.id.restart_chrono);
        mime = (Button) findViewById(R.id.mime_id);
        team_id = (TextView) findViewById(R.id.team_id);
        player_id = (TextView) findViewById(R.id.player_id);
        finger_good_view = (ImageView) findViewById(R.id.finger_good_view);
        finger_bad_view = (ImageView) findViewById(R.id.finger_bad_view);
        sessionEnd = false;
        teams = getPlayersTeams();
        topicName = getIntent().getStringExtra("topicName");
        DBHandler db = new DBHandler(this);
        playerList = db.getAllPlayers();
        teamList = db.getAllTeams();
        createDuck();
        getPlayersTeams();
        setSession(cptSession);
        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value1 = generateInt(1, 3);

                int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.mmm.noureddine.mapp");

                imageView1.setImageResource(res1);

            }
        });


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // TODO Auto-generated method stub
                String currentTime = chronometer.getText().toString();
                if (currentTime.equals("00:05")) {
                   /*  Commenter Pour Tests
                    AssetFileDescriptor afd = null;
                     */

                   /*   try {
                      afd = getAssets().openFd("times_up.mp3");
                        MediaPlayer audio = new MediaPlayer();
                        audio.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        audio.prepare();
                        audio.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
*/
                    // chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();
                    setSession(cptSession);
                    cptTeam++;

                }
            }
        });
        start_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

            }
        });
        stop_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();

            }
        });
        restart_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

            }
        });


        mime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  mime.setText(data.get(topicName).get(generateInt(0, 4)));
                generateNewMime();


            }
        });

        finger_good_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNewMime();
                //setSession(cptSession);


            }
        });
        finger_bad_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  mime.setText(data.get(topicName).get(generateInt(0, 4)));
                generateNewMime();
                //setSession(cptSession);
            }
        });


    }

    @OnClick(R.id.openWikiBtn)
    public void openWiki(View v) {
        String escapedQuery = null;
        try {
            if (!sessionEnd) {
                escapedQuery = URLEncoder.encode(listMime.get(index - 1), "UTF-8");
                Uri uri = Uri.parse("http://www.google.fr/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.fr"));
                startActivity(intent);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


      /*  Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.fr"));
        startActivity(intent);*/
    }


    public List<String> getPlayersTeams() {
        HashSet<String> teamSet = new HashSet<>();
        DBHandler db = new DBHandler(this);
        List<Join_Team_Player> list = db.getJoin_Team_Player();
        for (Join_Team_Player j : list) {
            teamSet.add(j.getTeam());
        }

        List<String> teams = new ArrayList<String>();
        teams.addAll(teamSet);
        return teams;
    }

    private void setSession(int nb) {

        int max = getMaxSession(teams);
        if (nb < max) {
            DBHandler db = new DBHandler(this);

            String t = teams.get(cptTeam);
            List<String> players = db.getJoin_Player(t);
            if (players.size() > nb) {
                Log.d("Jointure: jouer ", t + " / " + players.get(nb));
                player_id.setText(players.get(nb));
                team_id.setText(t);

            }
            cptSession++;
        }
        if (cptTeam == nb) {
            cptTeam = 0;
        }

    }


    private int getMaxSession(List<String> teams) {
        int max = 0;
        DBHandler db = new DBHandler(this);
        for (String s : teams) {
            List<String> players = db.getJoin_Player(s);
            if (players.size() >= max)
                max = players.size();
        }
        Log.d("Jointure: Max ", String.valueOf(max));
        return max;
    }

    private void startSession() {


    }


    private void endSession() {


    }


    private void init() {


    }

    private List<String> getThemeList(String theme) {
        if (data != null)
            return data.get(theme);
        return null;
    }


    private void createDuck() {
        data = new HashMap<>();
        if (getString(R.string.films).matches(topicName)) {
            topicName = "films";
            listMime = Arrays.asList(getResources().getStringArray(R.array.films));

        } else if (getString(R.string.expressions).matches(topicName)) {
            topicName = "expressions";
            listMime = Arrays.asList(getResources().getStringArray(R.array.expressions));

        } else if (getString(R.string.personnages).matches(topicName)) {
            topicName = "personnages";
            listMime = Arrays.asList(getResources().getStringArray(R.array.personnages));

        } else if (getString(R.string.animaux).matches(topicName)) {
            topicName = "animaux";
            listMime = Arrays.asList(getResources().getStringArray(R.array.animaux));

        } else if (getString(R.string.metiers).matches(topicName)) {
            topicName = "metiers";
            listMime = Arrays.asList(getResources().getStringArray(R.array.metiers));

        } else if (getString(R.string.activites).matches(topicName)) {
            topicName = "activites";
            listMime = Arrays.asList(getResources().getStringArray(R.array.activites));

        } else {
            topicName = "metiers";
            listMime = Arrays.asList(getResources().getStringArray(R.array.metiers));
        }
        Collections.shuffle(listMime);
        data.put(topicName, listMime);
    }

    private int generateInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void generateNewMime() {
        if (index < 5) {
            // int index = generateInt(0, 4);
            // List<String> newList = data.get(topicName);
            mime.setText(listMime.get(index));
            //listMime.remove(index);
            index++;
        } else {
            mime.setText("Finish!");
            sessionEnd = true;
        }
    }

}
