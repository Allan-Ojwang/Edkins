package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ojwang.edkins.Home.HomeSubCategory.CreditorFragment;
import com.ojwang.edkins.Home.HomeSubCategory.DebtorFragment;

public class DebtTrackerViewPagerAdapter extends FragmentStateAdapter {

    public DebtTrackerViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
            default: return new DebtorFragment();
            case 1: return new CreditorFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
