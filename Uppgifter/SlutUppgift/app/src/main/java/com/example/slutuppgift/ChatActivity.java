package com.example.slutuppgift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sendBtn;
    private TextView sendText;
    private ListView listView;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendBtn = findViewById(R.id.sendBtn);
        sendText = findViewById(R.id.sendText);
        listView = findViewById(R.id.messageListView);
        sendBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);

        message = sendText.getText().toString();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
        listItems.add(sharedPreferences.getString("user","You")+": "+message);
        adapter.notifyDataSetChanged();
    }
}