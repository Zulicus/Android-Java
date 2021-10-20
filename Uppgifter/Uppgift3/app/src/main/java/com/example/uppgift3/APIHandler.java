package com.example.uppgift3;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIHandler {
    private String description = "";
    private String temp = "";
    private static final String getURL = "https://api.openweathermap.org/data/2.5/weather?q=skurup,se&APPID=099eff339f56d6a29a9823857b2f2671&mode=json";

    public void getJSON(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weather = response.getJSONArray("weather");
                            for (int i = 0; i < weather.length(); i++) {
                                JSONObject weatherJSON = weather.getJSONObject(i);
                                description = weatherJSON.getString("description");
                                Log.d("Tester", "onResponse: " + description);
                            }
                            JSONObject temperatureJSON = response.getJSONObject("main");
                            temp = temperatureJSON.getString("temp");
                            Log.d("Tester", "onResponse: " + temp);
                        } catch (JSONException e) {
                            Log.wtf("voll", "onResponse: ", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("voll", "onErrorResponse: ", error);
            }
        });
        queue.add(jsonObjectRequest);
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }
}
