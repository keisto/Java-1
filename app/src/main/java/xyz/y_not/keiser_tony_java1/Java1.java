package xyz.y_not.keiser_tony_java1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import static java.lang.String.*;

public class Java1 extends Activity {

    ArrayList<String> dataArray = new ArrayList<String>();
    int sum;
    int dataBlock = 100;
    int output;
    String finalData = "";
    AlertDialog.Builder selectAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java1);
        //Log.d(JAVA_1, "onCreate");
        TextView list = (TextView) findViewById(R.id.header);
        list.setText(R.string.header);
        selectAlert = new AlertDialog.Builder(this);
        loadHandler();
    }

//    public int math(){
//        for (int i = 0; i < dataArray.size(); i++) {
//            sum += dataArray.get(i).length();
//            //Log.d("Length: ", String.valueOf(dataList.get(i).length()));
//            //Log.d("Sum: ", String.valueOf((sum)));
//        }
//        return sum;
//    }

   public int median() {
        ArrayList<Integer> intArray = new ArrayList<Integer>();
        if (dataArray.size()==0){
            return 0;
        } else {
            int x;
            //Create intArray from dataArray
            for (int i = 0; i < dataArray.size(); i++) {
                intArray.add(dataArray.get(i).length());
            }
            //Put array in order
            Collections.sort(intArray);
            for(int counter: intArray){
                System.out.println(counter);
            }

            if (intArray.size()%2==0) {
                //Even
                x = intArray.size()/2;
                //System.out.println("X = " + x);
                output = ((intArray.get(x)+intArray.get(x-1))/2);
                System.out.println("MedianE : " + output);
                return output;//intArray.get(x);
            } else {
                //Odd
                x = (intArray.size()/2);
                //System.out.println("X = " + String.valueOf(dataArray.get(intArray.get(x))));
                output = intArray.get(x);
                System.out.println("MedianO = " + output);
                return output;//intArray.get(x);
            }
        }
    }

    private void updateText() {
        //Update text to display correct values
        TextView dataCount = (TextView) findViewById(R.id.dataCounter);
        TextView dataLength = (TextView) findViewById(R.id.dataLength);

        if (dataArray.size() == 0){
            dataLength.setText("Median Length: 0");
            dataCount.setText("Total Data Entries: 0");
        } else {
            //Get Sum
            median();
            //math();
            dataLength.setText("Median Length: " + String.valueOf(output));
            dataCount.setText("Total Data Entries: " + String.valueOf(dataArray.size()));
        }
            sum = 0; //Reset Sum
    }

    private void updateList() {
        //Update ListView
        saveHandler();
        final ListView dataList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArray);
        dataList.setAdapter(adapter);

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                // Log.d("You selected: ", String.valueOf(parent.getItemAtPosition(position)));
                selectAlert.setTitle("Hello!");
                selectAlert.setMessage("You have selected: " + String.valueOf(parent.getItemAtPosition(position)));
                selectAlert.setCancelable(true);
                selectAlert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                selectAlert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Log.d("You selected: ", String.valueOf(position));
                        Toast.makeText(getBaseContext(), "Removed: " + String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_LONG).show();
                        dataArray.remove(position);
                        updateList();
                        updateText();
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = selectAlert.create();
                dialog.show();
            }
        });
    }

    public void loadHandler() {
        try {
            FileInputStream getData = openFileInput("text.txt");
            InputStreamReader inputData = new InputStreamReader(getData);
            char[] data = new char[dataBlock];
            int size;
            try {
                while ((size = inputData.read(data))>0){
                    String readData = String.copyValueOf(data,0,size);
                    finalData += readData;
                    data = new char[dataBlock];
                }
                //Toast.makeText(getBaseContext(), "Message : "+finalData, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            String[] breakup = finalData.split("\n");
            for (int i = 0; i <breakup.length; i++) {
                dataArray.add(breakup[i]);
            }
        if (dataArray.get(0).equals("")) {
            dataArray.clear();
        } else {
            updateText();
            updateList();
        }
        //Log.d("HELLO : ", String.valueOf(breakup.length));
    }

    public void saveHandler(){
        try {
            FileOutputStream fileData = openFileOutput("text.txt", MODE_WORLD_READABLE);
            OutputStreamWriter outputData = new OutputStreamWriter(fileData);
            try {
                for (int i = 0; i < dataArray.size(); i++) {
                    outputData.write(dataArray.get(i)+"\n");
                }
                outputData.flush();
                outputData.close();
                Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_java1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickHandler(View view) {
        EditText addData = (EditText) findViewById(R.id.editText);
        Editable addDataText = addData.getText();
        //Add Text to Array
        dataArray.add(String.valueOf(addDataText));
        Toast.makeText(getBaseContext(), "Added: " + addDataText, Toast.LENGTH_LONG).show();
        updateText();
        updateList();
        addData.setText(""); //Clear EditText

    }

    @Override
    protected void onPause() {
        super.onPause();
       // Log.d(JAVA_1, "onPause");
    }
}
