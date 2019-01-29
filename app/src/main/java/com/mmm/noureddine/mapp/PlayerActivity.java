package com.mmm.noureddine.mapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity {
    private List<Player> playerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlayersAdapter mAdapter;

    @Bind(R.id.plus_button)
    Button plus_button;

    private EditText player_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Intent intent = getIntent();
        final String message = intent.getStringExtra("message");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        player_name = (EditText) findViewById(R.id.player_name);


        mAdapter = new PlayersAdapter(playerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        preparePlayerData();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //  int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Player item = playerList.get(position);
                        Toast.makeText(getBaseContext(), item.getPlayerPseudo() + " Selected ", Toast.LENGTH_LONG).show();

                      //  Intent intent = new Intent(getBaseContext(), PlayerActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Player item = PlayerList.get(position);
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    }
                })
        );

    }

    @OnClick(R.id.plus_button)
    public void addingPlayers(View v) {
        AddingPlayerData(player_name.getText().toString());
    }

    private void preparePlayerData() {

        DBHandler db = new DBHandler(this);
        Log.d("Insert: ", "Inserting ..");
        db.addPlayer(new Player("Player A"));
        db.addPlayer(new Player("Player B"));

// Reading all shops
        Log.d("Reading: ", "Reading all Players..");
        List<Player> players = db.getAllPlayers();

        for (Player player : players) {
            String log = "Id: " + player.getID() + " ,Name: " + player.getPlayerPseudo();
// Writing shops to log
            Log.d("Player: : ", log);
            playerList.add(player);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void AddingPlayerData(String name) {
        if (!name.equals("") && !name.equals(" ")) {
            DBHandler db = new DBHandler(this);
            Log.d("Insert: ", "Inserting ..");
            Player player = new Player(name);
            db.addPlayer(player);
            playerList.add(player);
            mAdapter.notifyDataSetChanged();
        }
    }
}
