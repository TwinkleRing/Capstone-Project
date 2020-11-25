package com.example.capstone_ui_1.Service;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_ui_1.Adapter.CustomAdapter;
import com.example.capstone_ui_1.HomeFragment;
import com.example.capstone_ui_1.MainActivity;
import com.example.capstone_ui_1.R;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    // HomeFragment 와 통신
    public static final int EDIT_OK_ADD = 10;
    public static final int EDIT_OK_EDIT = 20;
    public static final int EDIT_OK_DELETE = 30;

    // FindClassAcitivy 와 통신
    public static final int EDIT_REQUEST_ADD = 100;
    public static final int EDIT_REQUEST_EDIT = 200;

    // PopupActivity 와 통신
    public static final int EDIT_POP_CODE = 10000;

    private Context context;
    private Button alarmBtn;
    private Button time_table_search_button;
    private Button deleteBtn;
    private Button submitBtn;
    private EditText subjectEdit;
    private EditText classroomEdit;
    private EditText professorEdit;
    private Spinner daySpinner;
    private TextView startTv;
    private TextView endTv;
    private String Tag = "test";

    //request mode
    private int mode;

    private Schedule schedule;
    private int editIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        // 시간표 검색하기 버튼 누르면
        time_table_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindClassActivity.class);
//                intent.putExtra("mode", REQUEST_ADD);
//                intent.putExtra("schedules", schedule);
                startActivityForResult(intent, EDIT_REQUEST_ADD);
                Log.e("edit_startActivity", "startActivityForResult 실행됨");
                Log.e("edit_startActivity", String.valueOf(EDIT_REQUEST_ADD)); // 100
            }
        });
    }


    // 처리된 결과가 FindClassActivity의 FIND_OK_CODE이면 requestCode를 판별해 결과 처리 진행
    // 이제 FindClassActivity와 연결은 됨
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 그냥 submit 누르면 resultCode = 10
        // Find, Pop 거쳤다 오면 resultCode = 0
        Log.e("Edit_onActivityResult", "onActivityResult 실행됨");
        Log.e("EditAct_requestCode", String.valueOf(requestCode));
        Log.e("EditAct_resultCode", String.valueOf(resultCode));
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == FindClassActivity.FIND_OK_CODE && requestCode == EDIT_REQUEST_ADD) {
//            setResult(FindClassActivity.FIND_OK_CODE, data);
//            finish();
//        }
        if (resultCode == FindClassActivity.FIND_OK_CODE) {
            Log.e("Edit_requestCode", String.valueOf(requestCode));
            Log.e("message", "resultCode는 FIND_OK_CODE와 같음");
            Log.e("message", data.getStringExtra("professor"));
            switch (requestCode) {
                case EDIT_REQUEST_ADD:
                    // 이 부분 수정해야 함
//                    Intent i = getIntent();
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras()));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("professor")));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("classname")));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("classroom")));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("schedule")));
//                    ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
//                    Log.e("Edit_schedules", String.valueOf(schedules));
//                    schedule = schedules.get(0);
//                    subjectEdit.setText(schedule.getClassTitle());
//                    classroomEdit.setText(schedule.getClassPlace());
//                    professorEdit.setText(schedule.getProfessorName());
//                    daySpinner.setSelection(schedule.getDay());
                    Log.e("data_test", data.getStringExtra("classname"));
                    Log.e("data_test", data.getStringExtra("classroom"));
                    Log.e("data_test", data.getStringExtra("professor"));
                    subjectEdit.setText(data.getStringExtra("classname"));
                    classroomEdit.setText(data.getStringExtra("classroom"));
                    professorEdit.setText(data.getStringExtra("professor"));
                    break;
            }
        }
    }

    private void init(){
        this.context = this;
        deleteBtn = findViewById(R.id.delete_btn);
        submitBtn = findViewById(R.id.submit_btn);
        subjectEdit = findViewById(R.id.subject_edit);
        classroomEdit = findViewById(R.id.classroom_edit);
        professorEdit = findViewById(R.id.professor_edit);
        daySpinner = findViewById(R.id.day_spinner);
        startTv = findViewById(R.id.start_time);
        endTv = findViewById(R.id.end_time);
        alarmBtn = (Button) findViewById(R.id.alarm_btn);
        time_table_search_button = (Button) findViewById(R.id.timetable_search_button);


        //set the default time
        schedule = new Schedule();
        schedule.setStartTime(new Time(10,0));
        schedule.setEndTime(new Time(13,30));

        checkMode();
        initView();
    }

    /** check whether the mode is ADD or EDIT */
    private void checkMode(){
        Intent i = getIntent();
        mode = i.getIntExtra("mode", HomeFragment.HOME_REQUEST_ADD);

        if(mode == HomeFragment.HOME_REQUEST_EDIT){
            loadScheduleData();
            deleteBtn.setVisibility(View.VISIBLE);
            alarmBtn.setVisibility(View.VISIBLE);
        }
    }
    private void initView(){
        Intent test = getIntent();
        String get_classroom = test.getStringExtra("classroom");
        String get_classname = test.getStringExtra("classname");
        String get_professor = test.getStringExtra("professor");

        subjectEdit.setText(get_classname);
        classroomEdit.setText(get_classroom);
        professorEdit.setText(get_professor);


        submitBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        alarmBtn.setOnClickListener(this);

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), SettingActivity.class);
                intent2.putExtra("className",schedule.getClassTitle());
                startActivity(intent2);
                finish();
            }
        });


        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schedule.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,schedule.getStartTime().getHour(), schedule.getStartTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTv.setText(hourOfDay + ":" + minute);
                    schedule.getStartTime().setHour(hourOfDay);
                    schedule.getStartTime().setMinute(minute);
                    String test = schedule.getStartTime().toString();
                    Log.e(Tag, "log test : " + test);
                }
            };
        });
        endTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,schedule.getEndTime().getHour(), schedule.getEndTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTv.setText(hourOfDay + ":" + minute);
                    schedule.getEndTime().setHour(hourOfDay);
                    schedule.getEndTime().setMinute(minute);
                }
            };
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                Log.e("Edit_onclick_mode", String.valueOf(mode));
                Intent test = getIntent();
                Log.e("test_getExtras", String.valueOf(test.getExtras()));
                if (mode == PopupActivity.POP_REQUEST_ADD) {
                    inputDataProcessing();
                    // 결과를 Intent에 담아서 HomeFragment로 전달하고 현재 Activity는 종료
                    Intent i = getIntent();
                    Log.e("i_getExtras", String.valueOf(i.getExtras()));
                    Log.e("i_getExtras", String.valueOf(i.getExtras().get("professor")));
                    Log.e("i_getExtras", String.valueOf(i.getExtras().get("classname")));
                    Log.e("i_getExtras", String.valueOf(i.getExtras().get("classroom")));
                    Log.e("i_getExtras", String.valueOf(i.getExtras().get("timetest")));
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    //you can add more schedules m,to ArrayList
                    schedules.add(schedule);
                    Log.e("schedule_test", String.valueOf(schedule));
                    i.putExtra("schedules",schedules);
                    setResult(EDIT_POP_CODE, i);
                    finish();
                } else if (mode == FindClassActivity.FIND_OK_CODE){
                    Log.e("Find_mode", String.valueOf(mode));
                    inputDataProcessing();
                    // 결과를 Intent에 담아서 HomeFragment로 전달하고 현재 Activity는 종료
                    Intent i = new Intent();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    //you can add more schedules m,to ArrayList
                    schedules.add(schedule);
                    i.putExtra("schedules",schedules);
                    setResult(EDIT_OK_ADD, i);
                    finish();
                } else {
                    Log.e("Edit_mode", String.valueOf(mode));
                    inputDataProcessing();
                    // 결과를 Intent에 담아서 HomeFragment로 전달하고 현재 Activity는 종료
                    Intent i = new Intent();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    //you can add more schedules m,to ArrayList
                    schedules.add(schedule);
                    i.putExtra("schedules",schedules);
                    setResult(EDIT_OK_ADD, i);
                    finish();
                }
