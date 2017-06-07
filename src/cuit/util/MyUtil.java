package cuit.util;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public class MyUtil {
    public static JSONArray getJsonArrTagsByListTags(ArrayList<String> tags) {
        if (tags.size() <= 0){
            System.out.println("The hot tags is empty");
            return null;
        }
        JSONArray arrResult = new JSONArray();
        for (String obj:tags){
            arrResult.add(obj);
        }
        return arrResult;
    }
}
