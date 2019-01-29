package com.mmm.noureddine.mapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.R;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyViewHolder> {

    private List<Player> playerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView playerItem;

        public MyViewHolder(View view) {
            super(view);
            playerItem = (TextView) view.findViewById(R.id.teamNameItem);
        }

    }


    public PlayersAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public PlayersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_list_row, parent, false);

        return new PlayersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayersAdapter.MyViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.playerItem.setText(player.getPlayerPseudo());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }


}