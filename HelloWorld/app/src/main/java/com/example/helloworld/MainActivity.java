package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TextView tvCount;
    private FloatingActionButton butAdd;
    private FloatingActionButton butSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvCount = findViewById(R.id.tv_count);
        butAdd = findViewById(R.id.but_add);
        butSub = findViewById(R.id.but_sub);

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText("" + (++count));
            }
        });

        butSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText("" + (--count));
            }
        });
    }
}