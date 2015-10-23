package xyz.y_not.keiser_itunes_java1_4;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class items {

    private String artist;
    private String track;
    private String album;
    private String image;
    private String release;

    public items(){}

    public items(JSONObject apiData){
        try {
            artist = apiData.getString("artistName");
            track = apiData.getString("trackName");
            album = apiData.getString("collectionName");
            image = apiData.getString("artistName");
            release = apiData.getString("releaseDate");
        } catch (Exception e){
            Log.e("Error: ", "Updating JSON API");
        }
    }
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

//    SimpleDateFormat setFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//    Log.i("Time", setFormat.parse("safd"));

}
