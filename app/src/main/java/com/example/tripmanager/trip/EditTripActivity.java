package com.example.tripmanager.trip;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tripmanager.R;
import com.example.tripmanager.expenses.AddExpensesActivity;
import com.example.tripmanager.util.Constant;
import com.example.tripmanager.util.Utils;
import com.example.tripmanager.database.DatabaseQueryClass;
import com.example.tripmanager.expenses.ExpensesListActivity;
import com.example.tripmanager.model.Trip;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTripActivity extends AppCompatActivity {
    private Trip trip;
    private EditText edtName;
    private EditText edtDestination;
    private EditText edtDate;
    private RadioButton rbYes;
    private RadioButton rbNo;

    private EditText edtDescription;

    private Button btnEdit;
    private Button btnSeeAll;

    private String name = "";
    private String destination = "";
    private String description = "";


    private DatabaseQueryClass databaseQueryClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseQueryClass = new DatabaseQueryClass(this);

        trip = (Trip) getIntent().getSerializableExtra(Constant.KEY_TRIP);

        initView();
        initListener();
    }


    private void initView() {

        edtName = findViewById(R.id.edtName);
        edtDestination = findViewById(R.id.edtDestination);
        edtDate = findViewById(R.id.edtDate);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        edtDescription = findViewById(R.id.edtDescription);
        btnEdit = findViewById(R.id.btnEdit);
        btnSeeAll = findViewById(R.id.btnSeeAll);


        edtName.setText(trip.getName());
        edtDestination.setText(trip.getDestination());
        edtDate.setText(trip.getDateOfTrip());
        edtDescription.setText(trip.getDescription());
        if (trip.isRequireRisks()) {
            rbYes.setChecked(true);
        } else {
            rbNo.setChecked(true);
        }
    }


    private void initListener() {

        // get date
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                Date date = new Date(myCalendar.getTimeInMillis());
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(EditTripActivity.this);
                edtDate.setText(dateFormat.format(date));
                edtDate.setError(null);
            }
        };


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTripActivity.this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<EditText> list = new ArrayList<>();
                list.add(edtName);
                list.add(edtDestination);
                list.add(edtDate);
                list.add(edtDescription);

                if (!Utils.checkValidate(list)) {
                    return;
                }

                name = edtName.getText().toString();
                destination = edtDestination.getText().toString();
                description = edtDescription.getText().toString();

                trip.setName(name);
                trip.setDestination(destination);
                trip.setDateOfTrip(Utils.getDateTime(myCalendar.getTimeInMillis(), EditTripActivity.this));
                trip.setRequireRisks(rbYes.isChecked());
                trip.setDescription(description);
                long id = databaseQueryClass.updateTripInfo(trip);

                if (id > 0) {

                    Toast.makeText(EditTripActivity.this, "Edit successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTripActivity.this, ExpensesListActivity.class);
                intent.putExtra(Constant.TRIP_REGISTRATION, trip.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete trip?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            deleteTrip();
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


    private void deleteTrip() {
        long count = databaseQueryClass.deleteTrip(trip.getId());
        if (count > 0) {
            Toast.makeText(this, "Trip deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Trip not deleted. Something wrong!", Toast.LENGTH_LONG).show();
        finish();
    }


}
