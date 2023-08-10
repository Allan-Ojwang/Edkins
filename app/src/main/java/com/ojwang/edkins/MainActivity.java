package com.ojwang.edkins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.ojwang.edkins.setting.SettingFragment;
import com.ojwang.edkins.home.HomeFragment;
import com.ojwang.edkins.profile.ProfileFragment;
import com.ojwang.edkins.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        setContentView(R.layout.activity_main);
        ChipNavigationBar navBar = findViewById(R.id.bottomNav);
        navBar.setItemSelected(R.id.homeBtn,true);
        replaceFragments(new HomeFragment());

        //          NAVIGATION BAR LISTENER
        navBar.setOnItemSelectedListener(i -> {
            if (i == R.id.homeBtn){
                replaceFragments(new HomeFragment());
            } else if (i == R.id.settingBtn) {
                replaceFragments(new SettingFragment());
            } else if (i == R.id.searchBtn) {
                replaceFragments(new SearchFragment());
            } else if (i == R.id.profileBtn) {
                replaceFragments(new ProfileFragment());
            }
        });
    }

    //          FRAGMENT REPLACING FUNCTION
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}