package xyz.y_not.keiser_tony_java14;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class java1_4 extends Activity {
    ConnectivityManager mgr;
    String myUrl = "https://api.spotify.com/v1/artists/7dGJo4pcD2V6oG8kP0tJRR/related-artists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java1_4);
        mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private class getObject extends AsyncTask<URL, Integer, JSONObject> {
        String TAG = "REPORT: ";

        @Override
        protected JSONObject doInBackground(URL... urls) {

            String jsonString = "";
            for (URL url : urls) {
                try {

                    URLConnection connection = url.openConnection();
                    jsonString = IOUtils.toString(connection.getInputStream());
                } catch (Exception e) {
                    Log.e(TAG, "Connection Failed");
                }
            }

            Log.i(TAG, "Collected Data");
            JSONObject apiData;

            try {
                apiData = new JSONObject(jsonString);
                Log.i(TAG, "Convert Success!");
            } catch (Exception e) {
                Log.e(TAG, "Convert Failed.");
                apiData = null;
            }

            try {
                apiData = (apiData != null) ? apiData.getJSONObject("artists") : null;
                Log.i(TAG, "Received: " + apiData.toString());
            } catch (Exception e) {

                //Fails Here, I think it is because the apiData is reading a JSONArray instead of
                //an JSONObject. I am now out of time.
                Log.e(TAG, "Failed");
                apiData = null;
            }
            return apiData;
        }

    }

    protected void onPostExecute(JSONObject apiData){
        jsonObject results = new jsonObject(apiData);
        updateDisplay(results);
    }

    private void updateDisplay(jsonObject results) {
        TextView tName = (TextView) findViewById(R.id.tName);
        tName.setText(results.getjName().toString());
        TextView tPopularity = (TextView) findViewById(R.id.tPopularity);
        tPopularity.setText(results.getjPopularity());
        TextView tId = (TextView) findViewById(R.id.tId);
        tId.setText(results.getjId().toString());
    }


    public void onClickEvent(View view) throws IOException {
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                System.out.println("Connected " + netInfo.getTypeName());
            } else if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                System.out.println("Connected " + netInfo.getTypeName());
            }
            if (netInfo.isConnected()) {
                System.out.println("Connected Boom Whats Next" + netInfo.isConnected());
                URL url = new URL(myUrl);
                new getObject().execute(url);

            }
        }


    }

}

