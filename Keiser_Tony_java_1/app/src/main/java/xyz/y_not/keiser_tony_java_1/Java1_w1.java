package xyz.y_not.keiser_tony_java_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    Double dataLength;

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
            getAlert.setTitle("Error!");
            getAlert.setCancelable(true);
            getAlert.setMessage("You must enter a value.");
            getAlert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = getAlert.create();
            dialog.show();
        // If ID is out of range
        } else if (Integer.parseInt(textSearch.getText().toString())>dataCollection.size()){
            getAlert.setTitle("Error!");
            getAlert.setCancelable(true);
            getAlert.setMessage("ID is out out of range.");
            getAlert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = getAlert.create();
            dialog.show();
        } else {
            // If dataCollection not empty... search dataCollection
            if (dataCollection.size() != 0) {

                getAlert.setTitle("Search Result:");
                getAlert.setCancelable(true);
                getAlert.setMessage("You found: " +
                        // Subtracting 1 so array count starts at 1 in lieu of 0)
                        dataCollection.get(Integer.parseInt(textSearch.getText().toString()) - 1));
                getAlert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = getAlert.create();
                dialog.show();
                // Error due to no data in dataCollection
            } else {
                getAlert.setTitle("Error!");
                getAlert.setCancelable(true);
                getAlert.setMessage("No data has been collected!");
                getAlert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
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
            getAlert.setTitle("Error!");
            getAlert.setCancelable(true);
            getAlert.setMessage("You must enter text to add.");
            getAlert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = getAlert.create();
            dialog.show();
        } else { // Add text to dataCollection
            dataCollection.add(textAdd.getText().toString());
            recentAdd.setText("Recently added: " + textAdd.getText().toString() +
                    ", ID: " + dataCollection.size() + ", Length: " +
                    dataCollection.get(dataCollection.size()-1).length());
            textAdd.setText(""); // Reset EditText field

            //Update dataCollection Stats
            averageLength();
            textLength.setText("Average Length: " + dataLength);
            textCount.setText("Entry Count: " + dataCollection.size());

            //Update Hint
            if (dataCollection.size()==1){
                updateAdd.setText("Enter an ID of 1");
            } else if (dataCollection.size()>1){
                updateAdd.setText("Enter an ID of 1 - " + dataCollection.size());
            } else {
                return; //Should never be called, but just in case
            }
        }
    }

    public double averageLength() {
        dataLength = 0.0; //Set to 0 to start to prevent adding previous lengths again
        if (dataCollection.size()==1){
            dataLength = Double.valueOf(dataCollection.get(0).length());
        }
        else if (dataCollection.size() > 1) {
            for (int i = 0; i < dataCollection.size(); i++) {
                dataLength += dataCollection.get(i).length();
            }
            dataLength = dataLength / dataCollection.size();
        } else {
            dataLength = 0.0;

        }
        return dataLength;
    }
}

