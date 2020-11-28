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

import static com.example.capstone_ui_1.Adapter.CustomAdapter.RESULT_OK_EDIT;

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
    private Spinner daySpinner, daySpinner2;
    private TextView startTv, startTv2;
    private TextView endTv, endTv2;
    private String Tag = "test";

    //request mode
    private int mode;

    private Schedule schedule, schedule2;
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
//                    Log.e("data_test", data.getStringExtra("day1"));
                    subjectEdit.setText(data.getStringExtra("classname"));
                    classroomEdit.setText(data.getStringExtra("classroom"));
                    professorEdit.setText(data.getStringExtra("professor"));
                    daySpinner.setSelection(Integer.parseInt(String.valueOf(data.getLongExtra("day1",0)))-1);
                    String[] hourandminute = timeProcessing(data.getStringExtra("day1_start_time"));
                    startTv.setText(hourandminute[0] + ":" + hourandminute[1]);
                    schedule.setStartTime(new Time(Integer.parseInt(hourandminute[0]),Integer.parseInt(hourandminute[1])));

                    String[] hourandminute2 = timeProcessing(data.getStringExtra("day1_end_time"));
                    endTv.setText(hourandminute2[0] + ":" + hourandminute2[1]);
                    schedule.setEndTime(new Time(Integer.parseInt(hourandminute2[0]),Integer.parseInt(hourandminute2[1])));

                    // TODO 예외처리
                    daySpinner2.setSelection(Integer.parseInt(String.valueOf(data.getLongExtra("day2",0)))-1);

                    String[] hourandminute3 = {"0","0"};
                    try {
                        hourandminute3 = timeProcessing(data.getStringExtra("day2_start_time"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    startTv2.setText(hourandminute3[0] + ":" + hourandminute3[1]);
                    schedule2.setStartTime(new Time(Integer.parseInt(hourandminute3[0]),Integer.parseInt(hourandminute3[1])));

                    String[] hourandminute4 = {"0","0"};
                    try {
                        hourandminute4 = timeProcessing(data.getStringExtra("day2_end_time"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    endTv2.setText(hourandminute4[0] + ":" + hourandminute4[1]);
                    schedule2.setEndTime(new Time(Integer.parseInt(hourandminute4[0]),Integer.parseInt(hourandminute4[1])));
                    break;

                case EDIT_REQUEST_EDIT:
//                case EDIT_REQUEST_EDIT:
//                    // 이 부분 수정해야 함
//                    Intent i = getIntent();
//                    int idx = i.getIntExtra("idx", 0);
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras()));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("professor")));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("classname")));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("classroom")));
//                    Log.e("onAct_i_getExtras", String.valueOf(i.getExtras().get("schedule")));
//                    ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
//                    Log.e("Edit_schedules", String.valueOf(schedules));
//                    schedule = schedules.get(idx);
//                    subjectEdit.setText(schedule.getClassTitle());
//                    classroomEdit.setText(schedule.getClassPlace());
//                    professorEdit.setText(schedule.getProfessorName());
//                    daySpinner.setSelection(schedule.getDay());
//                    Log.e("data_test", data.getStringExtra("classname"));
//                    Log.e("data_test", data.getStringExtra("classroom"));
//                    Log.e("data_test", data.getStringExtra("professor"));
////                    Log.e("data_test", data.getStringExtra("day1"));

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

        daySpinner2 = findViewById(R.id.day_spinner2);
        startTv2 = findViewById(R.id.start_time2);
        endTv2 = findViewById(R.id.end_time2);


        //set the default time
        schedule = new Schedule();
        schedule.setStartTime(new Time(10,0));
        schedule.setEndTime(new Time(13,30));

        // TODO
        schedule2 = new Schedule();
        schedule2.setStartTime(new Time(10,0));
        schedule2.setEndTime(new Time(13,30));

        checkMode();
        initView();
    }

    /** check whether the mode is ADD or EDIT */
    private void checkMode(){
        Intent i = getIntent();
        mode = i.getIntExtra("mode", HomeFragment.HOME_REQUEST_ADD);
        Log.d("what is mode?", "" + mode);
        // Home에서 tiemtable 클릭시 여기로 넘어옴
        if(mode == HomeFragment.HOME_REQUEST_EDIT){
            Log.d("here come?", "" + mode);
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

//        long get_day1 = test.getExtra("day1",3);

        int get_day1 = Integer.parseInt(String.valueOf(test.getLongExtra("day1",0)));
        String get_day1_start_time = test.getStringExtra("day1_start_time");
        String get_day1_end_time = test.getStringExtra("day1_end_time");

        long get_day2 = test.getIntExtra("day2", 0);
        String get_day2_start_time = test.getStringExtra("day2_start_time");
        String get_day2_end_time = test.getStringExtra("day2_end_time");

        subjectEdit.setText(schedule.getClassTitle());
        classroomEdit.setText(schedule.getClassPlace());
        professorEdit.setText(schedule.getProfessorName());

        Log.e("test", get_day1 + "");
        daySpinner.setSelection(schedule.getDay());
        daySpinner2.setSelection(schedule2.getDay());

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


        startTv.setText(schedule.getStartTime().getHour() + ":" + schedule.getStartTime().getMinute());
        endTv.setText(schedule.getEndTime().getHour() + ":" + schedule.getEndTime().getMinute());

        startTv2.setText(schedule2.getStartTime().getHour() + ":" + schedule2.getStartTime().getMinute());
        endTv2.setText(schedule2.getEndTime().getHour() + ":" + schedule2.getEndTime().getMinute());

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
        daySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schedule2.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,schedule2.getStartTime().getHour(), schedule2.getStartTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTv2.setText(hourOfDay + ":" + minute);
                    schedule2.getStartTime().setHour(hourOfDay);
                    schedule2.getStartTime().setMinute(minute);
                    String test = schedule2.getStartTime().toString();
                    Log.e(Tag, "log test : " + test);
                }
            };
        });
        endTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,schedule2.getEndTime().getHour(), schedule2.getEndTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTv2.setText(hourOfDay + ":" + minute);
                    schedule2.getEndTime().setHour(hourOfDay);
                    schedule2.getEndTime().setMinute(minute);
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
                }else if(mode == HomeFragment.HOME_REQUEST_EDIT){
                    inputDataProcessing();
                    Intent i = new Intent();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    schedules.add(schedule);
                    i.putExtra("idx",editIdx);
                    i.putExtra("schedules",schedules);
                    setResult(RESULT_OK_EDIT,i);
                    finish();
                }
                else {
                    Log.e("Edit_mode", String.valueOf(mode));
                    inputDataProcessing();
                    // 결과를 Intent에 담아서 HomeFragment로 전달하고 현재 Activity는 종료
                    Intent i = new Intent();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    //you can add more schedules m,to ArrayList
                    schedules.add(schedule);
                    schedules.add(schedule2);
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
        Log.e("loadScheduleData", "loadScheduleData 실행됨");
        Intent i = getIntent();
        // 이 부분때문에 시간표 load 오류남
//        editIdx = i.getIntExtra("idx",-1);
        ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
        schedule = schedules.get(editIdx);
        schedule2 = schedules.get(1);
        Log.e("loadScheduleData", String.valueOf(editIdx));
        Log.d("edit class", "" + schedule.getClassTitle());
        subjectEdit.setText(schedule.getClassTitle());
        classroomEdit.setText(schedule.getClassPlace());
        professorEdit.setText(schedule.getProfessorName());
        daySpinner.setSelection(schedule.getDay());
        daySpinner2.setSelection(schedule2.getDay());
        Log.e("edit class detail", schedule.getClassTitle());
        Log.e("edit class detail", schedule.getProfessorName());
        Log.e("edit class detail", String.valueOf(schedule.getDay()));
        Log.e("edit class detail", String.valueOf(schedule2.getDay()));
        Log.e("edit class detail", schedule2.getProfessorName());
    }

    private void inputDataProcessing(){
        schedule.setClassTitle(subjectEdit.getText().toString());
        schedule.setClassPlace(classroomEdit.getText().toString());
        schedule.setProfessorName(professorEdit.getText().toString());

        schedule2.setClassTitle(subjectEdit.getText().toString());
        schedule2.setClassPlace(classroomEdit.getText().toString());
        schedule2.setProfessorName(professorEdit.getText().toString());
    }

    private String[] timeProcessing(String s){
        String[] test = new String[2];
        String target = ":";
        String result;
        String minute;
        String hour;
        int target_num = s.indexOf(target);
        hour = s.substring(0,target_num);
        minute = s.substring(target_num+1);
        test[0] = hour;
        test[1] = minute;



        return test;
    }
}