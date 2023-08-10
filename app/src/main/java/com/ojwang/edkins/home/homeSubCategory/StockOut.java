package com.ojwang.edkins.home.homeSubCategory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ojwang.edkins.MainActivity;
import com.ojwang.edkins.R;
import com.ojwang.edkins.home.homeSubCategory.model.StockOutModel;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.Calendar;

public class StockOut extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private int setDay,setYear, storeId,stockId;
    private String setMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        ImageButton backBtn = findViewById(R.id.backBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        ImageButton productBtn = findViewById(R.id.productBtn);
        TextView pId = findViewById(R.id.pId);
        EditText evProductName = findViewById(R.id.evProductName);
        ImageButton dateBtn = findViewById(R.id.dateBtn);
        EditText evDate = findViewById(R.id.evDate);
        ImageButton storeBtn = findViewById(R.id.storeBtn);
        EditText evStore = findViewById(R.id.evStore);
        EditText evSp = findViewById(R.id.sellingPrice);
        EditText evQuantity = findViewById(R.id.quantity);


        AddOrderTask addOrderTask = new AddOrderTask();

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                setMonth = addOrderTask.makeDateString(month);
                setDay = dayOfMonth;
                setYear = year;
                String formatted = setDay+" "+setMonth+" "+setYear;
                evDate.setText(formatted);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = android.R.style.Theme_Material_Dialog_Alert;
        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);

        dateBtn.setOnClickListener(v -> datePickerDialog.show());
        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        productBtn.setOnClickListener(v ->{
            if (evDate.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please select a date to continue", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(StockOut.this, StockList.class);
                intent.putExtra("DATE",evDate.getText().toString());
                intent.putExtra("TAG","STOCK-OUT");
                startActivity(intent);
            }
        });
        storeBtn.setOnClickListener(v->{
            if (evDate.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please select a date to continue", Toast.LENGTH_SHORT).show();
            } else if (evProductName.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please select a product to continue", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(StockOut.this, StoreList.class);
                intent.putExtra("DATE",evDate.getText().toString());
                intent.putExtra("PRODUCT-ID",Integer.parseInt(pId.getText().toString()));
                intent.putExtra("PRODUCT-NAME",evProductName.getText().toString());
                intent.putExtra("TAG","STOCK-OUT");
                startActivity(intent);
            }

        });
        Intent intent = getIntent();
        if (intent != null){
            String pName = intent.getStringExtra("PRODUCT-NAME");
            String sName = intent.getStringExtra("STORE-NAME");
            stockId = intent.getIntExtra("PRODUCT-ID",0);
            storeId= intent.getIntExtra("STORE-ID",0);
            String setDate = intent.getStringExtra("DATE");

            evDate.setText(setDate);
            evProductName.setText(pName);
            evStore.setText(sName);
            pId.setText(String.valueOf(stockId));

        }
        saveBtn.setOnClickListener(v->{
            String date = evDate.getText().toString();
            String [] dateParts= date.split(" ");
            int daySet = Integer.parseInt(dateParts[0]);
            String monthSet = dateParts[1];
            int yearSet = Integer.parseInt(dateParts[2]);

            String p_Name = evProductName.getText().toString();
            String s_Name = evStore.getText().toString();
            int s_Price = Integer.parseInt(evSp.getText().toString());
            int quantity = Integer.parseInt(evQuantity.getText().toString());

            if (stockId <= 0 || storeId<= 0){
                Toast.makeText(this,"Please enter all the fields to save",Toast.LENGTH_SHORT).show();
            } else {
                StockOutModel stockOutModel = new StockOutModel(yearSet,monthSet,daySet,stockId,p_Name,storeId,s_Name,s_Price,quantity);
                mainViewModel.insertStockOut(stockOutModel);
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            }

        });
    }
}