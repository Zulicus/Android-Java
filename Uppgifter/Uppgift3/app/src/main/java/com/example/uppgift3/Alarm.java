package com.example.uppgift3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class Alarm extends BroadcastReceiver {
    APIHandler handler = new APIHandler();
    public static final String EXTRA_DESCRIPTION = "extra_description";
    public static final String EXTRA_TEMPERATURE = "extra_temperature";
    public static final String ACTION_LOCATION_BROADCAST = WeatherService.class.getName() + "Weather";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Tester:", "onReceive: go");
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Tester:");
        wakeLock.acquire();
        // handler.getJSON(context);
        sendBroadcastMessage(context);

        wakeLock.release();
    }
    private void sendBroadcastMessage (Context context) {
        /*Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_DESCRIPTION, handler.getDescription());
        intent.putExtra(EXTRA_TEMPERATURE, handler.getTemp());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
        Log.d("Tester", "sendBroadcastMessage: works");
    }

    public void setAlarm(Context context) {
        Log.d("Tester", "onReceive: set");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10 * 1, pendingIntent); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
