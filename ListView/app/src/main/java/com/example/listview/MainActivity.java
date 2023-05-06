package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private MyViewModel model;

    private TextView tvCount;
    private FloatingActionButton butAdd;

    private ListView lvCount;
    private ArrayList<Integer> arrayList;
    private ArrayAdapter<Integer> arrayAdapter;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int numPos = data.getIntExtra("numPos", 0);
                String element = data.getStringExtra("element");
                arrayList.set(numPos, Integer.parseInt(element));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(MyViewModel.class);

        butAdd = findViewById(R.id.but_add);
        lvCount = findViewById(R.id.lv_count);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayList); // this = main_activity
        lvCount.setAdapter(arrayAdapter);

        model.getNumbers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                arrayList.add(integer);
                arrayAdapter.notifyDataSetChanged();
            }
        });


        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.increaseNumber();
            }
        });


        lvCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//                intent.putExtra("number", arrayList.get(position).toString());
                intent.putExtra("numPos", position);
                intent.putExtra("element", arrayList.get(position).toString());
                startActivityForResult(intent, 1);
            }
        });

    }
}