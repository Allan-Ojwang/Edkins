package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ojwang.edkins.MainActivity;
import com.ojwang.edkins.R;

public class StockInSub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in_sub);

        ImageButton backBtn = findViewById(R.id.backBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        ImageButton productBtn = findViewById(R.id.productBtn);

        TextView productName = findViewById(R.id.productName);
        productName.setText("Click to select a product");
        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, StockIn.class);
            startActivity(intent);
            finish();
        });
        productBtn.setOnClickListener(v ->{
            Intent intent = new Intent(StockInSub.this, StockList.class);
            intent.putExtra("TAG","STOCK-IN");
            startActivity(intent);
        });

        Intent intent = getIntent();
        if (intent != null){
            String pName = intent.getStringExtra("NAME");
            productName.setText(pName);

        }

        saveBtn.setOnClickListener(v->{

        });
    }
}