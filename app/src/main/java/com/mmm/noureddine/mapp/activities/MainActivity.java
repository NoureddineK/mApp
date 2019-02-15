package com.mmm.noureddine.mapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mmm.noureddine.mapp.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    CircleImageView start_game;
    @Bind(R.id.help)
    Button helpBtn;

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
                Intent intent = new Intent(getBaseContext(), TeamActivity.class);
                startActivity(intent);
            }
        });

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(2000);
        start_game.startAnimation(anim);

    }

    @OnClick(R.id.help)
    public void gethelp(View view) {
        showAddItemDialog(this);
    }


    private void showAddItemDialog(Context c) {
        final TextView taskEditText = new TextView(c);
        taskEditText.setVerticalScrollBarEnabled(true);
        taskEditText.setMovementMethod(ScrollingMovementMethod.getInstance());

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Welcome To Let's Mime! \n ")
                .setMessage(getResources().getString(R.string.regles)+"\n"+getResources().getString(R.string.msg))
                .setView(taskEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                    }
                }).create();
        dialog.show();
    }
}