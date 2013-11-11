package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;


public class Launcher extends Activity
{
    private Library trackList;
    private String musicPlayedName;
    private MediaPlayer mediaPlayer;
    private Button buttonPlayStop;
    private Song song2Play;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        // Get Library from intent + selected song name
        Intent intent = getIntent();
        trackList = (Library)intent.getSerializableExtra("library");
        musicPlayedName = intent.getStringExtra("song_selected");

        // Set references to widgets
        buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);

        // Get song to play
        song2Play = trackList.GetSong(musicPlayedName);

        // Show Alert test
        Toast.makeText(getApplicationContext(),
                "name :" + song2Play.GetName() + "  path : " + song2Play.GetPath() + "   artist : " + song2Play.GetArtist() + "   album : " + song2Play.GetAlbum(), Toast.LENGTH_LONG)
                .show();

        // Create MediaPlayer object
       //URI uri = new ur
       //mediaPlayer = MediaPlayer.create(this, R.raw.test);


    }
}