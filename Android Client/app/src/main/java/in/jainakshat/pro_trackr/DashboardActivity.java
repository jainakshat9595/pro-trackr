package in.jainakshat.pro_trackr;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;


/**
 * Created by Akshat Jain on 1/8/2016.
 */
public class DashboardActivity extends ActivityGroup {

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new Session(DashboardActivity.this);
        String phone = session.getphone();
        if (phone.equals("")) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        tabHost.setup(this.getLocalActivityManager());

        TabHost.TabSpec Tab1Spec = tabHost.newTabSpec("Add Entry");
        Intent IntentTab1 = new Intent(this,DashboardActivityTab1.class);
        Tab1Spec.setIndicator("Add Entry");
        Tab1Spec.setContent(IntentTab1);

        TabHost.TabSpec Tab2Spec = tabHost.newTabSpec("Empty");
        Intent IntentTab2 = new Intent(this,DashboardActivityTab2.class);
        Tab2Spec.setIndicator("Empty");
        Tab2Spec.setContent(IntentTab2);

        TabHost.TabSpec Tab3Spec = tabHost.newTabSpec("Profile");
        Intent IntentTab3 = new Intent(this,DashboardActivityTab3.class);
        Tab3Spec.setIndicator("Profile");
        Tab3Spec.setContent(IntentTab3);

        tabHost.addTab(Tab1Spec);
        tabHost.addTab(Tab2Spec);
        tabHost.addTab(Tab3Spec);

    }

    public static class visitClass {

        public String date;
        public String doctor_name;
        public String image_url;
        public String time;

        public visitClass(String date, String doctor_name, String image_url, String time) {

            this.date = date;
            this.doctor_name = doctor_name;
            this.image_url = image_url;
            this.time = time;

        }

    }

}
