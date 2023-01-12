package com.example.tripmanager.expenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanager.R;
import com.example.tripmanager.model.Expenses;

import java.util.List;

public class ExpensesListAdapter extends RecyclerView.Adapter<ExpensesListAdapter.CustomViewHolder> {

    private Context context;
    private List<Expenses> expensesList;

    public ExpensesListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Expenses> expensesList) {
        this.expensesList = expensesList;
        notifyDataSetChanged();
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expenses, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Expenses expenses = expensesList.get(position);

        holder.tvType.setText(expenses.getType());
        holder.tvAmount.setText(String.valueOf(expenses.getAmount()));
        holder.tvTime.setText(String.valueOf(expenses.getTime()));

    }


    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvType;
        TextView tvAmount;
        TextView tvTime;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvType);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

}



