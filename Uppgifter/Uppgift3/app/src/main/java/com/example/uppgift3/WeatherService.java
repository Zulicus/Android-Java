package com.example.uppgift3;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class WeatherService extends Service {


    public WeatherService() {
    }

    Alarm alarm = new Alarm();

    @Override
    public void onCreate() {
        super.onCreate();
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*Alarm Manager*/
        alarm.setAlarm(this);
        /*Alarm Manager!*/

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
