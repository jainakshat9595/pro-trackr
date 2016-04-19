package in.jainakshat.pro_trackr;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.widget.Toast;

/*
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
*/

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "PRO Trackr";

    Session session;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private MainApplication app;
    /*GoogleApiClient mGoogleApiClient;
    private GoogleApiClient.ConnectionCallbacks connectionCallbacks;
    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener;
    private LocationListener locationListener;
    private LocationRequest mLocationRequest;*/
    String phone;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Boolean ForwardFlag1=false, ForwardFlag2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new Session(SplashActivity.this);
        app = ((MainApplication)getApplication());

        /* To be deleted and actual code to be used when location and GCM will be used. */
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    phone = session.getphone();
                    if(phone.equals("")|| phone == null){
                        Intent mIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                        finish();
                    }
                    else {
                        Intent mIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();



        /*connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                doOnConnected();
            }

            @Override
            public void onConnectionSuspended(int i) {
                mGoogleApiClient.connect();
            }
        };*/

        /*connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener(){

            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Toast.makeText(getApplicationContext(), "Try launching the app again.", Toast.LENGTH_LONG).show();
                Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "+ connectionResult.getErrorCode());
            }
        };*/

        /*locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Send location to firebase.
                app.sendLocationToFirebase(location);
                ForwardFlag1 = true;
                checkForwardFlags();
            }
        };*/

        /*mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("jainJain");
                ForwardFlag2 = true;
                checkForwardFlags();
            }
        };*/

        /*IntentFilter filter = new IntentFilter("GCMBroadcastReciever");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mRegistrationBroadcastReceiver, filter);*/

        /*if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }*/

    }

    /*private void doOnConnected() {
        String sendLocationStatus = app.sendLocation();
        if(sendLocationStatus.equals("false")) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
            alertDialog.setTitle("GPS Disabled.");
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            alertDialog.show();
        }
        else if(sendLocationStatus.equals("LocationNull")) {
            Log.i(TAG, "Location Status is null. Requesting location updates.");
            mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10 * 1000) .setFastestInterval(1 * 1000);
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
            } catch(SecurityException e) { }

        }
        else if(sendLocationStatus.equals("true")) {
            ForwardFlag1 = true;
            checkForwardFlags();
        }
    }*/

    /*private void checkForwardFlags() {
        if(!ForwardFlag1 || !ForwardFlag2) {
            return;
        }
        else {
            forwardAfterDoOnConnected();
        }
    }*/

    /*private void forwardAfterDoOnConnected() {
        Boolean gcmRegistrationTokenStatus = app.getGCMRegistrationTokenStatusStatus();
        if (gcmRegistrationTokenStatus) {
            phone = session.getphone();
            if(phone.equals("")|| phone == null){
                Intent mIntent = new Intent(SplashActivity.this, LoginActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
            }
            else {
                Intent mIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Try launching the app again.(From GCM wala.)", Toast.LENGTH_LONG).show();
        }
    }*/

    /*private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }*/

    /*protected void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(connectionCallbacks).addOnConnectionFailedListener(connectionFailedListener).addApi(LocationServices.API).build();
        app.setmGoogleApiClient(mGoogleApiClient);
    }*/

}
