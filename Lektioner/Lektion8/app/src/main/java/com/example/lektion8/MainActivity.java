package com.example.lektion8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView setText;
    Button setBtn;
    EditText editSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setText = findViewById(R.id.name);
        setBtn = findViewById(R.id.button);
        editSet = findViewById(R.id.editText);

        setBtn.setOnClickListener(this::onClick);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        setText.setText(sharedPreferences.getString("sPText","Hello World"));

    }

    private void onClick(View view) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sPText",editSet.getText().toString());
        editor.apply();
        setText.setText(sharedPreferences.getString("sPText","Hello World"));
        editSet.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("tester","onSaveState: ");
        outState.putString("setText",setText.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("tester","onRestoreInstanceState: ");
        setText.setText(savedInstanceState.getString("setText"));
    }
}