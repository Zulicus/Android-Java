package com.example.slutuppgift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sendBtn;
    private TextView sendText;
    private ListView listView;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String message;
    private FirebaseDatabase database;
    private SharedPreferences sharedPreferences;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendBtn = findViewById(R.id.sendBtn);
        sendText = findViewById(R.id.sendText);
        listView = findViewById(R.id.messageListView);
        sendBtn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        try {
            reference = database.getReference("chat")
                    .child(sharedPreferences.getString("user", ""))
                    .child(sharedPreferences.getString("user2", ""));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                displayMessages();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("fBase", "onCancelled: " + error);
            }
        });
    }

    private void displayMessages() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot messages : task.getResult().getChildren()) {
                        listItems.add(messages.getKey().split(";")[0] + " "
                                + messages.child("time").getValue() + ": "
                                + messages.child("message").getValue());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("TAG", "onComplete: failed", task.getException());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        message = sendText.getText().toString();
        DatabaseReference sendReferenceSender = database.getReference("chat")
                .child(sharedPreferences.getString("user",""))
                .child(sharedPreferences.getString("user2",""));
        DatabaseReference sendReferenceReceiver = database.getReference("chat")
                .child(sharedPreferences.getString("user2",""))
                .child(sharedPreferences.getString("user1",""));
        
    }
}