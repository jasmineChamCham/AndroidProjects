package com.example.burgermenu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<Order> orderList;

    public OrderAdapter(ArrayList<Order> orderList){
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("orderList.get(position)",orderList.get(position).toString());
        holder.etId.setText(""+orderList.get(position).getId());
        holder.etName.setText(orderList.get(position).getName());
        if (orderList.get(position).isCheese()) {
            holder.etCheese.setText("Asiago cheese");
        }else{
            holder.etCheese.setText("Creme Fraiche");
        }
        if (orderList.get(position).isProsciutto()){
            holder.etPros.setText("yes");
        } else {
            holder.etPros.setText("no");
        }
        holder.etPrice.setText("$"+orderList.get(position).getPrice());
        holder.etStatus.setText(orderList.get(position).getStatus());
        holder.etSpoon.setText(""+orderList.get(position).getSauceSpoon());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        public EditText etId;
        public EditText etName;
        public EditText etCheese;
        public EditText etPrice;
        public EditText etStatus;
        public EditText etPros;
        public EditText etSpoon;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etId = itemView.findViewById(R.id.et_id);
            etName = itemView.findViewById(R.id.et_name);
            etCheese = itemView.findViewById(R.id.et_cheese);
            etPrice = itemView.findViewById(R.id.et_price);
            etStatus = itemView.findViewById(R.id.et_status);
            etPros = itemView.findViewById(R.id.et_pros);
            etSpoon = itemView.findViewById(R.id.et_spoon);
        }
    }
}
