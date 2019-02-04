package com.mmm.noureddine.mapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.utils.DbBitmapUtility;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyViewHolder> {

    private List<Player> playerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNamePlayer;
        public CircleImageView itemAvatarPlayer;
        public TextView itemTeamPlayer;

        public MyViewHolder(View view) {
            super(view);
            itemNamePlayer = (TextView) view.findViewById(R.id.itemNamePlayer);
            itemAvatarPlayer = (CircleImageView) view.findViewById(R.id.itemAvatarPlayer);
            itemTeamPlayer = (TextView) view.findViewById(R.id.itemTeamPlayer);
        }

    }


    public PlayersAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public PlayersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_list_row, parent, false);
        return new PlayersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayersAdapter.MyViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.itemNamePlayer.setText(player.getPlayerPseudo());
        holder.itemTeamPlayer.setText(player.getPlayerTeam());
        holder.itemAvatarPlayer.setImageBitmap(DbBitmapUtility.getImage(player.getPlayerImage()));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }


}