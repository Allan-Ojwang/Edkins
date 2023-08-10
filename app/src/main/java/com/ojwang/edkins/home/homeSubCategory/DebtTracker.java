package com.ojwang.edkins.home.homeSubCategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.ojwang.edkins.home.homeSubCategory.model.CreditorModel;
import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.DebtTrackerViewPagerAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.Objects;

public class DebtTracker extends AppCompatActivity  implements AddCreditorTask.OnCreditorInputListener,AddDebtorTask.OnDebtorInputListener{

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DebtTrackerViewPagerAdapter debtTrackerViewPagerAdapter;

    private MainViewModel mainViewModel;

    public String Name,Reason;
    public int Amount,Id;

    @Override
    public void sendInputCreditor(String name, String reason, int amount) {
        Name = name;
        Reason = reason;
        Amount = amount;
        CreditorModel creditorModel = new CreditorModel(Name,Reason,Amount);
        mainViewModel.insertCreditor(creditorModel);
    }

    @Override
    public void sendUpdateInputCreditor(int id,String name, String reason, int amount,Boolean status) {
        Id = id;
        Name = name;
        Reason = reason;
        Amount = amount;
        CreditorModel creditorModel = new CreditorModel(Name,Reason,Amount);
        creditorModel.setId(Id);
        creditorModel.setpStatus(status);
        mainViewModel.updateCreditor(creditorModel);
    }


    @Override
    public void sendInputDebtor(String name, String reason, int amount) {
        Name = name;
        Reason = reason;
        Amount = amount;
        DebtorModel debtorModel = new DebtorModel(Name,Reason,Amount);
        mainViewModel.insertDebtor(debtorModel);
    }

    @Override
    public void sendUpdateInputDebtor(int id, String name, String reason, int amount, Boolean status) {
        Id = id;
        Name = name;
        Reason = reason;
        Amount = amount;
        DebtorModel debtorModel = new DebtorModel(Name,Reason,Amount);
        debtorModel.setId(Id);
        debtorModel.setpStatus(status);
        mainViewModel.updateDebtor(debtorModel);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_tracker);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        debtTrackerViewPagerAdapter = new DebtTrackerViewPagerAdapter(this);
        viewPager2.setAdapter(debtTrackerViewPagerAdapter);
        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton menuButton = findViewById(R.id.menu_button);



        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        backBtn.setOnClickListener(v -> finish());

        menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(DebtTracker.this, menuButton);
                
                popupMenu.getMenuInflater().inflate(R.menu.debt_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuPaid){
                            Intent intentPaid = new Intent(DebtTracker.this, PaidDebt.class);
                            startActivity(intentPaid);
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });


    }

}