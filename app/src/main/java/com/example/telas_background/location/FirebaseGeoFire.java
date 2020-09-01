package com.example.telas_background.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.telas_background.initialize.UserPrincipal;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseGeoFire {

    static public void setLocationServer(Context context) {
        String id = UserPrincipal.getId();
        Location location1 = getLocation(context);

        GeoLocation location = new GeoLocation(location1.getLatitude(), location1.getLongitude());
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(id, location);

        ref.child(id).onDisconnect().removeValue();

        new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error == null) {
                    System.out.println("Location saved on server successfully!");
                } else {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                }
            }

        };

    }

    public static void removeLocationServer() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(UserPrincipal.getId());
    }

    public static Location getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return lastKnownLocationGPS;
                } else {
                    Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    System.out.println("1::" + loc);
                    System.out.println("2::" + loc.getLatitude());
                    return loc;
                }
        } else {
            return null;
        }
    }

     public static void getMapUsers(Double latitude, Double longitude){
        GeoLocation location = new GeoLocation(latitude , longitude);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
        GeoFire geoFire = new GeoFire(ref);

        Double raio = 0.6;

        GeoQuery query  = geoFire.queryAtLocation(location , raio);

       query.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
           @Override
           public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                String a = dataSnapshot.getKey();
               Log.d("FirebaseLocation.class" , "onDataEntered" + a);
           }

           @Override
           public void onDataExited(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

           }

           @Override
           public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

           }

           @Override
           public void onGeoQueryReady() {

           }

           @Override
           public void onGeoQueryError(DatabaseError error) {

           }
       });
    }
}
