package com.example.uppgift3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    //Global variables
    Button applyBtn;
    Switch switchSetPassOnOn;
    Switch switchSetPassOnTw;
    Switch switchSetPassOnTh;
    Switch switchSetPassTwOn;
    Switch switchSetPassTwTw;
    Switch switchSetPassTwTh;
    Switch switchSetPassThOn;
    Switch switchSetPassThTw;
    Switch switchSetPassThTh;
    RadioGroup genderGroup;
    ToggleButton toggleButton1;
    ToggleButton toggleButton2;
    ToggleButton toggleButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the global variables
        setContentView(R.layout.activity_main);
        applyBtn = findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(this::onClick);
        switchSetPassOnOn = findViewById(R.id.switchSetPassOnOn);
        switchSetPassOnTw = findViewById(R.id.switchSetPassOnTw);
        switchSetPassOnTh = findViewById(R.id.switchSetPassOnTh);
        switchSetPassTwOn = findViewById(R.id.switchSetPassTwOn);
        switchSetPassTwTw = findViewById(R.id.switchSetPassTwTw);
        switchSetPassTwTh = findViewById(R.id.switchSetPassTwTh);
        switchSetPassThOn = findViewById(R.id.switchSetPassThOn);
        switchSetPassThTw = findViewById(R.id.switchSetPassThTw);
        switchSetPassThTh = findViewById(R.id.switchSetPassThTh);
        genderGroup = findViewById(R.id.genderGroup);
        toggleButton1 = findViewById(R.id.toggleBtn1);
        toggleButton2 = findViewById(R.id.toggleBtn2);
        toggleButton3 = findViewById(R.id.toggleBtn3);
    }

    @Override
    protected void onResume() {
        //Log in session checker
        super.onResume();
        long unixTime = System.currentTimeMillis() / 1000;
        int dateInt = (int) unixTime;
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getInt("logInTime", 0) + 60 < dateInt) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("logIn", false);
            editor.apply();
        }
        if (!sharedPreferences.getBoolean("logIn", false)) {
            Intent logIntent = new Intent(this, LoginActivity.class);
            logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logIntent);
        }
    }

    private void onClick(View view) {
        //Sets the values
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 9; i++) {
            editor.putBoolean("passArray" + i, getPassword(i));
        }
        if (((RadioButton) findViewById(genderGroup.getCheckedRadioButtonId())) == null) {
            editor.putString("genderString", "");
        } else {
            editor.putString("genderString", ((RadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString());
        }
        editor.putBoolean("bool1", toggleButton1.isChecked());
        editor.putBoolean("bool2", toggleButton2.isChecked());
        editor.putBoolean("bool3", toggleButton3.isChecked());
        editor.apply();
        Intent infoIntent = new Intent(this, InfoActivity.class);
        infoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(infoIntent);

    }

    private boolean getPassword(int i) {
        //Imports the selected password switches
        boolean[] password = {
                switchSetPassOnOn.isChecked(),
                switchSetPassOnTw.isChecked(),
                switchSetPassOnTh.isChecked(),
                switchSetPassTwOn.isChecked(),
                switchSetPassTwTw.isChecked(),
                switchSetPassTwTh.isChecked(),
                switchSetPassThOn.isChecked(),
                switchSetPassThTw.isChecked(),
                switchSetPassThTh.isChecked()
        };
        return password[i];
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
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}