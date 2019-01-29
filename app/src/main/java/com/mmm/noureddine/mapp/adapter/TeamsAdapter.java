package com.mmm.noureddine.mapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.Team;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> {

    private List<Team> teamList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView teamItem;

        public MyViewHolder(View view) {
            super(view);
            teamItem = (TextView) view.findViewById(R.id.teamNameItem);
        }

    }


    public TeamsAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.teamItem.setText(team.getName());
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }



}