package com.example.capstone_ui_1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    NavigationFragment navigationFragment;
    AlarmFragment alarmFragment;
    ClassFragment classFragment;
    TensorFragment tensorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView();
    }

    private void mBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        homeFragment = new HomeFragment();
        navigationFragment = new NavigationFragment();
        classFragment = new ClassFragment();
        alarmFragment = new AlarmFragment();
        tensorFragment = new TensorFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navi_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_navi_class: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, classFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_navi_alarm: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, alarmFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_navi_navigation: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, navigationFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_navi_tensor: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, tensorFragment).commitAllowingStateLoss();
                        break;
                    }

                }
                return true;
            }
            });
        }



}