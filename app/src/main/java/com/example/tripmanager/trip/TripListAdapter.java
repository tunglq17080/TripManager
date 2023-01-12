package com.example.tripmanager.trip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanager.R;
import com.example.tripmanager.util.Constant;
import com.example.tripmanager.model.Trip;

import java.util.List;

;

public class TripListAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Trip> tripList;

    public TripListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Trip> tripList) {
        this.tripList = tripList;
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
        return new CustomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Trip trip = tripList.get(position);

        holder.tvPosition.setText(position + 1 + "");
        holder.tvName.setText(trip.getName());
        holder.tvDestination.setText(trip.getDestination());
        holder.tvDate.setText(trip.getDateOfTrip());
        holder.tvRequire.setText("Require Assessment: " + trip.isRequireRisks());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTripActivity.class);
                intent.putExtra(Constant.KEY_TRIP, trip);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
