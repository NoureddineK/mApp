package com.mmm.noureddine.mapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.adapter.ResultAdapter;
import com.mmm.noureddine.mapp.adapter.TeamsAdapter;
import com.mmm.noureddine.mapp.components.MapResult;
import com.mmm.noureddine.mapp.components.RecyclerItemClickListener;
import com.mmm.noureddine.mapp.components.Team;
import com.mmm.noureddine.mapp.db.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private DBHandler db;
    private List<MapResult> mapResultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultAdapter mAdapter;
    private Button map_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        db = new DBHandler(this);

        map_btn = (Button) findViewById(R.id.map_btn);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_res);
        try {
            prepareTeamData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new ResultAdapter(mapResultList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //  int itemPosition = recyclerView.getChildLayoutPosition(view);
                        MapResult item = mapResultList.get(position);
                        Toast.makeText(getBaseContext(), item.getTeamName() + " Selected ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        MapResult item = mapResultList.get(position);
                        mapResultList.remove(item);
                        db.deleteResult(item);
                        Toast.makeText(getBaseContext(), item.getPlayerName() + " Deleted", Toast.LENGTH_LONG).show();
                        mAdapter.notifyDataSetChanged();
                    }
                })
        );

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LocationActivity.class);
                startActivityForResult(intent, 0);
            }
        });


    }


    private void prepareTeamData() throws Exception {

        Log.d("Insert: ", "Inserting ..");
        List<MapResult> results = db.getAllResult();
        for (MapResult res : results) {
            mapResultList.add(res);
            Log.d("resultats: ", res.toString());
        }
        mAdapter.notifyDataSetChanged();
    }


}
