package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ojwang.edkins.home.homeSubCategory.PaidCreditorFragment;
import com.ojwang.edkins.home.homeSubCategory.PaidDebtorFragment;

public class PaidDebtViewPagerAdapter extends FragmentStateAdapter {

    public PaidDebtViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
            default: return new PaidDebtorFragment();
            case 1: return new PaidCreditorFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
