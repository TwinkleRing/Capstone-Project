package com.example.capstone_ui_1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_ui_1.Adapter.LectureListRecyclerViewAdapter;
import com.example.capstone_ui_1.Service.ClassActivity;
import com.example.capstone_ui_1.Service.LectureList;
import com.example.capstone_ui_1.Service.MyDBHelper;
import com.example.capstone_ui_1.Service.SelectingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase sqlDB;
    MyDBHelper myHelper;
    RecyclerView lecture_list;
    BackPressCloseHandler backPressCloseHandler;
    LectureListRecyclerViewAdapter LectureListRecyclerViewAdapter;

    FragmentManager fragmentManager = getSupportFragmentManager();
    ArrayList<LectureList> lectureList = new ArrayList<>();

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    NavigationFragment navigationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new MyDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();

        backPressCloseHandler = new BackPressCloseHandler(this);

        mBottomNavigationView();

    }

    private void mBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        homeFragment = new HomeFragment();
        navigationFragment = new NavigationFragment();


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
                        Intent Class = new Intent(MainActivity.this, ClassActivity.class);
                        startActivity(Class);
                        break;
                    }
                    case R.id.bottom_navi_alarm: {
                        Intent Select = new Intent(MainActivity.this, SelectingActivity.class);
                        startActivity(Select);
                        break;
                    }
                    case R.id.bottom_navi_navigation: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, navigationFragment).commitAllowingStateLoss();
                        break;
                    }

                }
                return true;
            }
            });
        }



}