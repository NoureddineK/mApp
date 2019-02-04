package com.mmm.noureddine.mapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.MapResult;
import com.mmm.noureddine.mapp.utils.DbBitmapUtility;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private List<MapResult> resultList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView res_player;
        public TextView res_team;
        public TextView res_score;
        public TextView res_date;


        public MyViewHolder(View view) {
            super(view);
            res_player = (TextView) view.findViewById(R.id.res_player);
            res_team = (TextView) view.findViewById(R.id.res_team);
            res_score = (TextView) view.findViewById(R.id.res_score);
            res_date = (TextView) view.findViewById(R.id.res_date);

        }

    }

    public ResultAdapter(List<MapResult> resultList) {
        this.resultList = resultList;
    }

    @Override
    public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_list_row, parent, false);
        return new ResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultAdapter.MyViewHolder holder, int position) {
        MapResult mapResult = resultList.get(position);
        holder.res_player.setText(mapResult.getPlayerName());
        holder.res_team.setText(mapResult.getTeamName());
        holder.res_score.setText(String.valueOf(mapResult.getScore()));
        holder.res_date.setText(mapResult.getDate());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}


