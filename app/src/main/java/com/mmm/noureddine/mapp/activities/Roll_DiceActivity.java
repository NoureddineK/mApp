package com.mmm.noureddine.mapp.activities;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.components.Team;
import com.mmm.noureddine.mapp.db.DBHandler;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private List<Player> playerList;
    private List<Team> teamList;

    private HashMap<String, List<String>> data;

    private Button mime;
    private Boolean sessionEnd = false;

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



        DBHandler db = new DBHandler(this);
        playerList = db.getAllPlayers();
        teamList = db.getAllTeams();
        createDuck();

        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value1 = generateInt(1,3);

                int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.mmm.noureddine.mapp");

                imageView1.setImageResource(res1);

            }
        });


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // TODO Auto-generated method stub
                String currentTime = chronometer.getText().toString();
                if (currentTime.equals("00:19")) {
                    AssetFileDescriptor afd = null;
                    try {
                        afd = getAssets().openFd("times_up.mp3");
                        MediaPlayer audio = new MediaPlayer();
                        audio.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        audio.prepare();
                        audio.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();

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
                mime.setText(data.get("metiers").get(generateInt(0,4)));

            }
        });

    }
    @OnClick(R.id.openWikiBtn)
    public void openWiki(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.fr"));
        startActivity(intent);
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
        data = new HashMap<String, List<String>>();
        data.put("films", Arrays.asList(getResources().getStringArray(R.array.topic)));
        data.put("expressions", Arrays.asList(getResources().getStringArray(R.array.expressions)));
        data.put("personnages", Arrays.asList(getResources().getStringArray(R.array.personnages)));
        data.put("animaux", Arrays.asList(getResources().getStringArray(R.array.animaux)));
        data.put("metiers", Arrays.asList(getResources().getStringArray(R.array.metiers)));
        data.put("activites", Arrays.asList(getResources().getStringArray(R.array.activites)));
    }


    private int generateInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
