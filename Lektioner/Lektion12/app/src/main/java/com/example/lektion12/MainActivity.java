package com.example.lektion12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView valueText;
    private Button setBtn;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueText = findViewById(R.id.textView);
        setBtn = findViewById(R.id.button);
        setBtn.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("Value");
        //reference.setValue("HELLO DATABASE");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String getValue = String.valueOf(snapshot.getValue());
                Log.d("Tester:", "onDataChange: " + getValue);
                valueText.setText(getValue);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Tester:", "onCancelled: " + error);
            }
        });

    }

    @Override
    public void onClick(View view) {
        Log.d("Tester:", "onClick: clicked");

        DatabaseReference reference = database.getReference("Value");
        reference.setValue("Hej från knappen");
        reference = database.getReference("parent").child("first");
        reference.setValue("Uppgift 1");


    }
}