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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class TeamActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_1 = 1;
    private List<Team> teamList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TeamsAdapter mAdapter;

    @Bind(R.id.plus_button)
    Button plus_button;

    private EditText team_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        team_name = (EditText) findViewById(R.id.team_name);

        mAdapter = new TeamsAdapter(teamList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareTeamData();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                      //  int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Team item = teamList.get(position);
                        Toast.makeText(getBaseContext(), item.getName() +" Selected ", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getBaseContext(), PlayerActivity.class);
                        intent.putExtra("message", "This message comes from PassingDataSourceActivity's first button");
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        //Team item = teamList.get(position);
                        //Toast.makeText(getBaseContext(), item.getName()+ " Loo", Toast.LENGTH_LONG).show();
                    }
                })
        );

    }

    @OnClick(R.id.plus_button)
    public void addingTeams(View v) {
            AddingTeamData(team_name.getText().toString());
    }

    private void prepareTeamData() {

        DBHandler db = new DBHandler(this);
        Log.d("Insert: ", "Inserting ..");
        db.addTeam(new Team("Team A"));
        db.addTeam(new Team("Team B"));

// Reading all shops
        Log.d("Reading: ", "Reading all Teams..");
        List<Team> teams = db.getAllTeams();

        for (Team team : teams) {
            String log = "Id: " + team.getID() + " ,Name: " + team.getName();
// Writing shops to log
            Log.d("Team: : ", log);
            teamList.add(team);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void AddingTeamData(String name) {
        if (!name.equals("") && !name.equals(" ")) {
            DBHandler db = new DBHandler(this);
            Log.d("Insert: ", "Inserting ..");
            Team team = new Team(name);
            db.addTeam(team);
            teamList.add(team);
            mAdapter.notifyDataSetChanged();
        }
    }


}
