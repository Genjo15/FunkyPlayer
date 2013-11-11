package com.example.funkyplayer;

import android.annotation.TargetApi;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import java.io.Serializable;

public class Song implements Serializable
{
    private String path;
    private String name;
    private String artist;
    private String album;
    //private byte[] image;

    /***************\
     * Constructor *
    \***************/

    public Song(String path)
    {
        this.path = path;
        RetrieveMetadata();
    }

    public Song()
    {
        path = new String();
        name = new String();
        artist = new String();
        album = new String();
        //image = null;
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
            byte[] art;

            name = MMR.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE).toString();
            artist = MMR.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST).toString();
            album = MMR.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM).toString();
            //art = MMR.getEmbeddedPicture();
            //System.arraycopy(art,0,this.image,0,art.length);
            //Bitmap bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
        }
        catch (Exception e) {
        }
    }

    /*********************\
     * Getters / setters *
    \*********************/

    public String GetName(){return name;}
    public String GetPath(){return path;}
    public String GetArtist(){return artist;}
    public String GetAlbum(){return album;}
    //public byte[] GetImage(){return image;}

    public void SetName(String name){this.name = name;}
    public void SetPath(String path){this.path = path;}
    public void SetArtist(String artist){this.artist = artist;}
    public void SetAlbum(String album){this.album = album;}
    //public void SetImage(byte[] image)
    //{
    //    System.arraycopy(image,0,this.image,0,image.length);
    //}

    public void CopyFrom(Song s)
    {
        SetPath(s.GetPath());
        SetName(s.GetName());
        SetArtist(s.GetArtist());
        SetAlbum(s.GetAlbum());
        //SetImage(s.GetImage());
    }
}

