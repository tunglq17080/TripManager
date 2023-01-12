package com.example.tripmanager.trip;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanager.R;
import com.example.tripmanager.database.DatabaseQueryClass;
import com.example.tripmanager.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class SearchTripActivity extends AppCompatActivity {


    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);


    private List<Trip> tripList = new ArrayList<>();

    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private TripListAdapter tripListAdapter;
    private Button btnTrips;
    private EditText edtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnTrips = findViewById(R.id.btnTrips);
        edtSearch = findViewById(R.id.edtSearch);


        tripList.addAll(databaseQueryClass.getAllTrip());


        tripListAdapter = new TripListAdapter(this);
        tripListAdapter.setData(tripList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(tripListAdapter);
        viewVisibility();

        btnTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchTripActivity.this, TripListActivity.class));
                finish();
            }
        });



        edtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<Trip> searchData = databaseQueryClass.searchTrip(s.toString());
                if (searchData.size() <= 0) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
                tripListAdapter.setData(searchData);
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtSearch.setText("");
        tripList.clear();
        tripList.addAll(databaseQueryClass.getAllTrip());
        tripListAdapter.setData(tripList);
        viewVisibility();
    }

    public void viewVisibility() {
        if (tripList.isEmpty())
            tvEmpty.setVisibility(View.VISIBLE);
        else
            tvEmpty.setVisibility(View.GONE);
    }


}
