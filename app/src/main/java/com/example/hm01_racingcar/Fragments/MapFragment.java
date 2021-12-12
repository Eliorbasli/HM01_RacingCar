package com.example.hm01_racingcar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hm01_racingcar.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    FusedLocationProviderClient client;

    private GoogleMap recordMap;
    private MarkerOptions markerOptions;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize View
        View view = inflater.inflate(R.layout.fragment_map, container , false);

        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                recordMap = googleMap;


                LatLng israel = new LatLng(32.062061, 34.812743);

                googleMap.addMarker(new MarkerOptions().position(israel));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(israel));
            }
        });


        return view;
    }

    private void addPin(LatLng latlng) {
        recordMap.clear();
        markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        recordMap.addMarker(markerOptions);
    }

    public void RecordClicked(double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        recordMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        addPin(latLng);
    }

}