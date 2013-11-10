package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainMenu extends Activity
{
    Library musicalLibrary;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Create library
        musicalLibrary = new Library();

        // Set references to buttons
        final Button bySongsButton = (Button)findViewById(R.id.browse_songs);


        /*********************************************\
         * Event for clicking the "All Songs" button *
        \*********************************************/

        bySongsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Prepare bundle to sent
                Bundle bundle = new Bundle();
                bundle.putSerializable("library", musicalLibrary);

                // Create and prepare intent
                Intent intent = new Intent(MainMenu.this, BySongs.class);
                intent.putExtras(bundle);

                // Switch activity
                startActivity(intent);
            }
        });
    }
}