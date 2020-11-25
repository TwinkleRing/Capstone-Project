package com.example.capstone_ui_1.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstone_ui_1.HomeFragment;
import com.example.capstone_ui_1.R;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PopupActivity extends Activity {
    Button myCheck;
    Button myCancel;
    TextView mytest;
    HomeFragment homefrag;
    private Schedule new_schedule;
    public static final int POP_REQUEST_ADD = 5000;
    public static final int POP_EDIT_CODE = 10001;
    public static final int REQUEST_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        Schedule new_schedule = new Schedule();
        Intent my_getIntent = getIntent();
        homefrag = new HomeFragment();
        String given_classname = my_getIntent.getStringExtra("classname");
        String given_classroom = my_getIntent.getStringExtra("classroom");
        String given_professor = my_getIntent.getStringExtra("professor");
        String given_time = my_getIntent.getStringExtra("time");



        mytest = (TextView) findViewById(R.id.test);
        mytest.setText(given_classname);

        myCheck = (Button) findViewById(R.id.check);
        myCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Schedule> schedules = new ArrayList<Schedule>();

                Intent addSchedule = new Intent(getApplicationContext(), EditActivity.class);
                addSchedule.putExtra("classname", given_classname);
                addSchedule.putExtra("classroom", given_classroom);
                addSchedule.putExtra("professor", given_professor);
                addSchedule.putExtra("timetest", given_time);
                addSchedule.putExtra("mode", POP_REQUEST_ADD);

//                Intent test = new Intent(getApplicationContext(), FindClassActivity.class);
//                test.putExtra("classname", given_classname);
//                test.putExtra("classroom", given_classroom);
//                test.putExtra("professor", given_professor);
//                test.putExtra("mode", POP_REQUEST_ADD);
//                setResult(POP_REQUEST_ADD, test);
//                Log.e("Popup", "PopupActivity 에서 실행됨");

                startActivityForResult(addSchedule, POP_REQUEST_ADD);

//                startActivityForResult(test, REQUEST_ADD);
//                new_schedule.setClassPlace(given_classroom);
//                new_schedule.setClassTitle(given_classname);
//                new_schedule.setProfessorName(given_professor);
//                new_schedule.setDay(3);
//                new_schedule.setStartTime(new Time(10,0));
//                new_schedule.setEndTime(new Time(12,0));
//                schedules.add(new_schedule);
//                addSchedule.putExtra("schedules", schedules);
                finish();
            }
        });
        myCancel = (Button) findViewById(R.id.cancel);
        myCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Pop_onActivityResult", "Popup에서 onActivityResult 실행됨");
        if (resultCode == EditActivity.EDIT_POP_CODE) {
            switch (requestCode) {
                case POP_EDIT_CODE:
                    Intent i = new Intent();
                    i.putExtra("result", "done");
                    break;
            }
        }
    }

}