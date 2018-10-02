package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sw = new JSONObject(json);
            JSONObject name = sw.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsJSON = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = parseJSONarray(alsoKnownAsJSON);
            String placeOfOrigin = sw.getString("placeOfOrigin");
            String description = sw.getString("description");
            String image = sw.getString("image");
            List<String> ingredients = parseJSONarray(sw.getJSONArray("ingredients"));
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
