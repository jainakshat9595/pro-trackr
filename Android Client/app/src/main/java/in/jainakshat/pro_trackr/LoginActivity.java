package in.jainakshat.pro_trackr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Akshat Jain on 1/6/2016.
 */
public class LoginActivity extends AppCompatActivity {

    Firebase ref = new Firebase("https://pro-trackr.firebaseio.com/");

    EditText ed1,ed2;
    Button b1;
    TextView t1;

    Session session;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(LoginActivity.this);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in..");

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button);
        t1 = (TextView) findViewById(R.id.textView);

        t1.setText(session.getphone());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String phone  = ed1.getText().toString();
                final String password  = ed2.getText().toString();
                /*AsyncLogin asynclogin = new AsyncLogin();
                asynclogin.execute(phone, password);*/
                ref.child("user_hash").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        final String key_temp = snapshot.getValue().toString();
                        ref.child("Users").child(key_temp).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Map<String, Object> map = snapshot.getValue(Map.class);
                                progressDialog.dismiss();
                                if (password.equals(map.get("Item_password").toString())) {
                                    session.setphone(phone);
                                    session.setUserId(key_temp);
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                } else {
                                    t1.setText("Passwords do not match!");
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                progressDialog.dismiss();
                                t1.setText("Unexpected error!");
                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        progressDialog.dismiss();
                        t1.setText("User does not exist!");
                    }
                });
            }
        });

    }

    /*private class AsyncLogin extends AsyncTask<String, String, String> {
        private String resp;

        private String ph;
        @Override
        protected void onPreExecute() {

            //ProgressDialog.show(LoginActivity.this, "Please Wait", "Loggin in..");
        }
        @Override
        protected String doInBackground(final String... params) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                resp = "4";
            }
            System.out.println("akshatjain: "+resp);
            //return resp;
            // 1 : successfull login, 2 : password do not match, 3 : User does not exist, 4 : Unexpected error
        }
        @Override
        protected void onPostExecute(String result) {

            *//*switch (result.toString()) {
                case "1" :

                    break;
                case "2" :

                    break;
                case "3" :

                    break;
                case "4" :

                    break;
            }*//*
            t1.setText(result);
        }
    }*/

}
