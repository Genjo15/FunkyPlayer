package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

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
        musicalLibrary.FillLibrary();

        // Set references to buttons
        final Button byArtistsButton = (Button)findViewById(R.id.browse_artists);
        final Button byAlbumsButton = (Button)findViewById(R.id.browse_albums);
        final Button bySongsButton = (Button)findViewById(R.id.browse_songs);
        final CheckBox shakeForShuffleCheckBox = (CheckBox)findViewById(R.id.shake_checkbox);


        /****************************************************\
         * Event for clicking the "Browse by Artists" button *
         \***************************************************/

        byArtistsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Prepare bundle to sent
                Bundle bundle = new Bundle();
                bundle.putSerializable("library", musicalLibrary);

                // Create and prepare intent
                Intent intent = new Intent(MainMenu.this, ByArtists.class);
                intent.putExtras(bundle);
                intent.putExtra("shake", shakeForShuffleCheckBox.isChecked());

                // Switch activity
                startActivity(intent);
            }
        });

        /****************************************************\
         * Event for clicking the "Browse by Albums" button *
        \***************************************************/

        byAlbumsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Prepare bundle to sent
                Bundle bundle = new Bundle();
                bundle.putSerializable("library", musicalLibrary);

                // Create and prepare intent
                Intent intent = new Intent(MainMenu.this, ByAlbums.class);
                intent.putExtras(bundle);
                intent.putExtra("shake", shakeForShuffleCheckBox.isChecked());

                // Switch activity
                startActivity(intent);
            }
        });

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
                intent.putExtra("shake", shakeForShuffleCheckBox.isChecked());

                // Switch activity
                startActivity(intent);
            }
        });
    }
}