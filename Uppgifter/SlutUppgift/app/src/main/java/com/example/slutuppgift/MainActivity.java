package com.example.slutuppgift;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;


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
                                if (users.child("email").getValue().equals(emailText.getText().toString())) {
                                    if (users.child("password").getValue().equals(passText.getText().toString())) {
                                        Log.d("TAG", "onComplete: Loggin"+ users.getKey());
                                    }
                                }
                            }
                            Log.d("TAG", "onComplete: success" + task.getResult().getChildren());

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
}