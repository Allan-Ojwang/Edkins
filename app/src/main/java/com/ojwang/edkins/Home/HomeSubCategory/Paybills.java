package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Database.EdkinsDb;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.PaybillAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;

public class Paybills extends AppCompatActivity implements AddPaybillTask.OnPaybillInputListener {

    public int Id;
    public String Title, Body;
    private MainViewModel mainViewModel;

    @Override
    public void sendInput( String title, String body) {
        Title = title;
        Body = body;
        PaybillModel paybillModel = new PaybillModel(Title,Body);
        mainViewModel.insertPaybill(paybillModel);
    }
    public void sendUpdateInput( int id, String title, String body) {
        Id = id;
        Title = title;
        Body = body;
        PaybillModel paybillModel = new PaybillModel(Title,Body);
        paybillModel.setId(id);
        mainViewModel.updatePaybill(paybillModel);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybills);

        FloatingActionButton addBtn = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.paybillRecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);

        PaybillAdapter paybillAdapter = new PaybillAdapter();
        recyclerView.setAdapter(paybillAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getPaybillData().observe(this, new Observer<List<PaybillModel>>() {
            @Override
            public void onChanged(List<PaybillModel> paybillModels) {
                paybillAdapter.setPaybillNotes(paybillModels);

            }
        });

        addBtn.setOnClickListener(v -> {
            AddPaybillTask addPaybills = new AddPaybillTask();
            addPaybills.show(getSupportFragmentManager(),AddPaybillTask.TAG);
        });

        backBtn.setOnClickListener(v -> finish());

//        Swipe to delete feature
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(Paybills.this, "Are you sure you want to delete?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Delete
                                mainViewModel.deletePaybill(paybillAdapter.getPaybillAt(viewHolder.getAdapterPosition()));
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do nothing
//                                mainViewModel.getPaybillData().observe(Paybills.this, new Observer<List<PaybillModel>>() {
//                                    @Override
//                                    public void onChanged(List<PaybillModel> paybillModels) {
//                                        paybillAdapter.setPaybillNotes(paybillModels);
//
//                                    }
//                                });
                                break;
                        }

                    }
                });
                confirmAlertDialog.showDialog();

            }
        }).attachToRecyclerView(recyclerView);

        paybillAdapter.setOnClickListener(new PaybillAdapter.OnItemClickListener() {
            @Override
            public void OnClick(PaybillModel paybillModel) {
                AddPaybillTask addPaybillTask = new AddPaybillTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",paybillModel.getId());
                bundle.putString("TITLE",paybillModel.getTitle());
                bundle.putString("BODY",paybillModel.getBody());
                addPaybillTask.setArguments(bundle);
                addPaybillTask.show(getSupportFragmentManager(),AddPaybillTask.EDIT_TAG);

            }
        });
//        TODO: Implement searchview
//        SearchView searchView = findViewById(R.id.paybillSearch);
//        searchView.setOnQueryTextListener(this);
    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        List<PaybillModel> items = db.paybillDao().searchPaybill("%");
//        adapter.setPaybillNotes(items);
//        return false;
//    }
}