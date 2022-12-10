package com.ojwang.edkins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragments(new HomeFragment());

        //        NAVIGATION BAR
        BottomNavigationView navBar = findViewById(R.id.bottomNav);
        navBar.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.homeBtn:
                    replaceFragments(new HomeFragment());
                    break;
                case R.id.addBtn:
                    replaceFragments(new AddFragment());
                    break;
                case R.id.searchBtn:
                    replaceFragments(new SearchFragment());
                    break;
                case R.id.profileBtn:
                    replaceFragments(new ProfileFragment());
                    break;
            }

            return true;
        });
    }
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}