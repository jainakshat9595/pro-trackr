package in.jainakshat.pro_trackr;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.firebase.client.Firebase;
/*import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;*/

import io.filepicker.Filepicker;

/**
 * Created by Akshat Jain on 1/8/2016.
 */
public class MainApplication extends Application {

    //private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Boolean GCMRegistrationTokenStatus = false;
    private String GCMRegistrationToken = "";
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Filepicker.setKey("Aim7cw2oQnKAxscqRW5jvz");
        Filepicker.setAppName("PRO TrackR");
    }

    /*public void setGCMRegistrationToken(String GCMRegistrationToken) {
        this.GCMRegistrationToken = GCMRegistrationToken;
    }

    public String getGCMRegistrationToken() {
        return GCMRegistrationToken;
    }

    public void deleteGCMRegistrationToken() {
        this.GCMRegistrationToken = "";
    }

    public void setGCMRegistrationTokenStatus(Boolean GCMRegistrationTokenStatus) {
        this.GCMRegistrationTokenStatus = GCMRegistrationTokenStatus;
    }

    public Boolean getGCMRegistrationTokenStatusStatus() {
        return this.GCMRegistrationTokenStatus;
    }

    public void deleteGCMRegistrationTokenStatus() {
        this.GCMRegistrationTokenStatus = false;
    }

    public void setmGoogleApiClient(GoogleApiClient x) {
        mGoogleApiClient = x;
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public String sendLocation(){
        LocationManager lm = (LocationManager) MainApplication.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch(Exception ex) {
            return "false";
        }
        if(!gps_enabled && !network_enabled) {
            return "false";
        }
        else {
            if(mLastLocation == null){
                return "LocationNull";
            }
            else {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();

                sendLocationToFirebase(mLastLocation);

                System.out.println("Latitude: "+latitude+" and Longitude: "+longitude);

                return "true";
            }
        }
    }

    public void sendLocationToFirebase(Location location) {
        // Send to firebase.
    }*/

}
