package com.example.tripmanager.trip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.tripmanager.database.DatabaseQueryClass;
import com.example.tripmanager.model.Trip;
import com.example.tripmanager.R;

import java.util.ArrayList;
import java.util.List;

public class TripListActivity extends AppCompatActivity {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Trip> tripList = new ArrayList<>();

    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private TripListAdapter tripListAdapter;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnSearch = findViewById(R.id.btnSearch);

        tripList.addAll(databaseQueryClass.getAllTrip());


        tripListAdapter = new TripListAdapter(this);
        tripListAdapter.setData(tripList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(tripListAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TripListActivity.this, CreateTripActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TripListActivity.this, SearchTripActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // update list
        tripList.clear();
        tripList.addAll(databaseQueryClass.getAllTrip());
        tripListAdapter.setData(tripList);
        viewVisibility();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all trips?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllTrips();
                            if (isAllDeleted) {
                                tripList.clear();
                                tripListAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if (tripList.isEmpty())
            tvEmpty.setVisibility(View.VISIBLE);
        else
            tvEmpty.setVisibility(View.GONE);
    }


}
