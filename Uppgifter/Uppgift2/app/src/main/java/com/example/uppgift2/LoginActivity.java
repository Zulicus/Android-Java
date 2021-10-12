package com.example.uppgift2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    boolean loggedIn;
    Button loginBtn;

    Switch switchOneOne;
    Switch switchOneTwo;
    Switch switchOneThree;
    Switch switchTwoOne;
    Switch switchTwoTwo;
    Switch switchTwoThree;
    Switch switchThreeOne;
    Switch switchThreeTwo;
    Switch switchThreeThree;
    boolean[] password = new boolean[]{false, false, false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        switchOneOne = findViewById(R.id.switchOneOne);
        switchOneTwo = findViewById(R.id.switchOneTwo);
        switchOneThree = findViewById(R.id.switchOneThree);
        switchTwoOne = findViewById(R.id.switchTwoOne);
        switchTwoTwo = findViewById(R.id.switchTwoTwo);
        switchTwoThree = findViewById(R.id.switchTwoThree);
        switchThreeOne = findViewById(R.id.switchThreeOne);
        switchThreeTwo = findViewById(R.id.switchThreeTwo);
        switchThreeThree = findViewById(R.id.switchThreeThree);
        switchOneOne.setOnClickListener(this::onOnOnClick);
        switchOneTwo.setOnClickListener(this::onOnTwClick);
        switchOneThree.setOnClickListener(this::onOnThClick);
        switchTwoOne.setOnClickListener(this::onTwOnClick);
        switchTwoTwo.setOnClickListener(this::onTwTwClick);
        switchTwoThree.setOnClickListener(this::onTwThClick);
        switchThreeOne.setOnClickListener(this::onThOnClick);
        switchThreeTwo.setOnClickListener(this::onThTwClick);
        switchThreeThree.setOnClickListener(this::onThThClick);

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("logIn", false);

        Log.d("testing", String.valueOf(sharedPreferences.getBoolean("logIn", false)));
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginBtn = findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(this::onClick);
        if (loggedIn) {
            startActivity(intent);
        }

    }

    private void onClick(View view) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        boolean checker = false;
        for (int i = 0; i < password.length; i++) {
            if (password[i] == sharedPreferences.getBoolean("passArray" + i, false)) {
                checker = true;
            } else {
                checker = false;
                break;
            }
        }
        if (checker) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("logIn", true);
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
        }
    }

    private void onThThClick(View view) {
        password[8] = !password[8];
    }

    private void onThTwClick(View view) {
        password[7] = !password[7];
    }

    private void onThOnClick(View view) {
        password[6] = !password[6];
    }

    private void onTwThClick(View view) {
        password[5] = !password[5];
    }

    private void onTwTwClick(View view) {
        password[4] = !password[4];
    }

    private void onTwOnClick(View view) {
        password[3] = !password[3];
    }

    private void onOnThClick(View view) {
        password[2] = !password[2];
    }

    private void onOnTwClick(View view) {
        password[1] = !password[1];
    }

    private void onOnOnClick(View view) {
        password[0] = !password[0];
    }
}
