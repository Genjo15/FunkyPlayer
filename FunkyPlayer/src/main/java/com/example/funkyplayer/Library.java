package com.example.funkyplayer;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 10/11/13.
 */
public class Library
{
    private final String MEDIA_PATH = "/sdcard/MusicTest/";
    private List<Song> library;


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
}
