package com.mmm.noureddine.mapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mmm.noureddine.mapp.components.MapResult;
import com.mmm.noureddine.mapp.components.Team;
import com.mmm.noureddine.mapp.db.DBHandler;
import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.adapter.PlayersAdapter;
import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.RecyclerItemClickListener;
import com.mmm.noureddine.mapp.utils.DbBitmapUtility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity {
    private List<Player> playerList = new ArrayList<>();
    private List<String> teamNameList = new ArrayList<>();

    private RecyclerView recyclerView;
    private PlayersAdapter mAdapter;

    @Bind(R.id.plus_button)
    Button plus_button;

    @Bind(R.id.validate_players)
    ImageView validate_players;

    private String namePlayer = "";
    private String nameTeam = "";
    private EditText player_name;
    Intent playerIntent;
    Spinner team_choice_spinner;
    private List<String> topicList;
    Spinner topic_choice_spinner;
    String topicName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        player_name = (EditText) findViewById(R.id.player_name);
        team_choice_spinner = (Spinner) findViewById(R.id.team_choice);

        mAdapter = new PlayersAdapter(playerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        prepareData();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Player item = playerList.get(position);
                        Toast.makeText(getBaseContext(), item.getPlayerPseudo() + " Selected ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Player item = playerList.get(position);
                        removePlayer(item);
                        Toast.makeText(getBaseContext(), item.getPlayerPseudo() + " Deleted ", Toast.LENGTH_LONG).show();
                    }
                })
        );


        team_choice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(getBaseContext(), teamNameList.get(position) + " Selected ", Toast.LENGTH_LONG).show();
                nameTeam = teamNameList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        topic_choice_spinner = (Spinner) findViewById(R.id.topic_choice_spinner);

        this.topicList = Arrays.asList(getResources().getStringArray(R.array.topic));
        Log.d("topicList: ", topicList.toString());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, topicList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic_choice_spinner.setAdapter(adapter);

        topic_choice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                topicName = topicList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @OnClick(R.id.plus_button)
    public void addingPlayers(View v) {
        if (player_name.getText().toString().matches("")) {
            Toast.makeText(getBaseContext(), "Please input your Name!", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ImageView imageView = new ImageView(this);
        Picasso.get()
                .load(R.drawable.photo)
                .into(imageView);
        builder.setInverseBackgroundForced(true);
        builder.setView(imageView);
        builder.setMessage("Would you like to take a picture?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        playerIntent = new Intent(getApplicationContext(), CameraActivity.class);
                        String n = player_name.getText().toString();
                        if (!n.matches("")) {
                            AddingPlayerData(n);

                            playerIntent.putExtra("playerName", n);
                            Log.d("playerName: ", n);
                        }
                        startActivityForResult(playerIntent, 0);

                        dialog.cancel();
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String n = player_name.getText().toString();
                        AddingPlayerData(n);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void prepareData() {

        //Get Teams from Data base to Spinner
        DBHandler db = new DBHandler(this);
        List<Team> teamList = db.getAllTeams();
        for (Team team : teamList) {
            teamNameList.add(team.getName());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, teamNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team_choice_spinner.setAdapter(adapter);

        List<Player> players = db.getAllPlayers();
        if (players.size() != 0) {
            for (Player player : players) {
                playerList.add(player);
            }
            mAdapter.notifyDataSetChanged();
        }

    }


    private void AddingPlayerData(String name1) {
        if (!name1.matches("")) {
            namePlayer = name1;
            player_name.setText("");
            DBHandler db = new DBHandler(this);
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_icone);
            Player player;
            player = new Player(name1, DbBitmapUtility.getBytes(icon), nameTeam);
            db.addPlayer(player);
            playerList.add(player);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.validate_players)
    public void validatePlayers(View v) {
        List<String> teamUsed = new ArrayList<>();
        for (Player player : playerList) {
            teamUsed.add(player.getPlayerTeam());
        }
        Set set = new HashSet(teamUsed);
        if (playerList.size() < 2 || set.size() < 2) {
            Toast.makeText(getBaseContext(), "Please use at least two teams and two players", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(getBaseContext(), StartGameActivity.class);
            intent.putExtra("topicName", topicName);
            startActivityForResult(intent, 0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Retrieve data in the intent
        DBHandler db = new DBHandler(this);
        try {
            String path = data.getStringExtra("path");
            Log.d("path result: ", path);

            File imgFile = new File(path);
            if (imgFile.exists()) {

            }
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap bitmap = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);
            setPlayerImageFromList(namePlayer, DbBitmapUtility.getBytes(getCroppedBitmap(bitmap)), playerList);
            int u = db.updatePlayerImage(namePlayer, DbBitmapUtility.getBytes(getCroppedBitmap(bitmap)));
            Log.d("Update: ", String.valueOf(u));
            mAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e("onActivityResult", e.getMessage());
        }
    }

    private void setPlayerImageFromList(String name, byte[] image, List<Player> list) {
        for (Player p : list) {
            if (p.getPlayerPseudo().equals(name)) {
                p.setPlayerImage(image);
            }
        }
    }

    private void removePlayer(Player player) {
        if (player != null) {
            DBHandler db = new DBHandler(this);
            playerList.remove(player);
            db.deletePlayer(player);
            removePlayerResult(player.getPlayerPseudo());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void removePlayerResult(String name) {
        DBHandler db = new DBHandler(this);
        List<MapResult> results = db.getAllResult();
        for (MapResult mapResult : results) {
            if (mapResult.getPlayerName().matches(name)) {
                db.deleteResult(mapResult);
            }
        }

    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return rotateImage(output, 270);
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

}
