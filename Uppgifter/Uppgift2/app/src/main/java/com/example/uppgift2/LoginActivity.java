package com.example.uppgift2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

//Main Start Activity

public class LoginActivity extends AppCompatActivity {
    boolean loggedIn = false;
    Button loginBtn;
    SharedPreferences sharedPreferences;
    Switch switchOneOne;
    Switch switchOneTwo;
    Switch switchOneThree;
    Switch switchTwoOne;
    Switch switchTwoTwo;
    Switch switchTwoThree;
    Switch switchThreeOne;
    Switch switchThreeTwo;
    Switch switchThreeThree;
    //Default Password
    boolean[] password = new boolean[]{false, false, false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initiate global variables
        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        switchOneOne = findViewById(R.id.switchOneOne);
        switchOneTwo = findViewById(R.id.switchOneTwo);
        switchOneThree = findViewById(R.id.switchOneThree);
        switchTwoOne = findViewById(R.id.switchTwoOne);
        switchTwoTwo = findViewById(R.id.switchTwoTwo);
        switchTwoThree = findViewById(R.id.switchTwoThree);
        switchThreeOne = findViewById(R.id.switchThreeOne);
        switchThreeTwo = findViewById(R.id.switchThreeTwo);
        switchThreeThree = findViewById(R.id.switchThreeThree);
        switchOneOne.setOnClickListener(this::onClick);
        switchOneTwo.setOnClickListener(this::onClick);
        switchOneThree.setOnClickListener(this::onClick);
        switchTwoOne.setOnClickListener(this::onClick);
        switchTwoTwo.setOnClickListener(this::onClick);
        switchTwoThree.setOnClickListener(this::onClick);
        switchThreeOne.setOnClickListener(this::onClick);
        switchThreeTwo.setOnClickListener(this::onClick);
        switchThreeThree.setOnClickListener(this::onClick);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Checks for already logged in
        loggedIn = sharedPreferences.getBoolean("logIn", false);
        Intent intent = new Intent(this, InfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginBtn = findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(this::onClick);
        //If already logged in go to InfoActivity
        if (loggedIn) {
            startActivity(intent);
        }
    }

    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.LoginBtn:
                boolean checker = false;
                //Compares the set password to the inputted one
                for (int i = 0; i < password.length; i++) {
                    if (password[i] == sharedPreferences.getBoolean("passArray" + i, false)) {
                        checker = true;
                    } else {
                        checker = false;
                        break;
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checker) {
                    long unixTime = System.currentTimeMillis() / 1000;
                    int dateInt = (int) unixTime;
                    editor.putBoolean("logIn", true);
                    editor.putInt("logInTime", dateInt);
                    editor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    editor.putBoolean("logIn", false);
                    editor.apply();
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
                break;
            //Keeps track of the buttons for password input
            case R.id.switchOneOne:
                password[0] = !password[0];
                break;
            case R.id.switchOneTwo:
                password[1] = !password[1];
                break;
            case R.id.switchOneThree:
                password[2] = !password[2];
                break;
            case R.id.switchTwoOne:
                password[3] = !password[3];
                break;
            case R.id.switchTwoTwo:
                password[4] = !password[4];
                break;
            case R.id.switchTwoThree:
                password[5] = !password[5];
                break;
            case R.id.switchThreeOne:
                password[6] = !password[6];
                break;
            case R.id.switchThreeTwo:
                password[7] = !password[7];
                break;
            case R.id.switchThreeThree:
                password[8] = !password[8];
                break;
        }
    }
}
