package com.example.slutuppgift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


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

        reference = database.getReference("chat")
                .child(sharedPreferences.getString("user1", "brokenU1"))
                .child(sharedPreferences.getString("user2", "brokenU2"));
        displayMessages();
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
                    listItems.clear();
                    for (DataSnapshot messages : task.getResult().getChildren()) {
                        listItems.add(messages.getKey().split(";")[1] + " "
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
        Timestamp timestamp = new Timestamp(new Date().getTime());
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String time = timestamp.toString().split(" ")[1].split("\\.")[0];
        String key = timestamp.toString().split("\\.")[0] + ","
                + timestamp.toString().split("\\.")[1] + ";"
                + sharedPreferences.getString("user1", "");
        message = sendText.getText().toString();
        DatabaseReference sendReferenceSender = database.getReference("chat")
                .child(sharedPreferences.getString("user1", "brokenU1"))
                .child(sharedPreferences.getString("user2", "brokenU2"))
                .child(key);
        sendReferenceSender.child("time").setValue(time.split(":")[0] + ":" + time.split(":")[1]);
        sendReferenceSender.child("message").setValue(message);
        DatabaseReference sendReferenceReceiver = database.getReference("chat")
                .child(sharedPreferences.getString("user2", "brokenU2"))
                .child(sharedPreferences.getString("user1", "brokenU1"))
                .child(key);
        sendReferenceReceiver.child("time").setValue(time.split(":")[0] + ":" + time.split(":")[1]);
        sendReferenceReceiver.child("message").setValue(message);
        sendText.setText("");

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutOption:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login", false);
                editor.apply();
                Intent logIntent = new Intent(this, MainActivity.class);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logIntent);
                return true;
            case R.id.chooseChatOption:
                Intent infoIntent = new Intent(this, ChooseChatActivity.class);
                infoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(infoIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}