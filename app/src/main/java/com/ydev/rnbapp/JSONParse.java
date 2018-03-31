package com.ydev.rnbapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YRT on 30/03/2018.
 */

public class JSONParse {

    Context context;
    public JSONParse (Context context){
        this.context = context;
    }

    public ArrayList<HashMap<String, String>> getNameList(String s) throws JSONException {

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        JSONObject json = new JSONObject(s);
        JSONArray namesJSON = json.getJSONArray("results");

        for (int i=0; i<namesJSON.length(); i++){

            JSONObject object = namesJSON.getJSONObject(i);
            HashMap<String, String> map = new HashMap<>();
            map.put("name", object.getString("name"));
            if (object.has("rating")){
                map.put("rating", String.valueOf(object.getDouble("rating")));
            }else{
                map.put("rating", "0");
            }
            map.put("lat",object.getJSONObject("geometry").getJSONObject("location").getString("lat"));
            map.put("lng",object.getJSONObject("geometry").getJSONObject("location").getString("lng"));
            map.put("vicinity", object.getString("vicinity"));

            if (object.has("photos")){
                //map.put("place_id", object.getString("place_id"));
                JSONArray photosArray = object.getJSONArray("photos");
                JSONObject firstImage = photosArray.getJSONObject(0);

                String placeRef = firstImage.getString("photo_reference");
                String img = getImageStringURL(placeRef);
                map.put("photos", img);
            }else{
                map.put("photos", object.getString("icon"));
            }

            if (object.has("opening_hours"))
                map.put("open_now",object.getJSONObject("opening_hours").getString("open_now"));
            else
                map.put("open_now","false");
            arrayList.add(map);
        }

        return arrayList;
    }

    private String getImageStringURL(String placeRef) {

        String imgURL = "https://maps.googleapis.com/maps/api/place/photo?" +
                "maxwidth=100" +
                "&photoreference="+placeRef +
                "&key="+context.getResources().getString(R.string.google_API_key);

        return imgURL;

    }

}
