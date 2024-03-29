package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ojwang.edkins.home.homeSubCategory.CreditorFragment;
import com.ojwang.edkins.home.homeSubCategory.DebtorFragment;

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
