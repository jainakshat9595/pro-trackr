package in.jainakshat.pro_trackr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Akshat Jain on 1/8/2016.
 */
public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        prefs = context.getSharedPreferences("SessPref", context.MODE_PRIVATE);
    }

    public void setphone(String phone) {
        prefs.edit().putString("phone", phone).commit();
    }

    public String getphone() {
        String phone = prefs.getString("phone","");
        return phone;
    }

    public void deletephone() {
        prefs.edit().remove("phone").commit();
    }

    public void setUserId(String id) {
        prefs.edit().putString("user_id", id).commit();
    }

    public String getUserId() {
        String id = prefs.getString("user_id","");
        return id;
    }

    public void deleteUserId() {
        prefs.edit().remove("user_id").commit();
    }

}
