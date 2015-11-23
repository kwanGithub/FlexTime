package com.uni.kevintruong.flextime.managers;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.models.GeoLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by kwan8 on 2015-11-07.
 */


public class GeofenceIntentService extends IntentService
{

    private final String TAG = this.getClass().getCanonicalName();
    private TransitionManager tm = TransitionManager.getInstance();

    public GeofenceIntentService()
    {
        super("GeofenceIntentService");
        Log.v(TAG, "Constructor.");
    }

    public void onCreate()
    {
        super.onCreate();
        Log.v(TAG, "onCreate");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Log.v(TAG, "onHandleIntent");
        if (!geofencingEvent.hasError())
        {
            int transition = geofencingEvent.getGeofenceTransition();
            String notificationTitle;

            GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
            DatabaseManager db = DatabaseManager.getInstance(getApplicationContext());
            Calendar cl = new GregorianCalendar();


            switch (transition)
            {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    notificationTitle = "Geofence Entered";
                    cl.setTimeInMillis(geoFenceEvent.getTriggeringLocation().getTime());
                    GeoLocation geoLocation = db.getGeoLoactionByName(geoFenceEvent.getTriggeringGeofences().get(0).getRequestId());
                    tm.startSession(geoFenceEvent.getTriggeringGeofences().get(0).getRequestId(), cl.getTime(), geoLocation.getId());
                    Log.v(TAG, "Geofence Entered " + geoFenceEvent.getTriggeringGeofences().get(0).getRequestId() + " " + cl.getTime());
                    break;
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    notificationTitle = "Geofence Dwell";
                    Calendar cl2 = new GregorianCalendar();
                    cl2.setTimeInMillis(geoFenceEvent.getTriggeringLocation().getTime());
                    Log.v(TAG, "Dwelling in Geofence " + geoFenceEvent.getTriggeringGeofences().get(0).getRequestId() + " " + cl2.getTime());
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    notificationTitle = "Geofence Exit";
                    cl.setTimeInMillis(geoFenceEvent.getTriggeringLocation().getTime());
                    db.addSession(tm.endSession(cl.getTime()));
                    Log.v(TAG, "Geofence Exited "+ db.databaseSessionsToString() + geoFenceEvent.getTriggeringGeofences().get(0).getRequestId() + " " + cl.getTime());
                    break;
                default:
                    notificationTitle = "Geofence Unknown";
            }

            sendNotification(this, getTriggeringGeofences(intent), notificationTitle);
        }
    }

    private void sendNotification(Context context, String notificationText,
                                  String notificationTitle)
    {
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_cast_on_0_light)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setDefaults(Notification.DEFAULT_ALL).setAutoCancel(false);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        wakeLock.release();
    }

    private String getTriggeringGeofences(Intent intent)
    {
        GeofencingEvent geofenceEvent = GeofencingEvent.fromIntent(intent);
        List<Geofence> geofences = geofenceEvent
                .getTriggeringGeofences();

        String[] geofenceIds = new String[geofences.size()];

        for (int i = 0; i < geofences.size(); i++)
        {
            geofenceIds[i] = geofences.get(i).getRequestId();
        }

        return TextUtils.join(", ", geofenceIds);
    }
}
