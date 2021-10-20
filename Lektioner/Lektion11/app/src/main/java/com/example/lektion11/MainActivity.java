package com.example.lektion11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText editValue;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editValue = findViewById(R.id.editValue);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
        dbHelper = new DBHelper(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        Log.d("Tester", "onClick: ");
        boolean status=dbHelper.addValue("First Value","Text");
        dbHelper.getTable();
    }
}