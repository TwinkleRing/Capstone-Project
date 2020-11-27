package com.example.capstone_ui_1.Service;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_ui_1.Adapter.LectureListRecyclerViewAdapter;
import com.example.capstone_ui_1.BackPressCloseHandler;
import com.example.capstone_ui_1.MainActivity;
import com.example.capstone_ui_1.R;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {


    MyDBHelper myHelper;
    RecyclerView lecture_list;
    BackPressCloseHandler backPressCloseHandler;
    com.example.capstone_ui_1.Adapter.LectureListRecyclerViewAdapter LectureListRecyclerViewAdapter;
    ArrayList<LectureList> lectureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        myHelper = new MyDBHelper(this);
        lectureList.addAll(myHelper.allLListItems());

        backPressCloseHandler = new BackPressCloseHandler(this);


        lecture_list = (RecyclerView) findViewById(R.id.lecture_list);
        LectureListRecyclerViewAdapter = new LectureListRecyclerViewAdapter(lectureList, this);
        lecture_list.setAdapter(LectureListRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lecture_list.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);

        super.onBackPressed();
        this.finish();
    }
}
