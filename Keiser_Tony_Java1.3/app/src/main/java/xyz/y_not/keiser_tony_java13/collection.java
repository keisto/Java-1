package xyz.y_not.keiser_tony_java13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class collection {
    static Map<String, String> clickers = new HashMap<String, String>();
    static ArrayList<String> texters = new ArrayList<String>();

    private String cName;
    private String cColor;
    private String cFood;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;

    }

    public String getcColor() {

        return cColor;
    }

    public void setcColor(String cColor) {
        this.cColor = cColor;
        clickers.put(cName, cColor);
    }

    public String getcFood() {
        return cFood;
    }

    public void setcFood(String cFood) {
        this.cFood = cFood;
        texters.add(cFood);
    }

}
