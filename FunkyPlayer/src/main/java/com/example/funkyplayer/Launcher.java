package com.example.funkyplayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;


public class Launcher extends Activity
{
    private final Handler handler = new Handler();

    private Library trackList;
    private String musicPlayedName;
    private static MediaPlayer mediaPlayer;
    private Song song2Play;
    private Boolean random;
    private Boolean repeat;

    private ImageButton buttonPlayPause;
    private ImageButton buttonRandom;
    private ImageButton buttonRepeat;
    private ImageView songCoverArt;
    private TextView textViewSong;
    private TextView textViewArtist;
    private TextView textViewAlbum;
    private SeekBar seekbar;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        // Initialize booleans
        random = false;
        repeat = false;

        // Get Library from intent + selected song name
        Intent intent = getIntent();
        trackList = (Library)intent.getSerializableExtra("library");
        musicPlayedName = intent.getStringExtra("song_selected");

        // Set references to widgets
        buttonPlayPause = (ImageButton) findViewById(R.id.button_play_pause);
        buttonRandom = (ImageButton)findViewById(R.id.button_random);
        buttonRepeat = (ImageButton)findViewById(R.id.button_repeat);
        songCoverArt = (ImageView) findViewById(R.id.album_art);
        textViewSong = (TextView)findViewById(R.id.song);
        textViewArtist = (TextView)findViewById(R.id.artist);
        textViewAlbum = (TextView)findViewById(R.id.album);
        seekbar = (SeekBar)findViewById(R.id.seekbar);

        // Launch song
        LaunchSong();


        /*******************************************\
         * Set event handler for play/pause button *
        \*******************************************/

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    buttonPlayPause.setImageResource(R.drawable.play);
                }

                else
                {
                    mediaPlayer.start();
                    StartPlayProgressUpdater();
                    buttonPlayPause.setImageResource(R.drawable.pause);
                }
            }
        });

        /***************************************\
         * Set event handler for random button *
        \***************************************/

        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!random)
                {
                    random = true;
                    buttonRandom.setImageResource(R.drawable.random_enabled);
                    Toast.makeText(getApplicationContext(),"Random mode enabled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    random = false;
                    buttonRandom.setImageResource(R.drawable.random_disabled);
                    Toast.makeText(getApplicationContext(),"Random mode disabled", Toast.LENGTH_LONG).show();
                }
            }
        });

        /***************************************\
         * Set event handler for repeat button *
        \***************************************/

        buttonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!repeat)
                {
                    repeat = true;
                    buttonRepeat.setImageResource(R.drawable.repeat_enabled);
                    Toast.makeText(getApplicationContext(),"Repeat enabled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    repeat = false;
                    buttonRepeat.setImageResource(R.drawable.repeat_disabled);
                    Toast.makeText(getApplicationContext(),"Repeat disabled", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*************************************\
         * Set event handler for the seekbar *
        \*************************************/

        seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                SeekBar sb = (SeekBar)view;
                mediaPlayer.seekTo(sb.getProgress());

                return false;
            }
        });
    }

    /***************\
     * Launch Song *
    \***************/

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void LaunchSong()
    {
        // Get song to play
        song2Play = trackList.GetSong(musicPlayedName);

        // Create MediaPlayer object (if an instance is running kill it)
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(song2Play.GetPath()));
        mediaPlayer.start();
        StartPlayProgressUpdater();

        // Set song name
        textViewSong.setText(song2Play.GetName());

        // Set artist name
        textViewArtist.setText(song2Play.GetArtist());

        // Set album name
        textViewAlbum.setText(song2Play.GetAlbum());

        // Set image art
        MediaMetadataRetriever MMR= new MediaMetadataRetriever();
        MMR.setDataSource(song2Play.GetPath());
        byte[] art;
        art = MMR.getEmbeddedPicture();
        Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
        songCoverArt.setImageBitmap(songImage);

        // Set seekbar
        seekbar.setMax(mediaPlayer.getDuration());
    }

    /***********************\
     * Update Progress bar *
    \***********************/

    public void StartPlayProgressUpdater()
    {
        seekbar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying())
        {
            Runnable notification = new Runnable() {
                @Override
                public void run() {
                    StartPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
        else
        {
            mediaPlayer.pause();
            seekbar.setProgress(0);
        }
    }
}