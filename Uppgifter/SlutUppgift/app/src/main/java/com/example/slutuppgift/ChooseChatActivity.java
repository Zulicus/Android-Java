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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseChatActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase database;
    private SharedPreferences sharedPreferences;
    private DatabaseReference reference;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chat);
        listView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance();
        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        reference = database.getReference("users");
        displayUsers();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                displayUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("fBase", "onCancelled: " + error);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void displayUsers() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    listItems.clear();
                    for (DataSnapshot users : task.getResult().getChildren()) {
                        if (!users.getKey().equals(sharedPreferences.getString("user1", ""))) {
                            listItems.add(users.getKey());
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.e("TAG", "onComplete: failed", task.getException());
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startChat(adapterView.getItemAtPosition(i).toString());
            }
        });
    }

    private void startChat(String user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user2", user);
        editor.apply();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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