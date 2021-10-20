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
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    //Global variables
    TextView genderText;
    TextView boolText;
    TextView PassText;
    CheckBox bool1;
    CheckBox bool2;
    CheckBox bool3;
    RadioButton RBPassOnOn;
    RadioButton RBPassOnTw;
    RadioButton RBPassOnTh;
    RadioButton RBPassTwOn;
    RadioButton RBPassTwTw;
    RadioButton RBPassTwTh;
    RadioButton RBPassThOn;
    RadioButton RBPassThTw;
    RadioButton RBPassThTh;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //Initializing the global variables
        genderText = findViewById(R.id.genderTextResponse);
        boolText = findViewById(R.id.boolTextResponse);
        PassText = findViewById(R.id.passwordTextResponse);
        bool1 = findViewById(R.id.showBool1);
        bool2 = findViewById(R.id.showBool2);
        bool3 = findViewById(R.id.showBool3);
        RBPassOnOn = findViewById(R.id.radioButton1);
        RBPassOnTw = findViewById(R.id.radioButton2);
        RBPassOnTh = findViewById(R.id.radioButton3);
        RBPassTwOn = findViewById(R.id.radioButton4);
        RBPassTwTw = findViewById(R.id.radioButton5);
        RBPassTwTh = findViewById(R.id.radioButton6);
        RBPassThOn = findViewById(R.id.radioButton7);
        RBPassThTw = findViewById(R.id.radioButton8);
        RBPassThTh = findViewById(R.id.radioButton9);
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this::onClick);
    }

    //Sends the user to the settings view
    private void onClick(View view) {
        Intent infoIntent = new Intent(this, MainActivity.class);
        infoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(infoIntent);
    }

    @Override
    protected void onResume() {
        //Log in session checker
        super.onResume();
        long unixTime = System.currentTimeMillis() / 1000;
        int dateInt = (int) unixTime;
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getInt("logInTime", 0) + 600 < dateInt) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("logIn", false);
            editor.apply();
        }
        if (!sharedPreferences.getBoolean("logIn", false)) {
            Intent logIntent = new Intent(this, LoginActivity.class);
            logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logIntent);
        }
        //Imports all the values
        switch (sharedPreferences.getString("genderString", "")) {
            case "Male":
                genderText.setText(R.string.genderMaleGreeting);
                break;
            case "Female":
                genderText.setText(R.string.genderFemaleGreeting);
                break;
            case "Other":
                genderText.setText(R.string.genderOtherGreeting);
                break;
            default:
                break;
        }
        bool1.setChecked(sharedPreferences.getBoolean("bool1", false));
        bool2.setChecked(sharedPreferences.getBoolean("bool2", false));
        bool3.setChecked(sharedPreferences.getBoolean("bool3", false));
        RBPassOnOn.setChecked(sharedPreferences.getBoolean("passArray0", false));
        RBPassOnTw.setChecked(sharedPreferences.getBoolean("passArray1", false));
        RBPassOnTh.setChecked(sharedPreferences.getBoolean("passArray2", false));
        RBPassTwTw.setChecked(sharedPreferences.getBoolean("passArray4", false));
        RBPassTwTh.setChecked(sharedPreferences.getBoolean("passArray5", false));
        RBPassTwOn.setChecked(sharedPreferences.getBoolean("passArray3", false));
        RBPassThOn.setChecked(sharedPreferences.getBoolean("passArray6", false));
        RBPassThTw.setChecked(sharedPreferences.getBoolean("passArray7", false));
        RBPassThTh.setChecked(sharedPreferences.getBoolean("passArray8", false));
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