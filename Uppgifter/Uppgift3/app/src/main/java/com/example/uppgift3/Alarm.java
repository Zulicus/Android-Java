package com.example.uppgift3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Alarm extends BroadcastReceiver {
    APIHandler handler = new APIHandler();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "setAlarm: it's in");
        Bundle bundle = intent.getBundleExtra("city");
        String city = bundle.getString("city");
        handler.getJSON(context, city);
    }

}
