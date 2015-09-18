package xyz.y_not.keiser_tony_java13;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.widget.AdapterView;
import android.view.View;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import xyz.y_not.keiser_tony_java13.collection;

import static xyz.y_not.keiser_tony_java13.R.layout.landscape;


public class java1dot3 extends Activity {

    ArrayAdapter<String> collectionAdapter;
    ArrayList<String> keyList = new ArrayList<String>();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Set Collection
        collection task = new collection();
        //Set data 1
        task.setcName("Tide Blue");
        task.setcColor("#30374F");
        task.setcFood("Blueberry");
        //Set data 2
        task.setcName("Seafaring Green");
        task.setcColor("#408156");
        task.setcFood("Kiwi");
        //Set data 3
        task.setcName("Ahoy Green");
        task.setcColor("#93A31C");
        task.setcFood("Lime");
        //Set data 4
        task.setcName("Yacht Yellow");
        task.setcColor("#D6AA26");
        task.setcFood("Banana");
        //Set data 5
        task.setcName("Sailor Red");
        task.setcColor("#BD2A33");
        task.setcFood("Raspberry");

        final Set<String> keys = collection.clickers.keySet();


        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_LONG).show();
            //Set View
            setContentView(landscape);
            final ListView listItems = (ListView) findViewById(R.id.listView);

            collectionAdapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_list_item_1,keyList);

            listItems.setAdapter(collectionAdapter);

            listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                    {

                        //.setBackgroundColor(Color.parseColor(collection.clickers.get
                          //      (listItems.getSelectedItem().toString())));

                        TextView text1 = (TextView) findViewById(R.id.textView);
                        TextView text2 = (TextView) findViewById(R.id.textView2);
                        TextView text3 = (TextView) findViewById(R.id.textView3);
                        text1.setText(collection.texters.get(position));
                        text2.setText(collection.texters.get(position));
                        text3.setText(collection.texters.get(position));
                    }


                }});

            }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                Toast.makeText(this, "portrait", Toast.LENGTH_LONG).show();
                    //Set View
                setContentView(R.layout.portrait);

                //Create Spinner
                final Spinner pSpinner = (Spinner) findViewById(R.id.spinner);

                    for (String key : keys) keyList.add(key);

                collectionAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_spinner_dropdown_item,keyList);

                pSpinner.setAdapter(collectionAdapter);

                //ADD SPINNER EVENT LISTENER
                pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        view.setBackgroundColor(Color.parseColor(collection.clickers.get
                                (pSpinner.getSelectedItem().toString())));

                        TextView text4 = (TextView)findViewById(R.id.textView4);
                        TextView text5 = (TextView)findViewById(R.id.textView5);
                        TextView text6 = (TextView)findViewById(R.id.textView6);
                        text4.setText(collection.texters.get(i));
                        text5.setText(collection.texters.get(i));
                        text6.setText(collection.texters.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onConfigurationChanged(getResources().getConfiguration());
    }

}


