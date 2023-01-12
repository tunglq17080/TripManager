package com.example.tripmanager.expenses;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.database.DatabaseQueryClass;
import com.example.tripmanager.model.Expenses;
import com.example.tripmanager.util.Constant;
import com.example.tripmanager.util.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddExpensesActivity extends AppCompatActivity {

    private EditText edtAmount;
    private EditText edtTime;
    private Spinner spinner;
    private Button btnAdd;
    private String type = "";
    private String amount = "";
    private String time = "";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        id = getIntent().getIntExtra(Constant.TRIP_REGISTRATION, -1);

        initView();
        initListener();
    }


    private void initView() {

        spinner = findViewById(R.id.spinner);
        edtTime = findViewById(R.id.edtTime);
        edtAmount = findViewById(R.id.edtAmount);
        btnAdd = findViewById(R.id.btnAdd);


        List<String> arrayString = new ArrayList<>();
        arrayString.add("Food");
        arrayString.add("Travel");
        arrayString.add("Transport");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayString);//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    private void initListener() {
        final Calendar myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                Date date = new Date(myCalendar.getTimeInMillis());
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(AddExpensesActivity.this);
                edtTime.setText(dateFormat.format(date));
                edtTime.setError(null);
            }
        };


        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddExpensesActivity.this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<EditText> list = new ArrayList<>();
                list.add(edtAmount);
                list.add(edtTime);

                if (!Utils.checkValidate(list)) {
                    return;
                }


                type = spinner.getSelectedItem().toString();
                amount = edtAmount.getText().toString();
                time = edtTime.getText().toString();

                Expenses expenses = new Expenses(-1, type, amount, time);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(AddExpensesActivity.this);


                int id = databaseQueryClass.insertExpenses(expenses, AddExpensesActivity.this.id);

                if (id > 0) {

                    Toast.makeText(AddExpensesActivity.this, "Add successfully", Toast.LENGTH_LONG).show();
                    expenses.setId(id);
                    finish();
                }
            }
        });
    }


}
