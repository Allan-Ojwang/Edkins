package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ojwang.edkins.MainActivity;
import com.ojwang.edkins.R;

public class StockOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        ImageButton backBtn = findViewById(R.id.backBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        ImageButton productBtn = findViewById(R.id.productBtn);
        TextView productName = findViewById(R.id.productName);
        productName.setText("Click to select a product");
        productName.setTextColor(getResources().getColor(R.color.text));

        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        productBtn.setOnClickListener(v ->{
            Intent intent = new Intent(StockOut.this, StockList.class);
            intent.putExtra("TAG","STOCK-OUT");
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