package in.jainakshat.pro_trackr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Akshat Jain on 1/15/2016.
 */
public class DashboardActivityTab3 extends AppCompatActivity {

    Session session;
    TextView tv_username;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dashboard_tab3);

        tv_username = (TextView) findViewById(R.id.textView4);
        btn_logout = (Button) findViewById(R.id.button6);

        session = new Session(DashboardActivityTab3.this);
        String phone = session.getphone();

        tv_username.setText(phone);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.deletephone();
                session.deleteUserId();
                Intent intent = new Intent(DashboardActivityTab3.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    };

}
