package com.example.slutuppgift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

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
import java.util.Locale;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sendBtn;
    private Button speakBtn;
    private TextView sendText;
    private ListView listView;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String message;
    private FirebaseDatabase database;
    private SharedPreferences sharedPreferences;
    private DatabaseReference reference;
    private TextToSpeech toSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendBtn = findViewById(R.id.sendBtn);
        speakBtn = findViewById(R.id.speechBtn);
        sendText = findViewById(R.id.sendText);
        listView = findViewById(R.id.messageListView);
        sendBtn.setOnClickListener(this);
        speakBtn.setOnClickListener(this);
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
        toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                try {
                    toSpeech.setLanguage(new Locale("en", "EN"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("speak", "onInit: " + TextToSpeech.ERROR);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toSpeech.speak(adapterView.getItemAtPosition(i).toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        toSpeech.stop();
        toSpeech.shutdown();
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
        switch (view.getId()) {
            case R.id.sendBtn:
                if (sendText.getText().toString().isEmpty()) return;
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
                break;
            case R.id.speechBtn:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            sendText.setText(results.get(0));
        }
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