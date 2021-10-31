package com.example.slutuppgift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ChooseChatActivity extends AppCompatActivity implements View.OnClickListener {
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
                        listItems.add(users.getKey());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("TAG", "onComplete: failed", task.getException());
                }
            }
        });
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(View view) {
        Log.d("TAG", "onClick: "+view);
    }
}