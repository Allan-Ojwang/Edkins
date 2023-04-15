package com.ojwang.edkins.Home.HomeSubCategory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.DebtTrackerViewPagerAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class DebtTracker extends AppCompatActivity  implements AddCreditorTask.OnCreditorInputListener,AddDebtorTask.OnDebtorInputListener{

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DebtTrackerViewPagerAdapter debtTrackerViewPagerAdapter;

    private MainViewModel mainViewModel;

    public String cred_Name,debt_Name,debt_Reason,cred_Reason;
    public int cred_Amount,debt_Amount;

    @Override
    public void sendInputCreditor(String name, String reason, int amount) {
        cred_Name = name;
        cred_Reason = reason;
        cred_Amount = amount;
        CreditorModel creditorModel = new CreditorModel(cred_Name,cred_Reason,cred_Amount);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.insertCreditor(creditorModel);
    }

    @Override
    public void sendInputDebtor(String name, String reason, int amount) {
        debt_Name = name;
        debt_Reason = reason;
        debt_Amount = amount;
        DebtorModel debtorModel = new DebtorModel(debt_Name,debt_Reason,debt_Amount);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.insertDebtor(debtorModel);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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