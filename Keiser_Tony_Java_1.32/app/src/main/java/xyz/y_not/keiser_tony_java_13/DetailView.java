package xyz.y_not.keiser_tony_java_13;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.Set;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Configuration;
import static xyz.y_not.keiser_tony_java_13.R.layout.landscape;

import java.util.ArrayList;

public class DetailView extends Activity {

    public ArrayList<customClass> collection = new ArrayList<customClass>();
    public ArrayAdapter collectionAdapter;
    public ArrayList<String> cTitles = new ArrayList<String>();
    public ArrayList<String> cPlots = new ArrayList<String>();
    public ArrayList<String> cDates = new ArrayList<String>();
    public ListView listItems;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(landscape);
            //Variables
            final TextView tTitle = (TextView) findViewById(R.id.movieTitle);
            final TextView tPlot = (TextView) findViewById(R.id.moviePlot);
            final TextView tDate = (TextView) findViewById(R.id.movieRelease);
            //Defaults
            tTitle.setText(cTitles.get(0));
            tPlot.setText(cPlots.get(0));
            tDate.setText(cDates.get(0));

            listItems = (ListView) findViewById(R.id.listView);
            listItems.setAdapter(collectionAdapter);
            listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position,
                                        long id) {
                    {
                        tTitle.setText(cTitles.get(position));
                        tPlot.setText(cPlots.get(position));
                        tDate.setText(cDates.get(position));
                    }
                }
            });
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Set View
            setContentView(R.layout.portrait);
            //Variables
            final TextView tTitle = (TextView) findViewById(R.id.movieTitle);
            final TextView tPlot = (TextView) findViewById(R.id.moviePlot);
            final TextView tDate = (TextView) findViewById(R.id.movieRelease);
            //Defaults
            tTitle.setText(cTitles.get(0));
            tPlot.setText(cPlots.get(0));
            tDate.setText(cDates.get(0));

            Spinner dropDown = (Spinner) findViewById(R.id.dropDown);

            dropDown.setAdapter(collectionAdapter);

            //ADD SPINNER EVENT LISTENER
            dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Defaults
                    tTitle.setText(cTitles.get(i));
                    tPlot.setText(cPlots.get(i));
                    tDate.setText(cDates.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });

        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        customClass movie1 = new customClass("The Martian",
                "During a manned mission to Mars, Astronaut Mark Watney is presumed dead after " +
                        "a fierce storm and left behind by his crew. But Watney has survived " +
                        "and finds himself stranded and alone on the hostile planet. With only " +
                        "meager supplies, he must draw upon his ingenuity, wit and spirit to " +
                        "subsist and find a way to signal to Earth that he is alive.",
                "2 October 2015");

        customClass movie2 = new customClass("Steve Jobs",
                "Set backstage at three iconic product launches and ending in 1998 with the " +
                        "unveiling of the iMac, Steve Jobs takes us behind the scenes of the " +
                        "digital revolution to paint a portrait of the man at its epicenter.",
                "23 October 2015");

        customClass movie3 = new customClass("The Night Before",
                "In New York City for their annual tradition of Christmas Eve debauchery, three" +
                        " lifelong best friends set out to find the Holy Grail of Christmas" +
                        " parties since their yearly reunion might be coming to an end.",
                "20 November 2015");
        customClass movie4 = new customClass("Ride Along 2",
                "As his wedding day approaches, Ben heads to Miami with his soon-to-be " +
                        "brother-in-law James to bring down a drug dealer who's supplying the " +
                        "dealers of Atlanta with product.",
                "15 January 2016");
        customClass movie5 = new customClass("Dead Pool",
                "A former Special Forces operative turned mercenary is subjected to a rogue " +
                        "experiment that leaves him with accelerated healing powers and adopts " +
                        "the alter ego Deadpool.",
                "12 February 2016");

        collection.add(movie1);
        collection.add(movie2);
        collection.add(movie3);
        collection.add(movie4);
        collection.add(movie5);

        for (int i = 0; i < collection.size(); i++) {
            // System.out.println(collection.get(i).getMovieTitle());
            cTitles.add(collection.get(i).getMovieTitle());
            // System.out.println(collection.get(i).getMoviePlot());
            cPlots.add(collection.get(i).getMoviePlot());
            // System.out.println(collection.get(i).getMovieRelease());
            cDates.add(collection.get(i).getMovieRelease());
        }
        collectionAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,cTitles);

        onConfigurationChanged(getResources().getConfiguration());
    }
}

