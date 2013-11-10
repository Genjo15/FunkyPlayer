package com.example.funkyplayer;

import android.annotation.TargetApi;
import android.media.MediaMetadataRetriever;
import android.os.Build;

public class Song
{
    private String path;
    private String name;


    public Song(String path)
    {
        this.path = path;
        RetrieveMetadata();

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void RetrieveMetadata()
    {
        MediaMetadataRetriever MMR = new MediaMetadataRetriever();
        MMR.setDataSource(path);

        try
        {
            name = MMR.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE).toString();
        }
        catch (Exception e) {
        }
    }
}

