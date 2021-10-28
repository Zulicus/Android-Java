package com.example.slutuppgift;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginBtn;
    private Button createNewUserBtn;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.loginBtn);
        createNewUserBtn = findViewById(R.id.createNewUserBtn);
        loginBtn.setOnClickListener(this);
        createNewUserBtn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("login", false)) {
            //correct();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                TextView emailText = findViewById(R.id.editTextEmail);
                TextView passText = findViewById(R.id.editTextPassword);
                DatabaseReference reference = database.getReference("users");
                reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot users : task.getResult().getChildren()) {
                                if (users.child("email").getValue().equals(emailText.getText().toString().toLowerCase())) {
                                    if (users.child("password").getValue().equals(passText.getText().toString())) {
                                        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user", users.getKey());
                                        editor.putBoolean("login", true);
                                        editor.apply();
                                        correct();
                                        break;
                                    } else incorrect();
                                } else incorrect();
                            }
                        } else {
                            Log.e("TAG", "onComplete: failed", task.getException());
                        }
                    }
                });
                break;
            case R.id.createNewUserBtn:
                Log.d("TAG", "onClick: klick");
                break;
            default:
                break;
        }
    }

    private void correct() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void incorrect() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", false);
        editor.putString("user", "");
        editor.apply();
        Toast.makeText(this, "E-mail or Password is incorrect", Toast.LENGTH_SHORT).show();
    }
}