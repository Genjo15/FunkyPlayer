package com.example.funkyplayer;

import android.os.Environment;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anthony on 10/11/13.
 */
public class Library implements Serializable
{
    private ArrayList<Song> library;


    /***************\
     * Constructor *
    \***************/

    public Library()
    {

        library = new ArrayList<Song>();
        FillLibrary();
    }

    /*********************************************\
     * Fill the library from the internal memory *
    \*********************************************/

    private void FillLibrary()
    {
        File root = new File(Environment.getExternalStorageDirectory().toString() + "/MusicTest/");
        AnalyzeDirectory(root);
    }

    private void AnalyzeDirectory(File directory)
    {
        File[] listFiles = directory.listFiles();

        for(int i = 0; i < listFiles.length; i++)
        {
            if(listFiles[i].isDirectory())
                AnalyzeDirectory(listFiles[i]);
            else if(listFiles[i].getPath().endsWith(".mp3") || listFiles[i].getPath().endsWith(".MP3"))
                library.add(new Song(listFiles[i].getPath()));
        }
    }

    /**********************\
     * Get all songs name *
    \**********************/

    public ArrayList<String> GetSongsName ()
    {
        ArrayList<String> songsName = new ArrayList<String>();

        for(Song element : library)
        {
            songsName.add(new String(element.GetName()));
        }
        Collections.sort(songsName);
        songsName.add(0, new String("~ SHUFFLE ~"));

        return songsName;
    }

    /*********************\
     * Getters / setters *
    \*********************/

    public ArrayList<Song> GetLibrary(){return library;}
    public void SetSongs(ArrayList<Song> list){library = list;}

}
