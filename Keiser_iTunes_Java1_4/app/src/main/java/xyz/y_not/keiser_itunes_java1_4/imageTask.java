package xyz.y_not.keiser_itunes_java1_4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.os.AsyncTask;
import java.io.*;


public class imageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView artistImage;

    public imageTask(ImageView artistImage) {
        this.artistImage = artistImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlString = urls[0];
        Bitmap myImage = null;
        try {
            InputStream in = new java.net.URL(urlString).openStream();
            myImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return myImage;
    }

    protected void onPostExecute(Bitmap result) {
        artistImage.setImageBitmap(result);
    }
}
