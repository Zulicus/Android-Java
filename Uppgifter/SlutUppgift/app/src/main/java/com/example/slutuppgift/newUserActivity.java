package com.example.slutuppgift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView usernameText;
    private TextView emailText;
    private TextView passwordText;
    private Button applyBtn;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        usernameText = findViewById(R.id.usernameTextBox);
        emailText = findViewById(R.id.emailTextBox);
        passwordText = findViewById(R.id.passwordTextBox);
        applyBtn = findViewById(R.id.createNewUserBtn);
        applyBtn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void onClick(View view) {
        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        username.replaceAll(" ", "")
                .replaceAll("\\.", "")
                .replaceAll("#", "")
                .replaceAll("\\$", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        DatabaseReference reference = database.getReference("users").child(username);
        refren

    }
}