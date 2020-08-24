package com.example.telas_background.location;

import android.util.Log;

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

    static public void setLocationServer(Double latitude, Double longitude){

        GeoLocation location = new GeoLocation(latitude , longitude);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(UserPrincipal.getId(), location);
                new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            System.err.println("There was an error saving the location to GeoFire: " + error);
                        } else {
                            System.out.println("Location saved on server successfully!");
                        }
                    }

                };
    }

     public static void removeLocationServer(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(UserPrincipal.getId());
    }

     public static void getMapUsers(Double latitude, Double longitude){
        GeoLocation location = new GeoLocation(latitude , longitude);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
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
