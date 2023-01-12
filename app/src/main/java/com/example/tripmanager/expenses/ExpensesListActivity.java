package com.example.tripmanager.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.tripmanager.database.DatabaseQueryClass;
import com.example.tripmanager.model.Expenses;
import com.example.tripmanager.R;
import com.example.tripmanager.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class ExpensesListActivity extends AppCompatActivity {

    private int id;


    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Expenses> expensesList = new ArrayList<>();

    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private ExpensesListAdapter expensesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);


        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);

        // get id from intent
        id = getIntent().getIntExtra(Constant.TRIP_REGISTRATION, -1);

        // get all Expenses by id
        expensesList.addAll(databaseQueryClass.getAllExpensesByRegNo(id));


        expensesListAdapter = new ExpensesListAdapter(this);
        expensesListAdapter.setData(expensesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(expensesListAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpensesListActivity.this, AddExpensesActivity.class);
                intent.putExtra(Constant.TRIP_REGISTRATION, id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        expensesList.clear();
        expensesList.addAll(databaseQueryClass.getAllExpensesByRegNo(id));
        expensesListAdapter.setData(expensesList);
        viewVisibility();
    }


    public void viewVisibility() {
        if (expensesList.isEmpty())
            tvEmpty.setVisibility(View.VISIBLE);
        else
            tvEmpty.setVisibility(View.GONE);
    }
}
