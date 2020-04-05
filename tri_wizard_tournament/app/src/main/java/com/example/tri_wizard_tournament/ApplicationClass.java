package com.example.tri_wizard_tournament;

import android.app.Application;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ApplicationClass extends Application {

    public static String CHARACTERS_URL;
    public static String CHARACTERS_CHARACTER_URL;
    public static String HOUSES_URL;
    public static String HOUSES_HOUSE_URL;
    public static String SPELLS_URL;
    private List<Object> getKeysAndValues = new ArrayList<>();

    public static List<Object> getValuesForGivenKey(String jsonArrayStr, final String key) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonArrayStr);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                final JSONArray finalJsonArray = jsonArray;
                return IntStream.range(0, jsonArray.length())
                        .mapToObj(new IntFunction<Object>() {
                            @Override
                            public Object apply(int index) {
                                try {
                                    return ((JSONObject) finalJsonArray.get(index)).optString(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        })
                        .collect(Collectors.toList());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CHARACTERS_URL
                = "https://www.potterapi.com/v1/characters?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
        CHARACTERS_CHARACTER_URL
                = "https://www.potterapi.com/v1/characters/{characterId}?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
        HOUSES_URL
                = "https://www.potterapi.com/v1/houses?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
        HOUSES_HOUSE_URL
                = "https://www.potterapi.com/v1/houses/{houseId}?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
        SPELLS_URL
                = "https://www.potterapi.com/v1/spells?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

        //getKeysAndValues = getValuesForGivenKey(null,null);
    }
}