package com.example.funkyplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BySongs extends Activity
{
    private ListView listViewBySongs;
    private Library trackList;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // Get Library from intent
        trackList = (Library)getIntent().getSerializableExtra("library");

        // Set references to listView
        listViewBySongs = (ListView)findViewById(R.id.listview);

        // Set ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, trackList.GetSongsName());
        listViewBySongs.setAdapter(adapter);

        // ListView Item Click Listener
        listViewBySongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                // ListView Clicked item value
                String  itemValue = (String) listViewBySongs.getItemAtPosition(position);

                // Prepare bundle to sent
                Bundle bundle = new Bundle();
                bundle.putSerializable("library", trackList);

                // Create and prepare intent
                Intent intent = new Intent(BySongs.this, Launcher.class);
                intent.putExtras(bundle);
                intent.putExtra("song_selected", itemValue);

                // Switch activity
                startActivity(intent);
            }
        });
    }
}

