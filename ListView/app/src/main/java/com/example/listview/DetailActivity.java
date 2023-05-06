package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView tvDetail;
    private Button butOk;
    private int numPos;
    private String element;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvDetail = findViewById(R.id.tv_detail);
        butOk = findViewById(R.id.but_ok);
        Intent receivedIntent = getIntent();

        if (receivedIntent != null) {
            numPos = receivedIntent.getIntExtra("numPos", 0);
            element = receivedIntent.getStringExtra("element");
            tvDetail.setText(element);
        }

        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvDetail.getText().equals(element)) {
                    element = tvDetail.getText().toString();
                }
                Intent intentDetail = new Intent();
                intentDetail.putExtra("numPos", numPos);
                intentDetail.putExtra("element", element);
                setResult(RESULT_OK, intentDetail);
                finish();
            }
        });
    }

}