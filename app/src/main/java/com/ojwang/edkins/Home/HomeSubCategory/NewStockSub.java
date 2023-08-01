package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

public class NewStockSub extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private int id = -1;
    private int adapterPos, sellingPrice;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stock_sub);

        ImageButton backBtn = findViewById(R.id.backBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        EditText evName =  findViewById(R.id.evProductName);
        EditText evSp = findViewById(R.id.evSp);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        backBtn.setOnClickListener(v -> finish());
        saveBtn.setOnClickListener( v -> {

            if (evName.getText().toString().trim().isEmpty() || evSp.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter the product name and selling price", Toast.LENGTH_SHORT).show();
            } else{
                String name = evName.getText().toString();
                int sellingPrice = Integer.parseInt(evSp.getText().toString());

                StockModel stockModel = new StockModel(name, sellingPrice);

                if (id != -1){
                    stockModel.setId(id);
                    mainViewModel.updateStock(stockModel);
                    Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();

                } else{
                    mainViewModel.insertStock(stockModel);
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                }
                evName.getText().clear();
                evSp.getText().clear();
            }

        });
    }
}