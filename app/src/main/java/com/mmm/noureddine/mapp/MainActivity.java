package com.mmm.noureddine.mapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    // @Bind(R.id.start_game)
  //  ImageView start_game;
    CircleImageView start_game;

    @Bind(R.id.rollDices)
    Button recycleB;

    @Bind(R.id.team)
    Button team;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_game = (CircleImageView) findViewById(R.id.start_game);

        Picasso.get()
                .load(R.drawable.start)
                .into(start_game);
        start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Teams.class);
                startActivity(intent);
            }
        });

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(2000);
        start_game.startAnimation(anim);

    }


    @OnClick(R.id.rollDices)
    public void lancherRollActivity(View v) {
        Intent intent = new Intent(this, Roll_Dice.class);
        startActivity(intent);
    }

    @OnClick(R.id.team)
    public void selectTeam(View v) {
        Intent intent = new Intent(this, Teams.class);
        startActivity(intent);
    }



}