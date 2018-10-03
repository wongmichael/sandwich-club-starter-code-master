package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sw = new JSONObject(json);
            JSONObject name = sw.getJSONObject(KEY_NAME);
            String mainName = name.getString(KEY_MAIN_NAME);
            JSONArray alsoKnownAsJSON = name.getJSONArray(KEY_ALSO_KNOW_AS);
            List<String> alsoKnownAs = parseJSONarray(alsoKnownAsJSON);
            String placeOfOrigin = sw.getString(KEY_PLACE_OF_ORIGIN);
            String description = sw.getString(KEY_DESCRIPTION);
            //String image = sw.getString(KEY_IMAGE);
            String image = sw.optString(KEY_IMAGE,"no image");
            List<String> ingredients = parseJSONarray(sw.getJSONArray(KEY_INGREDIENTS));
            return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> parseJSONarray(JSONArray jsonArray) throws JSONException {
        List<String> arr = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            arr.add(jsonArray.getString(i));
        }
        return arr;
    }
}
