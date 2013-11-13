package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ByAlbums extends Activity
{
    private ListView listViewByAlbums;
    private Library trackList;
    private Boolean shakeForShuffleMode;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // Get Library from intent
        Intent intent = getIntent();
        trackList = (Library)intent.getSerializableExtra("library");
        shakeForShuffleMode = (Boolean)intent.getBooleanExtra("shake", false);

        // Set references to listView
        listViewByAlbums = (ListView)findViewById(R.id.listview);

        // Set ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, trackList.GetAlbumsName());
        listViewByAlbums.setAdapter(adapter);

        // ListView Item Click Listener
        listViewByAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                String  itemValue = (String) listViewByAlbums.getItemAtPosition(position);

                // Retrieve songs of selected album
                Library newTrackList = new Library();
                newTrackList.SetLibrary(trackList.GetSongsOfAlbum(itemValue));

                // Prepare bundle to sent
                Bundle bundle = new Bundle();
                bundle.putSerializable("library", newTrackList);

                // Create and prepare intent
                Intent intent = new Intent(ByAlbums.this, BySongs.class);
                intent.putExtras(bundle);
                intent.putExtra("shake", shakeForShuffleMode);

                // Switch activity
                startActivity(intent);

                // Show Alert test
                //Toast.makeText(getApplicationContext(),
                //        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                //        .show();

            }

        });
    }
}
