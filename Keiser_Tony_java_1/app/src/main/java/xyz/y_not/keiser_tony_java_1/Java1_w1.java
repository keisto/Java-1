package xyz.y_not.keiser_tony_java_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

public class Java1_w1 extends Activity {
    // Item Call List
    EditText textAdd;
    EditText textSearch;
    TextView recentAdd;
    TextView updateAdd;
    TextView textCount;
    TextView textLength;
    Button addButton;
    Button searchButton;
    ArrayList<String> dataCollection;
    AlertDialog.Builder getAlert;
    Double results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java1_w1);

        // Get Call List Items
        textAdd = (EditText) findViewById(R.id.textAdd);
        textSearch = (EditText) findViewById(R.id.textSearch);
        textLength = (TextView) findViewById(R.id.textLength);
        textCount = (TextView) findViewById(R.id.textCount);
        recentAdd = (TextView) findViewById(R.id.recentAdd);
        updateAdd = (TextView) findViewById(R.id.updateAdd);
        addButton = (Button) findViewById(R.id.addButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        dataCollection = new ArrayList<String>();
        getAlert = new AlertDialog.Builder(this);

    }

    // Search data onClick (Check if data has any input)
    public void onSearchHandler(View view){ // Do something onClick
        if ((textSearch.getText().toString().equals(""))){
            getAlert.setTitle(Html.fromHtml("<b>" +
                    getResources().getString(R.string.error_string) + "!" + "</b>"));
            getAlert.setCancelable(true);
            getAlert.setMessage(Html.fromHtml("<b>" +
                    getResources().getString(R.string.value_alert) + "." + "</b>"));
            getAlert.setPositiveButton(Html.fromHtml("<b>" +
                            getResources().getString(R.string.okay_string) + "</b>"),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = getAlert.create();
            dialog.show();
        // If ID is out of range
        } else if (Integer.parseInt(textSearch.getText().toString())>dataCollection.size()){
            getAlert.setTitle(Html.fromHtml("<b>" +
                    getResources().getString(R.string.error_string) + "!" + "</b>"));
            getAlert.setCancelable(true);
            getAlert.setMessage(Html.fromHtml("<b>" +
                    getResources().getString(R.string.range_alert) + "." + "</b>"));
            getAlert.setPositiveButton(Html.fromHtml("<b>" +
                            getResources().getString(R.string.okay_string) + "</b>"),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = getAlert.create();
            dialog.show();
        } else {
            // If dataCollection not empty... search dataCollection
            if (dataCollection.size() != 0) {

                getAlert.setTitle(Html.fromHtml("<b>" +
                        getResources().getString(R.string.search_alert) + ":" + "</b>"));
                getAlert.setCancelable(true);
                getAlert.setMessage(Html.fromHtml("<b>" +
                        getResources().getString(R.string.you_found) + ": " +
                        // Subtracting 1 so array count starts at 1 in lieu of 0)
                        dataCollection.get(Integer.parseInt(textSearch.getText().toString()) - 1) +
                        "</b>"));
                getAlert.setPositiveButton(Html.fromHtml("<b>" +
                                getResources().getString(R.string.okay_string) + "</b>"),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = getAlert.create();
                dialog.show();

                // Error due to no data in dataCollection
            } else {
                getAlert.setTitle(Html.fromHtml("<b>" +
                        getResources().getString(R.string.error_string) + "!" + "</b>"));
                getAlert.setCancelable(true);
                getAlert.setMessage(Html.fromHtml("<b>" +
                        getResources().getString(R.string.no_data) + "!" + "</b>"));
                getAlert.setPositiveButton(Html.fromHtml("<b>" +
                        getResources().getString(R.string.okay_string)+ "</b>"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = getAlert.create();
                dialog.show();
            }
        }
        textSearch.setText(""); // Reset EditText field
    }


    // Add data onClick (Check if EditText has input)
    public void onAddHandler(View view){ // Do something onClick
        if (textAdd.getText().toString().equals("") ||
                textAdd.getText().toString().trim().equals("") ) { // Alert empty field
            getAlert.setTitle(Html.fromHtml("<b>" +
                    getResources().getString(R.string.error_string) + "!" + "</b>"));
            getAlert.setCancelable(true);
            getAlert.setMessage(Html.fromHtml("<b>" +
                    getResources().getString(R.string.add_text) + "." + "</b>"));
                    getAlert.setPositiveButton(Html.fromHtml("<b>" +
                                    getResources().getString(R.string.okay_string) + "</b>"),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog dialog = getAlert.create();
            dialog.show();
        } else { // Add text to dataCollection
            dataCollection.add(textAdd.getText().toString());
            recentAdd.setText(getResources().getString(R.string.recent_add) + ": " +
                    textAdd.getText().toString() +
                    ", ID: " + dataCollection.size() + ", " +
                    getResources().getString(R.string.recent_length) + ": " +
                            dataCollection.get(dataCollection.size() - 1).length());
            textAdd.setText(""); // Reset EditText field

            //Update dataCollection Stats
            getMedian();
            textLength.setText(getResources().getString(R.string.median_string) +
                    ": " + results);
            textCount.setText(getResources().getString(R.string.count_string) +
                    ": " + dataCollection.size());

            //Update Hint
            if (dataCollection.size()==1){
                updateAdd.setText(getResources().getString(R.string.enter_info));
            } else if (dataCollection.size()>1){
                updateAdd.setText(getResources().getString(R.string.enter_info) +
                        " - " + dataCollection.size());
            } else {
                return; //Should never be called, but just in case
            }
        }
    }

    public double getMedian() {
        ArrayList<Integer> medianArray = new ArrayList<Integer>();
        if (dataCollection.size() == 0) {
            return 0;
        } else {
            int x;
            //Create an array of Ints
            for (int i = 0; i < dataCollection.size(); i++) {
                medianArray.add(dataCollection.get(i).length());
            }
            //Order array
            Collections.sort(medianArray);

            if (medianArray.size() % 2 == 0) {
                //Even
                x = medianArray.size() / 2;
                results = (Double.valueOf(medianArray.get(x) + medianArray.get(x - 1)) / 2);

                return results;
            } else {
                //Odd
                x = (medianArray.size() / 2);
                results = Double.valueOf(medianArray.get(x));
                return results;
            }
        }
    }
}

