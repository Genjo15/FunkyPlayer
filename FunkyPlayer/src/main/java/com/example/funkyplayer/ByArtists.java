package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Anthony on 11/11/13.
 */
public class ByArtists extends Activity
{
    private ListView listViewByArtists;
    private Library trackList;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // Get Library from intent
        trackList = (Library)getIntent().getSerializableExtra("library");

        // Set references to listView
        listViewByArtists = (ListView)findViewById(R.id.listview);

        // Set ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, trackList.GetArtistsName());
        listViewByArtists.setAdapter(adapter);

        // ListView Item Click Listener
        listViewByArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {

                // ListView Clicked item value
                String  itemValue = (String) listViewByArtists.getItemAtPosition(position);

                // Retrieve albums of selected artist
                Library newTrackList = new Library();
                newTrackList.SetLibrary(trackList.GetAlbumsOfArtists(itemValue));

                // Prepare bundle to sent
                Bundle bundle = new Bundle();
                bundle.putSerializable("library", newTrackList);

                // Create and prepare intent
                Intent intent = new Intent(ByArtists.this, ByAlbums.class);
                intent.putExtras(bundle);

                // Switch activity
                startActivity(intent);

                // Show Alert test
                //Toast.makeText(getApplicationContext(),
                //        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                //         .show();
            }
        });
    }
}