package com.dynamicminds.ffwsafetyalert;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    MapView mMapView;
    ProblemDAO problemDAO;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container,false);
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        problemDAO = new ProblemDAO(this.getContext());
        mMapView.onResume();

        problemDAO = new ProblemDAO(getContext());

        ArrayList<Problem> problemList = new ArrayList<Problem>();
        problemList.add(new Problem(1, "hijakingspot", 343,433));
        problemList.add(new Problem(2, "hijackingspot", 2323, 454));
        problemList.add(new Problem(3, "hijakingspot", 2323,3443));

        //problemDAO.setTableData(problemList);

        ArrayList<Problem> test = problemDAO.getAll();
        String t = "Data: ";

        for (Problem p: test
        ) {
            t += p.getLatitude() + " - " + p.getLongitude() + ", ";
        }

        Toast.makeText(getContext(), t, Toast.LENGTH_LONG).show();

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double latitude = 17.385044;
        double longitude = 78.486671;
        MarkerOptions options = new MarkerOptions();

        options.position(new LatLng(latitude, longitude));
        options.title("Data");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(options);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(options.getPosition())
                .zoom(17).build();
        //Zoom in and animate the camera.
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        for(Problem p : problemDAO.getAll()){
            options.position(new LatLng(p.getLatitude(), p.getLongitude()));
            options.title(p.getType());
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(options);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
}
