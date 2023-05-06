package com.example.burgermenu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.burgermenu.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Order> orderList;
    private List<Order> allOrders;
    private OrderAdapter orderAdapter;
    private AppDatabase appDatabase;
    private OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appDatabase = AppDatabase.getInstance(this);
        orderDao = appDatabase.orderDao();
        getOrderLists();

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvOrders.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList);
        binding.rvOrders.setAdapter(orderAdapter);

        orderList.add(new Order(1,"Burger beef",false, true,1, 1.5f,"Dang che bien"));
        orderAdapter.notifyDataSetChanged();
        binding.butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void getOrderLists() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Order> orders;
                orders = orderDao.getAllOrders();
                if (orders != null) {
                    orderList = new ArrayList<>(orders);
                }
            }
        });
    }

    private void getContactLists() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                allOrders = orderDao.getAllOrders();
                if (allOrders != null){
                    orderList = new ArrayList<>(allOrders);
                }
                else orderList = new ArrayList<>();
            }
        });
    }

    private void addOrder(Order order){
        orderList.add(order);
        orderAdapter.notifyDataSetChanged();
        AsyncTask.execute(() -> {
            orderDao.insert(order);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


//                Intent intent = new Intent();
//                intent.putExtra("name", name);
//                intent.putExtra("cheese", cheese);
//                intent.putExtra("pros", pros);
//                intent.putExtra("spoon", tvSpoon.getText().toString());
//                intent.putExtra("price", ""+price);

                String name = data.getStringExtra("name");
                String cheese = data.getStringExtra("cheese");
                Boolean pros = data.getBooleanExtra("pros", false);
                String email = data.getStringExtra("email");
                String home = data.getStringExtra("home");
                String spoon = data.getStringExtra("spoon");
                Float price = data.getFloatExtra("price",0);

                addOrder(new Order(1,"Burger beef",false, true,1, 1.5f,"Dang che bien"));
            }
        }
    }
}