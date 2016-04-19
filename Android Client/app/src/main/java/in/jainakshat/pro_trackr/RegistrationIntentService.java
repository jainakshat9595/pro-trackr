/*
package in.jainakshat.pro_trackr;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

*/
/**
 * Created by Akshat on 2/4/2016.
 *//*

public class RegistrationIntentService extends IntentService {

    //private MainApplication app = ((MainApplication)getApplication());
    private MainApplication app = new MainApplication();
    private static final String TAG = "PRO TrackR";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("jain: inside RegistrationIntentService");
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("175962093870", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + token);
            // Send to firebase
            app.setGCMRegistrationToken(token);
            app.setGCMRegistrationTokenStatus(true);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            app.setGCMRegistrationTokenStatus(false);
        }
        System.out.println("jain: Work done from RegistrationIntentService");
        Intent registrationComplete = new Intent("GCMBroadcastReciever").addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }
}
*/
