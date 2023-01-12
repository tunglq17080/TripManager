package com.example.tripmanager.trip;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tripmanager.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView tvPosition;
    TextView tvName;
    TextView tvDestination;
    TextView tvDate;
    TextView tvRequire;

    public CustomViewHolder(View itemView) {
        super(itemView);

        tvPosition = itemView.findViewById(R.id.tvPosition);
        tvName = itemView.findViewById(R.id.tvName);
        tvDestination = itemView.findViewById(R.id.tvDestination);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvRequire = itemView.findViewById(R.id.tvRequire);
    }
}
