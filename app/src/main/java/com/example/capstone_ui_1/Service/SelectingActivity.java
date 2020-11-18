package com.example.capstone_ui_1.Service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_ui_1.Adapter.ListItemRecyclerViewAdapter;
import com.example.capstone_ui_1.MainActivity;
import com.example.capstone_ui_1.R;

import java.util.ArrayList;

public class SelectingActivity extends AppCompatActivity {
    Button btn_plus_fill;
    RecyclerView rv_list;
    ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;
    ArrayList<ListItem> selectingList = new ArrayList<>();
    MyDBHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting);

        btn_plus_fill = (Button) findViewById(R.id.btn_plus_fill);

        btn_plus_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

                finish();
            }
        });

        myHelper = new MyDBHelper(this);

        selectingList.addAll(myHelper.allListItems());

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        listItemRecyclerViewAdapter = new ListItemRecyclerViewAdapter(selectingList, this);
        rv_list.setAdapter(listItemRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        super.onBackPressed();
    }


}
