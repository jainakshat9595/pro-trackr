package in.jainakshat.pro_trackr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;




public class gridAdapter extends RecyclerView.Adapter<gridAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<Map<String, String>> mVisitsArrayList;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView_name;
        public TextView textView_time;
        public LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.grid_layout_item_image);
            textView_name = (TextView) v.findViewById(R.id.grid_layout_item_dr_name);
            textView_time = (TextView) v.findViewById(R.id.grid_layout_item_time);
            linearLayout = (LinearLayout) v.findViewById(R.id.grid_layout_item_root);
        }
    }

    public gridAdapter(Context context, ArrayList<Map<String, String>> data) {
        mContext = context;
        mVisitsArrayList = data;
    }

    @Override
    public gridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(mContext).load(mVisitsArrayList.get(position).get("image_url")).fit().into(holder.imageView);
        holder.textView_name.setText(mVisitsArrayList.get(position).get("doctor_name"));
        holder.textView_time.setText(mVisitsArrayList.get(position).get("time"));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Grid Image Clicked: ",""+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVisitsArrayList.size();
    }
}
