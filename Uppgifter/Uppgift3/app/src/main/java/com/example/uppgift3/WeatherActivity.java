package com.example.uppgift3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {
    TextView textViewDesc;
    TextView textViewTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        textViewDesc = findViewById(R.id.textViewDesc);
        textViewTemp = findViewById(R.id.textViewTemp);
        startService();

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Log.d("Tester:", "onReceive: ok");
                        String description = intent.getStringExtra(Alarm.EXTRA_DESCRIPTION);
                        String temperature = intent.getStringExtra(Alarm.EXTRA_TEMPERATURE);
                        if (!textViewDesc.getText().toString().equals(description)) {
                            textViewDesc.setText(description);
                        }
                        if (!textViewTemp.getText().toString().equals(temperature)) {
                            textViewTemp.setText(temperature);
                        }
                    }

                }, new IntentFilter(Alarm.ACTION_LOCATION_BROADCAST)
        );
    }

    public void startService() {
        Intent intent = new Intent(this, WeatherService.class);
        ContextCompat.startForegroundService(this, intent);
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOutOption:
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logIn", false);
                editor.apply();
                Intent logIntent = new Intent(this, LoginActivity.class);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logIntent);
                return true;
            case R.id.infoOption:
                Intent infoIntent = new Intent(this, InfoActivity.class);
                infoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(infoIntent);
                return true;
            case R.id.homeOption:
                Intent homeIntent = new Intent(this, WeatherActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
