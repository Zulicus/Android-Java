package com.example.lektion10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView cloudLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cloudLimit = findViewById(R.id.cloudLimit);
        Button cloudBtn = findViewById(R.id.cloudBtn);
        cloudBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("voll", "onClick");

        String getURL = "https://api.openweathermap.org/data/2.5/weather?q=malmo,se&APPID=099eff339f56d6a29a9823857b2f2671&mode=json";

        RequestQueue queue = Volley.newRequestQueue(this);
        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, getURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("voll", "onResponse: " + response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cloudLimit.setText("SOS");
                Log.e("voll", "onErrorResponse: " + error);

            }
        }
        );*/


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("voll", "onResponse: " + response);
                        try {
                            JSONArray weather = response.getJSONArray("weather");
                            Log.d("voll", "onResponse: " + weather);
                            for (int i = 0; i < weather.length(); i++) {
                                JSONObject weatherJSON =weather.getJSONObject(i);
                                Log.d("voll", "onResponse: "+weatherJSON.getString("description"));
                                cloudLimit.setText(weatherJSON.getString("description"));
                            }

                        } catch (JSONException e) {
                                Log.wtf("voll", "onResponse: ",e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cloudLimit.setText("SOS");
                Log.e("voll", "onErrorResponse: " + error);
            }
        });


        queue.add(jsonObjectRequest);


    }
}