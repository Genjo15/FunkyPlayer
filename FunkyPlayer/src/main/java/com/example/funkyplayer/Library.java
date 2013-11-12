package com.example.funkyplayer;

import android.os.Environment;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Library implements Serializable
{
    private ArrayList<Song> library;


    /***************\
     * Constructor *
    \***************/

    public Library()
    {
        library = new ArrayList<Song>();
    }

    /*********************************************\
     * Fill the library from the internal memory *
    \*********************************************/

    public void FillLibrary()
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

    /************************\
     * Get all artists name *
    \************************/

    public ArrayList<String> GetArtistsName ()
    {
        ArrayList<String> artistsName = new ArrayList<String>();

        for(Song element : library)
        {
            artistsName.add(new String(element.GetArtist()));
        }

        // Remove duplicates
        HashSet hashsetTmp = new HashSet();
        hashsetTmp.addAll(artistsName);
        artistsName.clear();
        artistsName.addAll(hashsetTmp);

        Collections.sort(artistsName);

        return artistsName;
    }

    /************************\
     * Get albums of artist *
     \***********************/

    public ArrayList<Song> GetAlbumsOfArtists(String artist)
    {
        ArrayList<Song> filteredTrackList = new ArrayList<Song>();

        for(Song element : library)
        {
            if(element.GetArtist().equalsIgnoreCase(artist))
                filteredTrackList.add(element);
        }

        return filteredTrackList;
    }

    /***********************\
     * Get all albums name *
    \***********************/

    public ArrayList<String> GetAlbumsName ()
    {
        ArrayList<String> albumsName = new ArrayList<String>();

        for(Song element : library)
        {
           albumsName.add(new String(element.GetAlbum()));
        }

        // Remove duplicates
        HashSet hashsetTmp = new HashSet();
        hashsetTmp.addAll(albumsName);
        albumsName.clear();
        albumsName.addAll(hashsetTmp);

        Collections.sort(albumsName);

        return albumsName;
    }

    /**********************\
     * Get songs of album *
    \**********************/

    public ArrayList<Song> GetSongsOfAlbum(String album)
    {
        ArrayList<Song> filteredTrackList = new ArrayList<Song>();

        for(Song element : library)
        {
            if(element.GetAlbum().equalsIgnoreCase(album))
                filteredTrackList.add(element);
        }

        return filteredTrackList;
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

    /********************\
     * Get song to play *
    \********************/

    public Song GetSong(String songName)
    {
        //Song song2Play = new Song();
//
        //for(Song element : library)
        //{
        //    if(element.GetName().equalsIgnoreCase(songName))
        //        song2Play.CopyFrom(element);
        //}
//
        //return song2Play;

        int i = 0;
        boolean loop = true;

        while(i<(library.size()-1) && loop)
        {
            if(library.get(i).GetName().equalsIgnoreCase(songName))
                loop = false;
            else
                i++;
        }

        return library.get(i);
    }

    /*********************\
     * Getters / setters *
    \*********************/

    //public ArrayList<Song> GetLibrary(){return library;}
    public void SetLibrary(ArrayList<Song> list){library = list;}

}
