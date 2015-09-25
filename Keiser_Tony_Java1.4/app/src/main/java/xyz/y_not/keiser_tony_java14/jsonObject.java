package xyz.y_not.keiser_tony_java14;

import android.util.Log;
import org.json.JSONObject;

public class jsonObject {
    private String jName;
    private String jId;
    private int jPopularity;


    public jsonObject(String name, String id, Integer popularity){
        jName = name;
        jId = id;
        jPopularity = popularity;
    }

    public  jsonObject(JSONObject jsonData){
        try {
            jName = jsonData.getString("Name");
            jId = jsonData.getString("Id");
            jPopularity = jsonData.getInt("Popularity");
        } catch (Exception e){
            Log.e("Error: ", "Failed to update.");
        }
    }

    public String getjId() {
        return jId;
    }

    public void setjId(String jId) {
        this.jId = jId;
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    public int getjPopularity() {
        return jPopularity;
    }

    public void setjPopularity(int jPopularity) {
        this.jPopularity = jPopularity;
    }
}