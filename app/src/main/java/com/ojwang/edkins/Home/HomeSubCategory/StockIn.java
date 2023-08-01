package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.ojwang.edkins.R;

public class StockIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);
        ImageButton newStockInfoBtn = findViewById(R.id.newStockInfoBtn);
        ImageButton oldStockInfoBtn = findViewById(R.id.oldStockInfoBtn);
        ImageButton newStockBtn = findViewById(R.id.newStockBtn);
        ImageButton oldStockBtn = findViewById(R.id.oldStockBtn);

        newStockInfoBtn.setOnClickListener(v -> showInfoDialog("Click this button to add a new product! The 'Add New Products' button is here to help you seamlessly update your system with newly stocked items that don't exist. If you've introduced new products to your inventory, this is where you can inform the system."));

        oldStockInfoBtn.setOnClickListener(v -> showInfoDialog("Click here to add restocked products! The 'Add Old Stock' button is here to help you easily update your system with freshly stocked products. If you've replenished your inventory, this is the place to let the system know."));
        newStockBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StockIn.this, NewStock.class);
            startActivity(intent);
        });

        oldStockBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StockIn.this, StockInSub.class);
            startActivity(intent);
        });
    }

    public void showInfoDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // You can perform additional actions if needed
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
