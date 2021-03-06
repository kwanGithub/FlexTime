package com.uni.kevintruong.flextime.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.GeoLocationManager;
import com.uni.kevintruong.flextime.managers.SessionManager;
import com.uni.kevintruong.flextime.managers.TransitionManager;
import com.uni.kevintruong.flextime.models.GeoLocation;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by kwan8 on 2015-11-07.
 * Service for handling locationRequests for geofences
 */


public class GeofenceIntentService extends IntentService
{
    private TransitionManager _tm = TransitionManager.getInstance();

    public GeofenceIntentService()
    {
        super("GeofenceIntentService");
    }

    public void onCreate()
    {
        super.onCreate();
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (!geofencingEvent.hasError())
        {
            int transition = geofencingEvent.getGeofenceTransition();
            String notificationTitle;

            GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
            GeoLocationManager gm = new GeoLocationManager(getApplicationContext());
            SessionManager sm = new SessionManager(getApplicationContext());
            Calendar cl = new GregorianCalendar();

            switch (transition)
            {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    notificationTitle = "Geofence Entered";
                    cl.setTimeInMillis(geoFenceEvent.getTriggeringLocation().getTime());
                    GeoLocation geoLocation = gm.getGeoLoactionByName(geoFenceEvent.getTriggeringGeofences().get(0).getRequestId());
                    if (geoLocation != null)
                    {
                        //TransitionManager holds the session object till we exit the geofence
                        _tm.startSession(geoFenceEvent.getTriggeringGeofences().get(0).getRequestId(), cl.getTime(), geoLocation.getId());
                    }
                    break;
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    notificationTitle = "Geofence Dwell";
                    cl.setTimeInMillis(geoFenceEvent.getTriggeringLocation().getTime());
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    notificationTitle = "Geofence Exit";
                    cl.setTimeInMillis(geoFenceEvent.getTriggeringLocation().getTime());
                    if (_tm.getCurrentSession() != null)
                    {
                        sm.addSession(_tm.endSession(cl.getTime()));
                        _tm.setCurrentSession(null);
                    }
                    break;
                default:
                    notificationTitle = "Geofence Unknown";
            }

            sendNotification(this, getTriggeringGeofences(intent), notificationTitle);
        }
    }

    /**
     * Displays notification on the unit
     * @param context
     * @param notificationText  to display
     * @param notificationTitle to display
     */
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

    /**
     * Displays triggerd geoFences
     * @param intent
     * @return string of geoFence
     */
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
