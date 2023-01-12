package com.example.tripmanager.trip;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.database.DatabaseQueryClass;
import com.example.tripmanager.model.Trip;
import com.example.tripmanager.util.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateTripActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtDestination;
    private EditText edtDate;
    private RadioButton rbYes;
    private EditText edtDescription;
    private String name = "";
    private String destination = "";
    private String description = "";
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        initView();
        initListener();
    }


    private void initView() {

        edtName = findViewById(R.id.edtName);
        edtDestination = findViewById(R.id.edtDestination);
        edtDate = findViewById(R.id.edtDate);
        rbYes = findViewById(R.id.rbYes);
        edtDescription = findViewById(R.id.edtDescription);
        btnAdd = findViewById(R.id.btnAdd);

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
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(CreateTripActivity.this);
                edtDate.setText(dateFormat.format(date));
                edtDate.setError(null);
            }
        };


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateTripActivity.this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
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

                Trip trip = new Trip(-1, name, destination, Utils.getDateTime(myCalendar.getTimeInMillis(), CreateTripActivity.this), rbYes.isChecked(), description);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(CreateTripActivity.this);


                int id = databaseQueryClass.insertTrip(trip);

                if (id > 0) {

                    Toast.makeText(CreateTripActivity.this, "Add successfully", Toast.LENGTH_LONG).show();
                    trip.setId(id);
                    finish();
                }
            }
        });
    }


}
