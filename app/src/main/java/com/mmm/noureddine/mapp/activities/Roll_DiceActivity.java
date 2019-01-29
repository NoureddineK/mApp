package com.mmm.noureddine.mapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mmm.noureddine.mapp.R;

import java.util.Random;

public class Roll_DiceActivity extends AppCompatActivity {
    public static final Random RANDOM = new Random();
    private Button rollDices;
    private ImageView imageView1, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll__dice);
        rollDices = (Button) findViewById(R.id.rollDices);
        imageView1 = (ImageView) findViewById(R.id.imageView1);

        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value1 = randomDiceValue();

                int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.mmm.noureddine.mapp");

                imageView1.setImageResource(res1);

            }
        });
    }

    public static int randomDiceValue() {
        return RANDOM.nextInt(3) + 1;
    }

}
