package com.ojwang.edkins.home.homeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.ojwang.edkins.home.homeSubCategory.model.CreditorModel;
import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.PaidDebtViewPagerAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.Objects;

public class PaidDebt extends AppCompatActivity implements AddCreditorTask.OnCreditorInputListener,AddDebtorTask.OnDebtorInputListener{
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PaidDebtViewPagerAdapter paidDebtViewPagerAdapter;

    private MainViewModel mainViewModel;

    public String Name,Reason;
    public int Amount,Id;


    @Override
    public void sendInputCreditor(String name, String reason, int amount) {

    }

    @Override
    public void sendUpdateInputCreditor(int id,String name, String reason, int amount, Boolean status) {
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
        setContentView(R.layout.activity_paid_debt);


        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        paidDebtViewPagerAdapter = new PaidDebtViewPagerAdapter(this);
        viewPager2.setAdapter(paidDebtViewPagerAdapter);
        ImageButton backBtn = findViewById(R.id.backBtn);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

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