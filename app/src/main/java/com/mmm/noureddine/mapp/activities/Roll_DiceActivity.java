package com.mmm.noureddine.mapp.activities;

import android.content.Intent;
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

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.db.DBHandler;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Roll_DiceActivity extends AppCompatActivity {
    public static final Random RANDOM = new Random();
    private Button rollDices;
    private ImageView dices;
    private Chronometer chronometer;
    private Button start_chrono;
    private Button stop_chrono;
    private Button restart_chrono;
    private TextView team_id;
    private TextView player_id;
    private CircleImageView finger_good_view;
    private CircleImageView finger_bad_view;
    private HashMap<String, List<String>> data;
    private List<String> listMime;
    private TextView mime;
    private Button next_palyerBtn;
    private Button result_btn ;
    private Button restart_btn;

    private Boolean sessionEnd;
    private int index = 0;
    private String topicName = "";
    private int maxMime;
    private int numPlayer = 0;
    private int numTeam = 0;
    private DBHandler db;
    private HashMap<String, List<Player>> hash;
    private List<String> teams;
    @Bind(R.id.openWikiBtn)
    Button openWikiB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll__dice);
        init();
        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value1 = generateInt(1, 3);

                int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.mmm.noureddine.mapp");

                dices.setImageResource(res1);

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
                    nextPlayer();
                    chronometer.stop();
                }
            }
        });
        start_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNewMime();
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
                generateNewMime();
            }
        });

        next_palyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPlayer();
            }
        });

        finger_good_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNewMime();
            }
        });
        finger_bad_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNewMime();

            }
        });
        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

    @OnClick(R.id.openWikiBtn)
    public void openWiki(View v) {
        String escapedQuery = null;
        try {
            if (!sessionEnd && !listMime.isEmpty()) {
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
    }


    private void startSession() {


    }


    private void endSession() {


    }


    private void init() {
        rollDices = (Button) findViewById(R.id.rollDices);
        dices = (ImageView) findViewById(R.id.imageView1);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        start_chrono = (Button) findViewById(R.id.start_chrono);
        stop_chrono = (Button) findViewById(R.id.stop_chrono);
        restart_chrono = (Button) findViewById(R.id.restart_chrono);
        mime = (TextView) findViewById(R.id.mime_id);
        team_id = (TextView) findViewById(R.id.team_id);
        player_id = (TextView) findViewById(R.id.player_id);
        finger_good_view = (CircleImageView) findViewById(R.id.finger_good_view);
        finger_bad_view = (CircleImageView) findViewById(R.id.finger_bad_view);
        next_palyerBtn = (Button) findViewById(R.id.next_palyerBtn);
        result_btn = (Button) findViewById(R.id.result_btn);
        restart_btn = (Button) findViewById(R.id.restart_btn);
        Picasso.get()
                .load(R.drawable.finger_good)
                .into(finger_good_view);
        Picasso.get()
                .load(R.drawable.finger_bad)
                .into(finger_bad_view);
        sessionEnd = false;
        topicName = getIntent().getStringExtra("topicName");
        db = new DBHandler(this);
        hash = db.getPresentPlayerTeams();
        teams = new ArrayList(hash.keySet());
        createDuck();
        nextPlayer();

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
        maxMime = listMime.size();
    }

    private int generateInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void generateNewMime() {
        if (index < maxMime) {
            mime.setText(listMime.get(index));
            index++;
        } else {
            mime.setText("Finish!");
            sessionEnd = true;
        }

    }

    private void nextPlayer() {
        player_id.setText(hash.get(teams.get(numTeam)).get(numPlayer).getPlayerPseudo());
        team_id.setText(teams.get(numTeam));
        numTeam++;
        if (numTeam >= teams.size()) {
            numPlayer++;
            numTeam = 0;
            if (numPlayer >= hash.get(teams.get(numTeam)).size()) {
                numPlayer = 0;
            }

        }

    }

}
