package com.example.linearlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Float> arrayList;
    private ArrayAdapter<Float> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvResult = findViewById(R.id.lv_res);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lvResult.setAdapter(arrayAdapter);

        EditText a = findViewById(R.id.num_a);
        EditText b = findViewById(R.id.num_b);

        Button butAdd = findViewById(R.id.but_add);
        Button butSub = findViewById(R.id.but_sub);
        Button butMul = findViewById(R.id.but_mul);
        Button butDiv = findViewById(R.id.but_div);

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float numA = Float.parseFloat(a.getText().toString());
                float numB = Float.parseFloat(b.getText().toString());
                arrayList.add(numA+numB);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        butSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float numA = Float.parseFloat(a.getText().toString());
                float numB = Float.parseFloat(b.getText().toString());
                arrayList.add(numA-numB);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        butMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float numA = Float.parseFloat(a.getText().toString());
                float numB = Float.parseFloat(b.getText().toString());
                arrayList.add(numA*numB);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        butDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float numA = Float.parseFloat(a.getText().toString());
                float numB = Float.parseFloat(b.getText().toString());
                arrayList.add(numA/numB);
                arrayAdapter.notifyDataSetChanged();
            }
        });

    }
}