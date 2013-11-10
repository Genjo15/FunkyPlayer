package com.example.funkyplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BySongs extends Activity
{
    ListView listViewBySongs;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // Get Library from intent
        Library trackList = (Library)getIntent().getSerializableExtra("library");

        // Set references to listView
        listViewBySongs = (ListView)findViewById(R.id.listview);

        // Set ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, trackList.GetSongsName());
        listViewBySongs.setAdapter(adapter);
    }
}

