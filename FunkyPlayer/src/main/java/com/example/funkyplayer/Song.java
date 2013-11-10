package com.example.funkyplayer;

import android.annotation.TargetApi;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import java.io.Serializable;

public class Song implements Serializable
{
    private String path;
    private String name;

    /***************\
     * Constructor *
    \***************/

    public Song(String path)
    {
        this.path = path;
        RetrieveMetadata();

    }

    /*********************\
     * Retrieve metadata *
    \*********************/

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

    /***********\
     * Getters *
    \***********/

    public String GetName(){return name;}
    public String GetPath(){return path;}

    public void SetName(String name){this.name = name;}
    public void SetPath(String path){this.path = path;}
}

