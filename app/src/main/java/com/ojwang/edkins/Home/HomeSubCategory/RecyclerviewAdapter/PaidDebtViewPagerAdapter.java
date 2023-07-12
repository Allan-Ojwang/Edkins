package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ojwang.edkins.Home.HomeSubCategory.CreditorFragment;
import com.ojwang.edkins.Home.HomeSubCategory.DebtorFragment;
import com.ojwang.edkins.Home.HomeSubCategory.PaidCreditorFragment;
import com.ojwang.edkins.Home.HomeSubCategory.PaidDebtorFragment;

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
