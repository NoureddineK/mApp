package com.mmm.noureddine.mapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mmm.noureddine.mapp.db.DBHandler;
import com.mmm.noureddine.mapp.components.Player;
import com.mmm.noureddine.mapp.adapter.PlayersAdapter;
import com.mmm.noureddine.mapp.R;
import com.mmm.noureddine.mapp.components.RecyclerItemClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    private List<Player> playerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlayersAdapter mAdapter;

    @Bind(R.id.plus_button)
    Button plus_button;

    @Bind(R.id.take_picture_btn)
    Button take_picture_btn;

    private EditText player_name;

    @Bind(R.id.avatar)
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Intent intent = getIntent();
        final String message = intent.getStringExtra("message");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        player_name = (EditText) findViewById(R.id.player_name);


        mAdapter = new PlayersAdapter(playerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        preparePlayerData();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //  int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Player item = playerList.get(position);
                        Toast.makeText(getBaseContext(), item.getPlayerPseudo() + " Selected ", Toast.LENGTH_LONG).show();

                      //  Intent intent = new Intent(getBaseContext(), PlayerActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Player item = PlayerList.get(position);
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    }
                })
        );

    }

    @OnClick(R.id.plus_button)
    public void addingPlayers(View v) {
        AddingPlayerData(player_name.getText().toString());
    }

    private void preparePlayerData() {

        DBHandler db = new DBHandler(this);
        Log.d("Insert: ", "Inserting ..");
        db.addPlayer(new Player("Player A"));
        db.addPlayer(new Player("Player B"));

// Reading all shops
        Log.d("Reading: ", "Reading all Players..");
        List<Player> players = db.getAllPlayers();

        for (Player player : players) {
            String log = "Id: " + player.getID() + " ,Name: " + player.getPlayerPseudo();
// Writing shops to log
            Log.d("Player: : ", log);
            playerList.add(player);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void AddingPlayerData(String name) {
        if (!name.equals("") && !name.equals(" ")) {
            DBHandler db = new DBHandler(this);
            Log.d("Insert: ", "Inserting ..");
            Player player = new Player(name);
            db.addPlayer(player);
            playerList.add(player);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.take_picture_btn)
    public void takeAphoto(View v){
      //  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        //    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
       // }

        Intent intent = new Intent(getBaseContext(), CameraActivity.class);
      //  intent.putExtra("message", "This message comes from PassingDataSourceActivity's first button");
        startActivity(intent);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Log.d("Data", "get data");
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            avatar.setImageBitmap(imageBitmap);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
