package com.example.uppgift3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewDesc;
    TextView textViewTemp;
    EditText cityText;
    Button enterBtn;
    FirebaseDatabase database;
    APIHandler handler = new APIHandler();
    String city = "skurup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        cityText = findViewById(R.id.cityText);
        textViewDesc = findViewById(R.id.textViewDesc);
        textViewTemp = findViewById(R.id.textViewTemp);
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();

        handler.getJSON(this, city);
        setAlarm();

        startService();
        DatabaseReference descReference = database.getReference("description");
        descReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String getDescription = String.valueOf(snapshot.getValue());
                Log.d("Tester:", "onDataChange: " + getDescription);
                textViewDesc.setText(getDescription);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Tester:", "onCancelled: " + error);
            }
        });
        DatabaseReference tempReference = database.getReference("temperature");
        tempReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String getTemperature = String.valueOf(snapshot.getValue());
                Log.d("Tester:", "onDataChange: " + getTemperature);
                textViewTemp.setText(getTemperature);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Tester:", "onCancelled: " + error);
            }
        });
    }

    public void startService() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("bool1", false)) {
            Intent intent = new Intent(this, WeatherService.class);
            ContextCompat.startForegroundService(this, intent);
        }
    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        intent.putExtra("cityBundle",bundle);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000, 10000, sender);
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


    @Override
    public void onClick(View view) {
        city = String.valueOf(cityText.getText());
        handler.getJSON(this, String.valueOf(cityText.getText()));
    }
}
