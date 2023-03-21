package com.ojwang.edkins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.ojwang.edkins.Setting.SettingFragment;
import com.ojwang.edkins.Home.HomeFragment;
import com.ojwang.edkins.Profile.ProfileFragment;
import com.ojwang.edkins.Search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChipNavigationBar navBar = findViewById(R.id.bottomNav);
        navBar.setItemSelected(R.id.homeBtn,true);
        replaceFragments(new HomeFragment());

        //          NAVIGATION BAR LISTENER
        navBar.setOnItemSelectedListener(i -> {
                switch (i){
                    case R.id.homeBtn:
                        replaceFragments(new HomeFragment());
                        break;
                    case R.id.settingBtn:
                        replaceFragments(new SettingFragment());
                        break;
                    case R.id.searchBtn:
                        replaceFragments(new SearchFragment());
                        break;
                    case R.id.profileBtn:
                        replaceFragments(new ProfileFragment());
                        break;
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