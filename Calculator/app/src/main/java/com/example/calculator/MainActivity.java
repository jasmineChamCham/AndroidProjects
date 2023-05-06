package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private float result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvResult = findViewById(R.id.tv_res);
        ArrayList<Button> butNumbers = new ArrayList<>();
        butNumbers.add(findViewById(R.id.but_0));
        butNumbers.add(findViewById(R.id.but_1));
        butNumbers.add(findViewById(R.id.but_2));
        butNumbers.add(findViewById(R.id.but_3));
        butNumbers.add(findViewById(R.id.but_4));
        butNumbers.add(findViewById(R.id.but_5));
        butNumbers.add(findViewById(R.id.but_6));
        butNumbers.add(findViewById(R.id.but_7));
        butNumbers.add(findViewById(R.id.but_9));

        EditText text = findViewById(R.id.et_equation);

        for (Button b : butNumbers){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button but = (Button) v;
                    String tempText = text.getText().toString();
                    if (tempText.equals("0") || tempText == null)
                        text.setText(but.getText().toString());
                    else {
                        text.setText(tempText + but.getText().toString());
                    }
                }
            });
        }

        Button butReverse = findViewById(R.id.but_reverse);
        butReverse.setOnClickListener(v -> {
            float temp = Float.parseFloat(text.getText().toString());
            result = (-1) * temp;
            tvResult.setText("" + result);
        });

        Button butAdd = findViewById(R.id.but_add);
        butAdd.setOnClickListener(v -> {
            text.setText(text.getText().toString() + " + ");
        });

        Button butSub = findViewById(R.id.but_sub);
        butSub.setOnClickListener(v -> {
            text.setText(text.getText().toString() + " - ");
        });

        Button butMul = findViewById(R.id.but_mul);
        butMul.setOnClickListener(v -> {
            text.setText(text.getText().toString() + " * ");
        });

        Button butDiv = findViewById(R.id.but_div);
        butDiv.setOnClickListener(v -> {
            text.setText(text.getText().toString() + " / ");
        });

        Button butResult = findViewById(R.id.but_result);
        butResult.setOnClickListener(v -> {
            String s = text.getText().toString();
            if (s.indexOf("+") != -1){
                String[] elements = s.split("\\+");
                result = Float.parseFloat(elements[0]) + Float.parseFloat(elements[1]);
            } else if (s.indexOf("-") != -1){
                String[] elements = s.split("\\-");
                result = Float.parseFloat(elements[0]) - Float.parseFloat(elements[1]);
            } else if (s.indexOf("*") != -1){
                String[] elements = s.split("\\*");
                result = Float.parseFloat(elements[0]) * Float.parseFloat(elements[1]);
            } else if (s.indexOf("/") != -1){
                String[] elements = s.split("\\/");
                result = Float.parseFloat(elements[0]) / Float.parseFloat(elements[1]);
            }
            tvResult.setText("" + result);
            text.setText("" + result);
        });

        Button butRestart = findViewById(R.id.but_restart);
        butRestart.setOnClickListener(v -> {
            text.setText("0");
            tvResult.setText("0");
            result=0;
        });

        Button butFloat = findViewById(R.id.but_float);
        butFloat.setOnClickListener(v -> {
            String s = text.getText().toString();
            String[] elements = null;
            if (s.indexOf("+") != -1){
                elements = s.split("\\+");
                result = Float.parseFloat(elements[0]) + Float.parseFloat(elements[1]);
            } else if (s.indexOf("-") != -1){
                elements = s.split("\\-");
                result = Float.parseFloat(elements[0]) - Float.parseFloat(elements[1]);
            } else if (s.indexOf("*") != -1){
                elements = s.split("\\*");
                result = Float.parseFloat(elements[0]) * Float.parseFloat(elements[1]);
            } else if (s.indexOf("/") != -1){
                elements = s.split("\\/");
                result = Float.parseFloat(elements[0]) / Float.parseFloat(elements[1]);
            }
            int temp = 0;
            if (elements != null) {
                temp = elements[elements.length - 1].indexOf(".");
            } else {
                temp = s.indexOf(".");
            }
            if (temp != -1) {
                tvResult.setText("NaN");
            } else {
                text.setText(s + ".");
            }
        });
    }



}