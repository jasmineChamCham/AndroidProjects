package com.example.burgermenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewOrderActivity extends AppCompatActivity {

    public RadioButton radBeef;
    public RadioButton radLamb;
    public RadioButton radOstrich;
    public CheckBox cbPros;
    public RadioButton radAsiago;
    public RadioButton radCreme;
    public TextView tvSpoon;
    public Button butOrder;
    String cheese, name;
    float price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        radBeef = findViewById(R.id.rad_beef);
        radLamb = findViewById(R.id.rad_lamb);
        radOstrich = findViewById(R.id.rad_ostrich);
        cbPros = findViewById(R.id.cb_pros);
        radAsiago = findViewById(R.id.rad_asiago);
        radCreme = findViewById(R.id.rad_creme);
        tvSpoon = findViewById(R.id.tv_spoon);
        butOrder = findViewById(R.id.but_order);


        if (radBeef.isChecked()) {
            name = "Beef Patty";
            price += 4;
        }
        if (radLamb.isChecked()){
            name = "Lamb Patty";
            price += 5;
        }
        if (radOstrich.isChecked()) {
            name = "Ostrich Patty";
            price += 6;
        }

        if (radAsiago.isChecked()) cheese = "Asiago";
        else cheese ="Creme Fraiche";
        Boolean pros;
        pros = cbPros.isChecked();

        if (cheese.equals("Asiago")) price += 1;
        else price += 0.5;
        if (pros == true) price += 2;


        butOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("cheese", cheese);
                intent.putExtra("pros", pros);
                intent.putExtra("spoon", tvSpoon.getText().toString());
                intent.putExtra("price", ""+price);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }




}