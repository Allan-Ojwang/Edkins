package com.ojwang.edkins.home.homeSubCategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.ConfirmAlertDialog;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderListModel;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.ToOrderAdapter;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.ToOrderListAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ToOrderSub extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ToOrderListAdapter toOrderListAdapter;

    private Long orderId;

    private int adapPos,day,year;
    private  String toOrderStatus,month,itemListText;
    private Integer numbOfStatus,totalOrder ;
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

        StringBuilder itemListBuilder = new StringBuilder();


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
        RecyclerView recyclerView = findViewById(R.id.toOrderSubRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(toOrderListAdapter);


        mainViewModel.getToOrderListData(orderId).observeForever(new Observer<List<ToOrderListModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ToOrderListModel> toOrderListModels) {
                // Update the orderNotes list with the new data
                orderNotes.clear();
                orderNotes.addAll(toOrderListModels);
                toOrderListAdapter.notifyDataSetChanged();

                for (ToOrderListModel order : toOrderListModels) {
                    String item = order.getItem();
                    itemListBuilder.append(item).append("\n");
                }
                itemListText = itemListBuilder.toString();

            }
        });


        MediatorLiveData<Pair<Integer, Integer>> mediatorLiveData = new MediatorLiveData<>();

        // Observe the LiveData for the total order count
        LiveData<Integer> totalOrderLiveData = mainViewModel.getNumbOfOrder(Math.toIntExact(orderId));
        mediatorLiveData.addSource(totalOrderLiveData, totalOrder -> {
            // When the totalOrderLiveData emits a value, update the totalOrder in the Pair
            Pair<Integer, Integer> pair = mediatorLiveData.getValue();
            if (pair == null) {
                pair = new Pair<>(totalOrder, null);
            } else {
                pair = new Pair<>(totalOrder, pair.second);
            }
            mediatorLiveData.setValue(pair);
        });

        // Observe the LiveData for the number of orders with a status
        LiveData<Integer> orderStatusLiveData = mainViewModel.getNumbOfOrderStatus(Math.toIntExact(orderId));
        mediatorLiveData.addSource(orderStatusLiveData, orderStatus -> {
            // When the orderStatusLiveData emits a value, update the orderStatus in the Pair
            Pair<Integer, Integer> pair = mediatorLiveData.getValue();
            if (pair == null) {
                pair = new Pair<>(null, orderStatus);
            } else {
                pair = new Pair<>(pair.first, orderStatus);
            }
            mediatorLiveData.setValue(pair);
        });
        mediatorLiveData.observe(this, new Observer<Pair<Integer, Integer>>() {
            @Override
            public void onChanged(Pair<Integer, Integer> integerIntegerPair) {
                numbOfStatus = integerIntegerPair.first;
                totalOrder = integerIntegerPair.second;
                ToOrderModel toOrderModel = new ToOrderModel(year,month,day);
                toOrderModel.setOrderId(Math.toIntExact(orderId));
                int dateDueDiff = dateDue();


                if (totalOrder !=null && numbOfStatus !=null){
                    if (dateDueDiff <= -7 && !numbOfStatus.equals(totalOrder)){
                        toOrderStatus = "Over Due";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                    } else if (numbOfStatus == 0 && totalOrder == 0) {
                        toOrderStatus = "NA";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                    } else if (numbOfStatus.equals(totalOrder)) {
                        toOrderStatus = "Done";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                    } else {
                        toOrderStatus = "Processing";
                    toOrderModel.setStatus(toOrderStatus);
                    mainViewModel.updateOrder(toOrderModel);
                    }
                }

            }
        });
        addBtn.setOnClickListener(v -> {
            orderNotes.add(new ToOrderListModel("", Math.toIntExact(orderId), false));
            toOrderListAdapter.notifyItemInserted(orderNotes.size() - 1);
        });

        backBtn.setOnClickListener(v -> finish());

        shareBtn.setOnClickListener(v -> shareItemListToWhatsApp(this,this));

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

    private int dateDue(){
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        currentMonth = currentMonth+1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);


        int setYear = year;
        int setMonth;
        int setDay = day;
        switch (month){
            case "JAN":
                setMonth = 1;
                break;
            case "FEB":
                setMonth = 2;
                break;
            case "MAR":
                setMonth = 3;
                break;
            case "APR":
                setMonth = 4;
                break;
            case "MAY":
                setMonth = 5;
                break;
            case "JUN":
                setMonth = 6;
                break;
            case "JUL":
                setMonth = 7;
                break;
            case "AUG":
                setMonth = 8;
                break;
            case "SEP":
                setMonth = 9;
                break;
            case "OCT":
                setMonth = 10;
                break;
            case "NOV":
                setMonth = 11;
                break;
            case "DEC":
                setMonth = 12;
                break;
            default:
                setMonth = 0;
                break;

        }

        int yearDiff = setYear - currentYear;
        int monthDiff = setMonth - currentMonth;
        int dayDiff = setDay - currentDay;



        return yearDiff * 365 + monthDiff * 30 + dayDiff;
    }

    private void shareItemListToWhatsApp(Context context, LifecycleOwner lifecycleOwner) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, itemListText);

        shareIntent.setPackage("com.whatsapp");

        try {
            context.startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Uri playStoreUri = Uri.parse("market://details?id=com.whatsapp");
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);
            context.startActivity(playStoreIntent);
        }

    }
}