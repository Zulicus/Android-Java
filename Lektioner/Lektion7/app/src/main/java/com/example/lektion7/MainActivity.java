package com.example.lektion7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button);

        button.setOnClickListener(this::onClick);

        String dogName = sampleObject.INSTANCE.getDogName();
        Log.d("test","onCreate: "+dogName);
        sampleObject.INSTANCE.setDogName("Nero");//Does nothing
        Log.d("test","onCreate: "+sampleObject.INSTANCE.runKot());

        CoroSample.Companion.coro();
        Log.d("test","onCreate: someother stuff");

    }

    private void onClick(View view) {
        Log.d("test","onClick: Click");

        CoroSample.Companion.setWorld(textView);

    }
}