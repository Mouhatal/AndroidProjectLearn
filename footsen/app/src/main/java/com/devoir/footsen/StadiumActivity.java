package com.devoir.footsen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StadiumActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_stadium
                , container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng stadiumCFM = new LatLng(14.4293188,-16.9740513);
        mMap.addMarker(new MarkerOptions()
                .position(stadiumCFM)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Stade Caroline Faye Mbour")
                .snippet("Billets:2000,5000,20000"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stadiumCFM));

        LatLng stadiumLDT = new LatLng(14.7699859,-16.9470656);
        mMap.addMarker(new MarkerOptions()
                .position(stadiumLDT)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Stade Lat Dior Thies")
                .snippet("Billets:2000,5000,20000"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stadiumLDT));

        LatLng stadiumLSS = new LatLng(14.7467086,-17.4541027);
        mMap.addMarker(new MarkerOptions()
                .position(stadiumLSS)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Stade Léopol Sédar Senghor")
                .snippet("Billets:2000,5000,20000,500000"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stadiumLSS,12));

    }
}
