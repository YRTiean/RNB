package com.ydev.rnbapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by YRT on 30/03/2018.
 */

public class AsyncTaskGetJSON extends AsyncTask<String, Void, String> {

    private String TAG = getClass().getName();
    private Context context;
    private RecyclerView recyclerView;

    public AsyncTaskGetJSON(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                    "location=" + strings[0]+
                    "&radius=5000" +
                    "&type=restaurant" +
                    "&key="+context.getResources().getString(R.string.google_API_key));

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }catch (Exception e){
            return null;
        }finally {
            urlConnection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        JSONParse parse = new JSONParse(context);
        Log.d(TAG, s);

        try {
            ArrayList<HashMap<String, String>> arrayList = parse.getNameList(s);

            RVRestaurant adapter = new RVRestaurant(arrayList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
