package com.dynamicminds.ffwsafetyalert;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import static com.android.volley.Request.Method.POST;

public class SafetyAlert extends Service {
    public int counter = 0;
    Context context = null;
    Speaker speaker;

    public SafetyAlert(Context context) {
        super();
        this.context = context;
    }

    public SafetyAlert() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        context = this;
        getData();
        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this, RestartBroadcasterReceiver.class);

        sendBroadcast(broadcastIntent);
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 01, 1000*60*60*24);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                getData();
            }
        };
    }

    private void getData() {
        JsonArrayRequest data = new JsonArrayRequest("http://www.effregapp.org/api/problem", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Problem> pL = new ArrayList<>();
                System.out.printf("Error Volley: mORE\n");
                try {
                    for (int x = 0; x < response.length(); x++) {
                        JSONObject obj = response.getJSONObject(x);
                        Problem p = new Problem(obj.getInt("problem_id"), obj.getString("type"),
                                obj.getDouble("longitude"), obj.getDouble("latitude"));
                        pL.add(p);
                        System.out.println(p.getLongitude());
                    }
                    ProblemDAO problemDAO = new ProblemDAO(context);
                    problemDAO.deleteAll();
                    problemDAO.setTableData(pL);
                } catch (Exception ex) {
                    System.out.printf("Error: %s\n", ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.printf("Error Volley: %s\n", error.getMessage());
            }
        });
        VolleyInstance.getInstance(context).addToRequestQueue(data);
    }

}