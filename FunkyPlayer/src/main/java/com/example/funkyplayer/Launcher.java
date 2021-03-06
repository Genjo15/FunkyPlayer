package com.example.funkyplayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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
import java.util.Arrays;
import java.util.Collections;


public class Launcher extends Activity
{
    private final Handler handler = new Handler();

    private Library trackList;
    private Library trackListBackup;
    private String music2Play;
    private static MediaPlayer mediaPlayer;
    private Boolean random;
    private Boolean repeat;
    private Boolean shakeForShuffleMode;
    private int idx;

    private ImageButton buttonPlayPause;
    private ImageButton buttonNextSong;
    private ImageButton buttonPreviousSong;
    private ImageButton buttonRandom;
    private ImageButton buttonRepeat;
    private ImageView songCoverArt;
    private TextView textViewSong;
    private TextView textViewArtist;
    private TextView textViewAlbum;
    private SeekBar seekbar;

    // For shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

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
        Collections.sort(trackList.GetSongsName());
        music2Play = intent.getStringExtra("song_selected");
        shakeForShuffleMode = intent.getBooleanExtra("shake", false);

        // Save trackList
        trackListBackup = new Library();
        trackListBackup.CopyFrom(trackList);

        // Set references to widgets
        buttonPlayPause = (ImageButton) findViewById(R.id.button_play_pause);
        buttonNextSong = (ImageButton) findViewById(R.id.button_next);
        buttonPreviousSong = (ImageButton) findViewById(R.id.button_previous);
        buttonRandom = (ImageButton)findViewById(R.id.button_random);
        buttonRepeat = (ImageButton)findViewById(R.id.button_repeat);
        songCoverArt = (ImageView) findViewById(R.id.album_art);
        textViewSong = (TextView)findViewById(R.id.song);
        textViewArtist = (TextView)findViewById(R.id.artist);
        textViewAlbum = (TextView)findViewById(R.id.album);
        seekbar = (SeekBar)findViewById(R.id.seekbar);

        // Launch song
        LaunchSong(music2Play);
        idx = trackList.IndexOfSong(trackList.GetSong(music2Play));


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

        /******************************************\
         * Set event handler for next song button *
        \******************************************/

        buttonNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                idx++;
                if (repeat) idx --;
                if (idx > trackList.GetLibrary().size()-1)
                    idx=0;

                LaunchSong(trackList.GetLibrary().get(idx).GetName());
            }
        });



        /**********************************************\
         * Set event handler for previous song button *
        \**********************************************/

        buttonPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                idx--;
                if (idx == -1)
                    idx=trackList.GetLibrary().size()-1;

                LaunchSong(trackList.GetLibrary().get(idx).GetName());
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

                    // Shuffle the tracklist
                    Collections.shuffle(trackList.GetLibrary());

                    // update the index
                    idx = trackList.IndexOfSong(trackList.GetSong(music2Play));
                }
                else
                {
                    random = false;
                    buttonRandom.setImageResource(R.drawable.random_disabled);
                    Toast.makeText(getApplicationContext(),"Random mode disabled", Toast.LENGTH_LONG).show();

                    // Rollback the tracklist
                    trackList.CopyFrom(trackListBackup);

                    // update the index
                    idx = trackList.IndexOfSong(trackList.GetSong(music2Play));
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

        /*********************************\
         * Shake Detector Initialization *
        \*********************************/


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        if (shakeForShuffleMode)
        {
            mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

                @Override
                public void onShake(int count) {
                    /*
                     * The following method, "handleShakeEvent(count):" is a stub //
                     * method you would use to setup whatever you want done once the
                     * device has been shook.
                     */
                    handleShakeEvent(count);
                }
            });
        }
    }

    /****************\
     * Shuffle Song *
    \****************/

    private void handleShakeEvent(int count)
    {
        idx++;
        if (repeat) idx --;
        if (idx > trackList.GetLibrary().size()-1)
            idx=0;

        LaunchSong(trackList.GetLibrary().get(idx).GetName());
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    /***************\
     * Launch Song *
    \***************/

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void LaunchSong(String music)
    {
        // Create MediaPlayer object (if an instance is running kill it)
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(trackList.GetSong(music).GetPath()));

        // Listener for notifying when a song ends
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                idx++;
                if (repeat) idx --;
                if (idx > trackList.GetLibrary().size()-1)
                    idx=0;

                LaunchSong(trackList.GetLibrary().get(idx).GetName());
            }
        });

        mediaPlayer.start();
        StartPlayProgressUpdater();

        // Set song name
        textViewSong.setText(trackList.GetSong(music).GetName());

        // Set artist name
        textViewArtist.setText(trackList.GetSong(music).GetArtist());

        // Set album name
        textViewAlbum.setText(trackList.GetSong(music).GetAlbum());

        // Set image art
        MediaMetadataRetriever MMR= new MediaMetadataRetriever();
        MMR.setDataSource(trackList.GetSong(music).GetPath());
        byte[] art;
        art = MMR.getEmbeddedPicture();
        Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
        songCoverArt.setImageBitmap(songImage);

        // Set seekbarrrrr
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
            //seekbar.setProgress(0);
        }
    }
}