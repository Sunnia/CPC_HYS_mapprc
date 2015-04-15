package com.example.michael.mapprc;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    private List<Station> station=new ArrayList<Station>();
    private LocationManager locManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {
            setUpMapIfNeeded();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            setUpMapIfNeeded();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() throws Exception {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() throws Exception {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(23.547795,120.816900)).title("Marker"));
        Resources res = this.getResources();
        XmlResourceParser xrp = res.getXml(R.xml.gasstation);
        station = UseXmlStation.ReadStationXML((XmlPullParser) xrp);
        for (int i = 0; i < station.size(); i++) {
            float lat = station.get(i).getLatitude();
            float longt = station.get(i).getLongtitude();
            String tit = station.get(i).getname();
            //String dbgstr
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longt)).title(tit));
        }


        //Set to go User's location
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //Set satart location on map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.547795,120.816900), 7));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {//navagate to info activity
                Intent nextScreen=new Intent(MapsActivity.this,StationInfoActivity.class);
                nextScreen.putExtra("latitude", marker.getPosition().latitude);
                nextScreen.putExtra("longitude", marker.getPosition().longitude);
                nextScreen.putExtra("name", marker.getTitle());
                nextScreen.putExtra("address", getAddressByName(marker.getTitle()));
                startActivity(nextScreen);


            }

        });


        //animate to cue location
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Criteria criteria = new Criteria();                                           //Criteria curlocation feature
        //String provider = locManager.getBestProvider(criteria,true);                  //Criteria curlocation feature
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, (android.location.LocationListener) this);
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Location location = locManager.getLastKnownLocation(provider);                //Criteria curlocation feature
        if (location!=null) {
            Log.w("setUpMap",location.toString());
            LatLng currentlocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, 14),2000,new MapCancelableCallback());
        }else{
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, (android.location.LocationListener) this);
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location!=null) {
                Log.w("setUpMap",location.toString());
                LatLng currentlocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, 14),2000,new MapCancelableCallback());
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private String getAddressByName(String input){
        for (int i = 0; i < station.size(); i++) {
            if(station.get(i).getname().equals(input))
            return station.get(i).getAddress();
        }
        return null;
    }
}
