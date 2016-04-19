package in.jainakshat.pro_trackr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akshat Jain on 1/15/2016.
 */
public class DashboardActivityTab2 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private gridAdapter mGridAdapter;
    private ArrayList<Map<String, String>> mVisitsArrayList = new ArrayList<>();
    private Session mSession;

    Firebase ref = new Firebase("https://pro-trackr.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dashboard_tab2);

        System.out.println("Activity created.");

        mSession = new Session(DashboardActivityTab2.this);

        getAllVisits();

        mRecyclerView = (RecyclerView) findViewById(R.id.grid_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mGridAdapter = new gridAdapter(this, mVisitsArrayList);
        mRecyclerView.setAdapter(mGridAdapter);

        mGridAdapter.notifyDataSetChanged();

    };

    void getAllVisits() {

        mVisitsArrayList.clear();
        ref.child("user_hash").child(mSession.getphone()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final String key_temp = snapshot.getValue().toString();
                ref.child("Users").child(key_temp).child("allVisits").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot childSnapShot: snapshot.getChildren()) {
                            ref.child("visits").child(childSnapShot.getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        DashboardActivity.visitClass visitclass = dataSnapshot.getValue(DashboardActivity.visitClass.class );
                                        //JSONObject jsonObject = new JSONObject(dataSnapshot.getValue().toString());
                                        Map<String, String> map = new HashMap<>();
                                        map.put("date", visitclass.date);
                                        map.put("doctor_name", visitclass.doctor_name);
                                        map.put("image_url", visitclass.image_url);
                                        map.put("time", visitclass.time);
                                        mVisitsArrayList.add(map);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Toast.makeText(DashboardActivityTab2.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(DashboardActivityTab2.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(DashboardActivityTab2.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("Size: "+mVisitsArrayList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllVisits();
    }
}
