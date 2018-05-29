package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");
            JSONArray alsoKnownAsJsonArray = nameObject.getJSONArray("alsoKnownAs");
            if (alsoKnownAsJsonArray != null) {
                for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsJsonArray.getString(i));
                }
            }
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");

            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
            if (ingredientsJsonArray != null) {
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredientsList.add(ingredientsJsonArray.getString(i));
                }
            }
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}