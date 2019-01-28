package com.mmm.noureddine.mapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class TeamActivity extends AppCompatActivity {

    private List<Team> teamList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(teamList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }
    private void prepareMovieData() {
        Team team = new Team("Mad Max: Fury Road", "Action & Adventure", "2015");
        teamList.add(team);

        team = new Team("Inside Out", "Animation, Kids & Family", "2015");
        teamList.add(team);

        team = new Team("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        teamList.add(team);

        team = new Team("Shaun the Sheep", "Animation", "2015");
        teamList.add(team);

        team = new Team("The Martian", "Science Fiction & Fantasy", "2015");
        teamList.add(team);

        team = new Team("Mission: Impossible Rogue Nation", "Action", "2015");
        teamList.add(team);

        team = new Team("Up", "Animation", "2009");
        teamList.add(team);

        team = new Team("Star Trek", "Science Fiction", "2009");
        teamList.add(team);

        team = new Team("The LEGO Team", "Animation", "2014");
        teamList.add(team);

        team = new Team("Iron Man", "Action & Adventure", "2008");
        teamList.add(team);

        team = new Team("Aliens", "Science Fiction", "1986");
        teamList.add(team);

        team = new Team("Chicken Run", "Animation", "2000");
        teamList.add(team);

        team = new Team("Back to the Future", "Science Fiction", "1985");
        teamList.add(team);

        team = new Team("Raiders of the Lost Ark", "Action & Adventure", "1981");
        teamList.add(team);

        team = new Team("Goldfinger", "Action & Adventure", "1965");
        teamList.add(team);

        team = new Team("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        teamList.add(team);

        mAdapter.notifyDataSetChanged();
    }
}
