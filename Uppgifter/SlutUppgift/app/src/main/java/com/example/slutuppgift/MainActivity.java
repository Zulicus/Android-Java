package com.example.slutuppgift;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Task<DataSnapshot> snapshotTask;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = findViewById(R.id.loginBtn);
        Button createNewUserBtn = findViewById(R.id.newUserBtn);
        loginBtn.setOnClickListener(this);
        createNewUserBtn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        getUsers(reference);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getUsers(reference);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: " + error);
            }
        });
    }

    private void getUsers(DatabaseReference reference) {
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    snapshotTask = task;
                } else {
                    Log.e("TAG", "onComplete: failed", task.getException());
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("login", false)) {
            Intent intent = new Intent(this, ChooseChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                TextView emailText = findViewById(R.id.editTextEmail);
                TextView passText = findViewById(R.id.editTextPassword);
                for (DataSnapshot users : snapshotTask.getResult().getChildren()) {
                    if (users.child("email").getValue().equals(emailText.getText().toString().toLowerCase())) {
                        if (users.child("password").getValue().equals(passText.getText().toString())) {
                            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user1", users.getKey());
                            editor.putBoolean("login", true);
                            editor.apply();
                            Intent intent = new Intent(this, ChooseChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            break;
                        } else incorrect();
                    } else incorrect();
                }
                break;
            case R.id.newUserBtn:
                Intent intent = new Intent(this, NewUserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void incorrect() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", false);
        editor.putString("user", "");
        editor.apply();
        Toast.makeText(this, "E-mail or Password is incorrect", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.goOffline();
    }
}