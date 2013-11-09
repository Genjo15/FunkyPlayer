package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Set references to buttons
        final Button bySongsButton = (Button)findViewById(R.id.browse_songs);

        // Event for clicking the "All Songs" button
        bySongsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenu.this, BySongs.class);
                startActivity(intent);
            }
        });
    }
}