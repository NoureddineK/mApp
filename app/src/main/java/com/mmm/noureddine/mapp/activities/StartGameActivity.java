package com.mmm.noureddine.mapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.GpsTracker;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class StartGameActivity extends AppCompatActivity {
    public static final Random RANDOM = new Random();
    private Button rollDices;
    private ImageView dices;
    private Button start_chrono;
    private Button stop_chrono;
    private TextView team_id;
    private TextView player_id;
    private CircleImageView finger_good_view;
    private CircleImageView finger_bad_view;
    private HashMap<String, List<String>> data;
    private List<String> listMime;
    private TextView mime;
    private Button next_palyerBtn;
    private Button result_btn;
    private Button restart_btn;
    private ProgressBar progressBar;
    private MyCountDownTimer myCountDownTimer;

    private Boolean sessionEnd;
    private int index = 0;
    private String topicName = "";
    private int maxMime;
    private int numPlayer = 0;
    private int numTeam = 0;
    private DBHandler db;
    private HashMap<String, List<Player>> hash;
    private List<String> teams;

    private Button openWikiBtn;

    private LocationManager locationManager;
    private Location locationGPS;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        init();
        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value1 = generateInt(1, 3);
                int res1 = getResources().getIdentifier("dice_" + value1, "drawable",
                        "com.mmm.noureddine.mapp");
                dices.setImageResource(res1);

            }
        });

        openWikiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String escapedQuery = null;
                try {
                    if (!sessionEnd && !listMime.isEmpty()) {
                        if (index == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.google.fr"));
                            startActivity(intent);
                        } else {
                            String s = listMime.get(index - 1);
                            escapedQuery = URLEncoder.encode(s, "UTF-8");
                            Uri uri = Uri.parse("http://www.google.fr/#q=" + escapedQuery);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getBaseContext(), "No Mime was found!", Toast.LENGTH_LONG).show();
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        start_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNewMime();
                progressBar.setProgress(0);
                myCountDownTimer.start();
                start_chrono.setEnabled(false);
                finger_bad_view.setEnabled(true);
                finger_good_view.setEnabled(true);
            }
        });

        stop_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCountDownTimer.onFinish();

            }
        });

        next_palyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCountDownTimer.onFinish();

            }
        });

        finger_good_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNewMime();

                // RECUPERER LE BON JOUEUR ET SET LE SCORE

                hash.get(teams.get(numTeam)).get(numPlayer).incrementScore();
                Log.d("Adding Result: ", (hash.get(teams.get(numTeam)).get(numPlayer).getPlayerPseudo()));


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
        result_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "ACCESS_LOCATION DENIED", Toast.LENGTH_LONG).show();

        }
        locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    private void startSession() {


    }


    private void endSession() {


    }


    private void init() {
        rollDices = (Button) findViewById(R.id.rollDices);
        dices = (ImageView) findViewById(R.id.imageView1);
        start_chrono = (Button) findViewById(R.id.start_chrono);
        stop_chrono = (Button) findViewById(R.id.stop_chrono);
        mime = (TextView) findViewById(R.id.mime_id);
        team_id = (TextView) findViewById(R.id.team_id);
        player_id = (TextView) findViewById(R.id.player_id);
        finger_good_view = (CircleImageView) findViewById(R.id.finger_good_view);
        finger_bad_view = (CircleImageView) findViewById(R.id.finger_bad_view);
        finger_bad_view.setEnabled(false);
        finger_good_view.setEnabled(false);
        next_palyerBtn = (Button) findViewById(R.id.next_palyerBtn);
        result_btn = (Button) findViewById(R.id.result_btn);
        restart_btn = (Button) findViewById(R.id.restart_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        myCountDownTimer = new MyCountDownTimer(30000, 1500);
        openWikiBtn = (Button) findViewById(R.id.openWikiBtn);

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
        gpsTracker = new GpsTracker(this);
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

        } else if (getString(R.string.actions).matches(topicName)) {
            topicName = "actions";
            listMime = Arrays.asList(getResources().getStringArray(R.array.actions));

        } else {
            topicName = "animaux";
            listMime = Arrays.asList(getResources().getStringArray(R.array.animaux));
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
            stop_chrono.setEnabled(false);
            start_chrono.setEnabled(false);
            next_palyerBtn.setEnabled(false);
            rollDices.setEnabled(false);
            myCountDownTimer.onFinish();

            for (int i = 0; i < teams.size(); i++) {
                for (Player p : hash.get(teams.get(i))) {
                    Log.d("Adding Result: ", gpsTracker.getLatitude() + " / "
                            + gpsTracker.getLongitude() + " / " + p.getPlayerPseudo()
                            + " / " + p.getPlayerTeam() + " / " + p.getScore());

                    db.addResult(gpsTracker.getLatitude(), gpsTracker.getLongitude(),
                            p.getPlayerPseudo(), p.getPlayerTeam(), p.getScore());
                }
            }
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


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished / 300);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            this.cancel();
            progressBar.setProgress(100);
            start_chrono.setEnabled(true);
            finger_bad_view.setEnabled(false);
            finger_good_view.setEnabled(false);
            nextPlayer();
            if (sessionEnd) {
                start_chrono.setEnabled(false);
            }

        }

    }

}
