package com.mmm.noureddine.mapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.db.DBHandler;
import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.RecyclerItemClickListener;
import com.mmm.noureddine.mapp.components.Team;
import com.mmm.noureddine.mapp.adapter.TeamsAdapter;

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

    @Bind(R.id.validate_teams)
    ImageView validate_teams;


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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        try {
            prepareTeamData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //  int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Team item = teamList.get(position);
                        Toast.makeText(getBaseContext(), item.getName() + " Selected ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Team item = teamList.get(position);
                        removeTeam(item);
                        Toast.makeText(getBaseContext(), item.getName() + " Deleted", Toast.LENGTH_LONG).show();
                    }
                })
        );

    }

    @OnClick(R.id.plus_button)
    public void addingTeams(View v) {
        AddingTeamData(team_name.getText().toString());
    }

    private void prepareTeamData() throws Exception {

        DBHandler db = new DBHandler(this);
        Log.d("Insert: ", "Inserting ..");
        db.addTeam(new Team("Team A"));
        db.addTeam(new Team("Team B"));
        List<Team> teams = db.getAllTeams();
        for (Team team : teams) {
            teamList.add(team);
        }
        mAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.validate_teams)
    public void validateTeams(View v) {
        if (teamList.size() < 2) {
            Toast.makeText(getBaseContext(), "Please introduce at least two teams!", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(this, PlayerActivity.class);
            startActivity(intent);
        }
    }


    private void AddingTeamData(String name) {
        if (!name.matches("")) {
            DBHandler db = new DBHandler(this);
            Team team = new Team(name);
            db.addTeam(team);
            teamList.add(team);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void removeTeam(Team team) {
        if (team != null) {
            teamList.remove(team);
            DBHandler db = new DBHandler(this);
            db.deleteTeam(team);
            mAdapter.notifyDataSetChanged();
        }
    }


}
