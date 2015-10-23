package xyz.y_not.keiser_itunes_java1_4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class iTuneSearch extends Activity {

    //Variables
    EditText getText;
    Button getResults;
    ImageView artistImage;
    TextView artistText;
    TextView albumText;
    TextView trackText;
    TextView releaseText;
    ListView listResults;
    ConnectivityManager mgr;
    ArrayList<JSONObject> itemObject = new ArrayList<JSONObject>();
    ArrayAdapter itemAdapter;
    ProgressBar progress;

    //API Vars
    private String artist;
    private String track;
    private String album;
    private String image;
    private String release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_tune_search);

        //Set Variables
        getText = (EditText) findViewById(R.id.getText);
        getResults = (Button) findViewById(R.id.getResults);
        artistImage = (ImageView) findViewById(R.id.artistImage);
        artistText = (TextView) findViewById(R.id.artistText);
        albumText = (TextView) findViewById(R.id.albumText);
        trackText = (TextView) findViewById(R.id.trackText);
        releaseText = (TextView) findViewById(R.id.releaseText);
        listResults = (ListView) findViewById(R.id.listResults);
        mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        progress = (ProgressBar)findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);
        clearSetting();

    }

    public void clearSetting(){
        artistText.setText("");
        trackText.setText("");
        albumText.setText("");
        releaseText.setText("");
        artistImage.setImageBitmap(null);
    }
    //JSON
    private class asyncTask extends AsyncTask<URL, Integer, JSONArray> {
        JSONObject singleData;

        @Override
        protected JSONArray doInBackground(URL... urls) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(5);
            String jsonString = null;
            URLConnection connection;
            for (URL queryURL : urls) {
                try {
                    connection = queryURL.openConnection();
                    jsonString = IOUtils.toString(connection.getInputStream());
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JSONArray apiData;
            JSONObject resultsObject;
            progress.setProgress(15);
            try {
                resultsObject = new JSONObject(jsonString.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                resultsObject = null;
            }
            progress.setProgress(25);
            try {
                apiData = resultsObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
                apiData = null;
            }
            progress.setProgress(35);
            return apiData;
        }

        protected void onPostExecute(JSONArray apiData) {
                try {
                    for (int i = 0; i < apiData.length() ; i++) {
                        singleData = (apiData != null) ? apiData.getJSONObject(i) : null;
                        itemObject.add(singleData);
                        progress.setProgress(45 + i);
                    }
                    progress.setProgress(100);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            setList();
            progress.setVisibility(View.INVISIBLE);
        }
    }

    private void setList() {

        itemAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemObject);
        Log.i(this.toString(), "Adapter");
        listResults.setAdapter(itemAdapter);
        listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position,
                                    long id) {
                {
                    try {
                        artist = itemObject.get(position).getString("artistName");
                        track = itemObject.get(position).getString("trackName");
                        album = itemObject.get(position).getString("collectionName");
                        image = itemObject.get(position).getString("artworkUrl100");
                        release = itemObject.get(position).getString("releaseDate");
                        //Date Format
                        SimpleDateFormat setFormat;
                        setFormat =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date setDate;

                        try {
                            setDate = setFormat.parse(release);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            setDate = null;
                        }
                        artistText.setText(artist);
                        trackText.setText(track);
                        albumText.setText(album);
                        releaseText.setText(setDate.toString());
                        getImage(image);

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    protected void getImage(String getUrl) {
        String imageUrl = getUrl;
        // Get an Image
        try{
            AsyncTask<String, Void, Bitmap> execute = new imageTask((ImageView)
                    findViewById(R.id.artistImage)).execute(imageUrl);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onConnect() {
        progress.setVisibility(View.VISIBLE);
        itemObject.clear();
        try {
            String text = getText.getText().toString();
            String search = text.replace(" ", "+");
            String baseURL = "https://itunes.apple.com/search?term=";
            URL queryURL = new URL(baseURL + search);
            new asyncTask().execute(queryURL);
        } catch (Exception e) {
            Log.e("ERROR", "Error converting result " + e.toString());
        }
    }
    public void onGetJson(View view) throws IOException {
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, "\"" + netInfo.getTypeName() + "\"", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "FETCHING DATA", Toast.LENGTH_LONG).show();
                onConnect();
            } else if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "\"" + netInfo.getTypeName() + "\"", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "FETCHING DATA", Toast.LENGTH_LONG).show();
                onConnect();
            } 
        } else {
            Toast.makeText(this, "NO INTERNET CONNECTION!", Toast.LENGTH_LONG).show();
        }
    }
}
