package com.example.lektion5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView textEmail, textPass, textNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textEmail = findViewById(R.id.textEmail);
        textPass = findViewById(R.id.textPass);
        textNumber = findViewById(R.id.textNumber);
        Intent intent = getIntent();
        Bundle editBundle = intent.getBundleExtra("editBundle");
        String editEmailValue = editBundle.getString("editEmail");
        String editPassValue = editBundle.getString("editPass");
        String editNumberValue = editBundle.getString("editNumber");
        textEmail.setText(editEmailValue);
        textPass.setText(editPassValue);
        textNumber.setText(editNumberValue);
    }

    public void Click(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}