//                inputDataProcessing();
//                // 결과를 Intent에 담아서 HomeFragment로 전달하고 현재 Activity는 종료
//                Intent i = new Intent();
//                ArrayList<Schedule> schedules = new ArrayList<Schedule>();
//                //you can add more schedules m,to ArrayList
//                schedules.add(schedule);
//                i.putExtra("schedules",schedules);
//                setResult(EDIT_OK_ADD, i);
//                finish();
//                if(mode == HomeFragment.REQUEST_ADD || mode == PopupActivity.REQUEST_ADD){
//                    inputDataProcessing();
//                    Intent i = new Intent(getApplicationContext(), HomeFragment.class);
//                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
//                    //you can add more schedules m,to ArrayList
//                    schedules.add(schedule);
//                    i.putExtra("schedules",schedules);
//                    setResult(EDIT_RES_ADD,i);
//                    finish();
//                }
//                else if(mode == HomeFragment.REQUEST_EDIT){
//                    inputDataProcessing();
//                    Intent i = new Intent();
//                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
//                    schedules.add(schedule);
//                    i.putExtra("idx",editIdx);
//                    i.putExtra("schedules",schedules);
//                    setResult(RESULT_OK_EDIT,i);
//                    finish();
//                }
                break;
            case R.id.delete_btn:
                Intent i = new Intent();
                i.putExtra("idx",editIdx);
                setResult(EDIT_OK_DELETE, i);
                finish();
                break;
        }
    }

    private void loadScheduleData(){
        Intent i = getIntent();
        editIdx = i.getIntExtra("idx",-1);
        ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
        schedule = schedules.get(0);
        subjectEdit.setText(schedule.getClassTitle());
        classroomEdit.setText(schedule.getClassPlace());
        professorEdit.setText(schedule.getProfessorName());
        daySpinner.setSelection(schedule.getDay());
    }

    private void inputDataProcessing(){
        schedule.setClassTitle(subjectEdit.getText().toString());
        schedule.setClassPlace(classroomEdit.getText().toString());
        schedule.setProfessorName(professorEdit.getText().toString());
    }
}