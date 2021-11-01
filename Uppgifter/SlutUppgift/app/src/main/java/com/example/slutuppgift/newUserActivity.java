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

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView usernameText;
    private TextView emailText;
    private TextView passwordText;
    private FirebaseDatabase database;
    private Task<DataSnapshot> snapshotTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        usernameText = findViewById(R.id.setUsernameText);
        emailText = findViewById(R.id.setEmailText);
        passwordText = findViewById(R.id.setPasswordText);
        Button applyBtn = findViewById(R.id.createNewUserBtn);
        applyBtn.setOnClickListener(this);
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
    public void onClick(View view) {
        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        username = username.replaceAll(" ", "")
                .replaceAll("\\.", "")
                .replaceAll("#", "")
                .replaceAll("\\$", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Pleas fill out the form", Toast.LENGTH_SHORT).show();
            return;
        }
        for (DataSnapshot users : snapshotTask.getResult().getChildren()) {
            if (users.child("email").getValue().equals(email.replaceAll(" ", ""))) {
                Toast.makeText(this, "That E-mail is already in use", Toast.LENGTH_SHORT).show();
                return;
            }
            if (users.getKey().equals(username)) {
                Toast.makeText(this, "That Username is already taken", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        DatabaseReference reference = database.getReference("users").child(username);
        reference.child("email").setValue(email.replaceAll(" ", ""));
        reference.child("password").setValue(password);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user1", username);
        editor.apply();
        Intent intent = new Intent(this, ChooseChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.goOffline();
    }
}