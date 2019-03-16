package com.dynamicminds.ffwsafetyalert;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static double LONGITUDE = 0;
    public static double LATITUDE = 0;
    public static TextView mTextMessage;
    private ProblemDAO problemDAO;
    Intent mServiceIntent;
    private SafetyAlert safetyAlert;
    Context ctx;
    private final int CHECK_CODE = 0x1;
    private final int LONG_DURATION = 5000;
    private final int SHORT_DURATION = 1200;
    public LocationManager locationManager;


    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private ArticleFragment articleFragment;
    private NotificationFragment notificationFragment;

    public JSONObject objectResults;
    private Speaker speaker;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(homeFragment);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(articleFragment);

                    return true;
                case R.id.navigation_notifications:
                    setFragment(notificationFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){

            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.main_frame);

        homeFragment = new HomeFragment();
        articleFragment = new ArticleFragment();
        notificationFragment = new NotificationFragment();
        setFragment(homeFragment);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 1);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationMonitor(this));
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationMonitor(this));

        List<Coords> coordsList = new ArrayList();
        coordsList.add(new Coords(-25.9793201,28.013278));
        coordsList.add(new Coords(-25.979822, 28.014659));
        coordsList.add(new Coords(-25.979407, 28.013908));
        coordsList.add(new Coords(-25.979658, 28.013554));
        coordsList.add(new Coords(-25.979754, 28.013618));
        coordsList.add(new Coords(-25.979417, 28.013897));
        coordsList.add(new Coords(-25.979388, 28.014176));
        coordsList.add(new Coords(-25.980000799915647, 28.013583499909046));

        for(Coords coord: coordsList) {
            Log.d("Coords Addeed: ", coord.Latitude + ", " + coord.Longitude);
            addProximityAlert(coord.Latitude, coord.Longitude, 1
            );
        }

        ctx = this;
        safetyAlert = new SafetyAlert(getCtx());

        mServiceIntent = new Intent(getCtx(), safetyAlert.getClass());
        if (!isMyServiceRunning(safetyAlert.getClass())) {
            startService(mServiceIntent);
        }

    }

    public Context getCtx() {
        return ctx;
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        super.onDestroy();

    }
    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        this.startActivityForResult(check, CHECK_CODE);
    }

    private void addProximityAlert(double latitude, double longitude, int ID) {

        Intent intent = new Intent("ProximityIntentReceiver");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 2);
            return;
        }
        IntentFilter filter = new IntentFilter("ProximityIntentReceiver");
        registerReceiver(new ProximityIntentReceiver(), filter);

        locationManager.addProximityAlert(latitude, longitude, 10, -1, pendingIntent);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
