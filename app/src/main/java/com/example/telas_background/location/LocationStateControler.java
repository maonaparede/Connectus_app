package com.example.telas_background.location;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;


import com.example.telas_background.dialog_toast.MakeToast;

public class LocationStateControler {

    private static Boolean isLocationServiceRunning(Context c){
        ActivityManager activityManager =
                (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null){
            for(ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public static void startLocationService(Context c){
        if(!isLocationServiceRunning(c)){
            Intent intent = new Intent(c.getApplicationContext() , LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            c.startService(intent);
            MakeToast.makeToast(c , "Location Service Started");
        }
    }


    public static void stopLocationService(Context c){
        if(isLocationServiceRunning(c)){
            Intent intent = new Intent(c.getApplicationContext() , LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            c.startService(intent);
            MakeToast.makeToast(c , "Location Service Stopped");
        }
    }
}
