package com.ojwang.edkins.Home.HomeSubCategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.ToOrderAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.ToOrderListAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class ToOrderSub extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private ToOrderListAdapter toOrderListAdapter;

    private Long orderId;

    private int adapPos,day,year;
    private  String toOrderStatus,month;
    private int numbOfStatus,totalOrder ;
    private static List<ToOrderListModel> orderNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_order_sub);

        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton deleteBtn = findViewById(R.id.deleteBtn);
        ImageButton saveBtn = findViewById(R.id.saveBtn);
        ImageButton shareBtn = findViewById(R.id.shareBtn);
        ImageButton addBtn = findViewById(R.id.addBtn);
        TextView orderNo = findViewById(R.id.orderNo);


        orderId = getIntent().getLongExtra("orderId", 0);
        adapPos = getIntent().getIntExtra("adapPos", 0);
        year = getIntent().getIntExtra("year", 0);
        month = getIntent().getStringExtra("month");
        day = getIntent().getIntExtra("day", 0);

        orderNotes.clear();
        orderNotes.add(new ToOrderListModel("", Math.toIntExact(orderId), false));
        toOrderListAdapter = new ToOrderListAdapter(orderNotes);


        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        String orderNoForm = "Order#" + orderId;
        orderNo.setText(orderNoForm);
        recyclerView = findViewById(R.id.toOrderSubRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(toOrderListAdapter);

        mainViewModel.getToOrderListData(orderId).observe(this, new Observer<List<ToOrderListModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ToOrderListModel> toOrderListModels) {
                // Update the orderNotes list with the new data
                orderNotes.clear();
                orderNotes.addAll(toOrderListModels);
                Log.e("TOTALORDER", String.valueOf(totalOrder));
                Log.e("STATUSNUMB", String.valueOf(numbOfStatus));
                ToOrderModel toOrderModel = new ToOrderModel(year,month,day);
                toOrderModel.setOrderId(Math.toIntExact(orderId));

                if (totalOrder == 0){
                    toOrderStatus = "NA";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                } else if (numbOfStatus == totalOrder) {
                    toOrderStatus = "Done";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                } else if (numbOfStatus < totalOrder) {
                    toOrderStatus = "Processing";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                }
                Log.e("ORDERSTATUS",toOrderStatus);
                toOrderListAdapter.notifyDataSetChanged();
            }
        });

        mainViewModel.getNumbOfOrder(Math.toIntExact(orderId)).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                totalOrder = integer;
            }
        });

        mainViewModel.getNumbOfOrderStatus(Math.toIntExact(orderId)).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                numbOfStatus = integer;
            }
        });
        addBtn.setOnClickListener(v -> {
            orderNotes.add(new ToOrderListModel("", Math.toIntExact(orderId), false));
            toOrderListAdapter.notifyItemInserted(orderNotes.size() - 1);
        });

        backBtn.setOnClickListener(v -> finish());

        shareBtn.setOnClickListener(v -> shareItemListToWhatsApp(this));

        saveBtn.setOnClickListener(v -> {
            List<ToOrderListModel> orderList = toOrderListAdapter.getOrderNotes();
            if (orderList != null && !orderList.isEmpty()) {
                boolean hasNonEmptyData = false;
                for (ToOrderListModel updatedItem : orderList) {
                    String item = updatedItem.getItem();
                    boolean status = updatedItem.isoStatus();
                    int id = updatedItem.getId();

                    // Check if the EditText contains non-empty text before saving
                    if (item.trim().isEmpty()) {
                        hasNonEmptyData = true;
                    } else {

                        ToOrderListModel toOrderListModel = new ToOrderListModel(item, Math.toIntExact(orderId), status);

                        if (updatedItem.getId() !=0){
                            toOrderListModel.setId(id);
                            mainViewModel.updateToOrderList(toOrderListModel);

                        } else{
                            mainViewModel.insertToOrderList(toOrderListModel);
                        }
                    }
                }
                if (hasNonEmptyData){
                    Toast.makeText(ToOrderSub.this, "Please enter some text before saving.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ToOrderSub.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(ToOrderSub.this, "No data to save!", Toast.LENGTH_SHORT).show();
            }

        });

        deleteBtn.setOnClickListener(v -> {
            ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(ToOrderSub.this, "Are you sure you want to delete the whole order? \n \n THIS PROCESS IS IRREVERSIBLE!!!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Delete
                            if (orderId != 0) {
                                ToOrderAdapter toOrderAdapter = new ToOrderAdapter();
                                mainViewModel.deleteToOrder(toOrderAdapter.getOrderAt(adapPos));
                                dialog.dismiss();
                                finish();
                            } else {
                                Toast.makeText(ToOrderSub.this, "Please select a order to delete", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //Do nothing
                            break;
                    }

                }
            });
            confirmAlertDialog.showDialog();
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ToOrderListModel toOrderListModel = toOrderListAdapter.getOrderListAt(position);
                mainViewModel.deleteToOrderList(toOrderListModel);
            }
        }).attachToRecyclerView(recyclerView);


    }

    private void shareItemListToWhatsApp(Context context) {

        StringBuilder itemListBuilder = new StringBuilder();
        mainViewModel.getToOrderListData(orderId).observe(this, new Observer<List<ToOrderListModel>>() {
            @Override
            public void onChanged(List<ToOrderListModel> toOrderListModels) {
                for (ToOrderListModel order : toOrderListModels) {
                    String item = order.getItem();
                    itemListBuilder.append(item).append("\n");
                }
                String itemListText = itemListBuilder.toString();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, itemListText);

//                PackageManager packageManager = context.getPackageManager();
//                List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(shareIntent, 0);
//
//                if (!resolveInfoList.isEmpty()) {
//                    // Create a chooser dialog to present the available sharing options to the user
//                    Intent chooserIntent = Intent.createChooser(shareIntent, "Share via...");
//
//                    // Check if the user has WhatsApp installed
//                    boolean whatsappInstalled = false;
//                    for (ResolveInfo resolveInfo : resolveInfoList) {
//                        String packageName = resolveInfo.activityInfo.packageName;
//                        if (packageName.equals("com.whatsapp")) {
//                            whatsappInstalled = true;
//                            break;
//                        }
//                    }
//
//                    // If WhatsApp is installed, set the package name to restrict the sharing intent to WhatsApp
//                    if (whatsappInstalled) {
//                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{shareIntent});
//                        chooserIntent.putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, new ComponentName[]{new ComponentName("com.whatsapp.w4b", "com.whatsapp.w4b.ContactPicker")}); // Optional: Exclude WhatsApp Business
//                    }
//
//                    // Start the chooser dialog
//                    context.startActivity(chooserIntent);
//                } else {
//                    // Handle the case where no apps can handle the share intent
//                    Toast.makeText(context, "No apps available to share the item list.", Toast.LENGTH_LONG).show();
//                }
                shareIntent.setPackage("com.gbwhatsapp");

                try {
                    context.startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Uri playStoreUri = Uri.parse("market://details?id=com.whatsapp");
                    Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);
                    context.startActivity(playStoreIntent);
                }


            }
        });


        // Set the package to WhatsApp to ensure WhatsApp is the destination app for sharing


    }
}