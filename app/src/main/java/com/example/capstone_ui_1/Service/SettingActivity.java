package com.example.capstone_ui_1.Service;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_ui_1.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SettingActivity extends AppCompatActivity {
    ImageView btn_back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button timeplus, btn_save, start, end;
    EditText number, name;
    LinearLayout timelayout1, timelayout2, timelayout3, timelayout4, timelayout5;
    SQLiteDatabase sqlDB;
    MyDBHelper myHelper;
    Integer timesPerDay;
    Integer[] day_array;
    String[] time_array;
    String startday, endday;
    TextView startDate, endDate;
    Integer Y, M, D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        myHelper = new MyDBHelper(this);

        btn_back = (ImageView) findViewById(R.id.btn_back_set);
        btn_save = (Button) findViewById(R.id.btn_save);

        start = (Button) findViewById(R.id.startBtn);
        end = (Button) findViewById(R.id.endBtn);
        name = (EditText) findViewById(R.id.name);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        Y = null;
        M = null;
        D = null;

        startday = null;
        endday = null;

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectingActivity.class);
                startActivity(intent);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SettingActivity.this, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay).show();
            }

            public DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Y = year;
                            M = monthOfYear + 1;
                            D = dayOfMonth;
                            UpdateNow();
                        }
                    };

            void UpdateNow() {
                if(mMonth < 9) {
                    if(mDay < 10) {
                        startDate.setText(String.format(" %d년 0%d월 0%d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-0%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        startDate.setText(String.format(" %d년 0%d월 %d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-0%d-%d", mYear, mMonth + 1, mDay);
                    }
                } else {
                    if(mDay < 10) {
                        startDate.setText(String.format(" %d년 %d월 0%d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        startDate.setText(String.format(" %d년 %d월 %d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                    }
                }
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SettingActivity.this, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay).show();
            }

            public DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            UpdateNow();
                        }
                    };

            void UpdateNow() {
                Toast toast3 = new Toast(SettingActivity.this);
                View toastView3 = (View) View.inflate(SettingActivity.this, R.layout.toast, null);
                TextView toastText3 = (TextView) toastView3.findViewById(R.id.toast1);
                toastText3.setText("잘못된 선택입니다.");
                toast3.setView(toastView3);

                if (mMonth < 9) {
                    if (mYear < Y) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 < M) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 == M && mDay < D) {
                        toast3.show();
                    } else if (mDay < 10) {
                        endDate.setText(String.format(" %d년 0%d월 0%d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-0%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        endDate.setText(String.format(" %d년 0%d월 %d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-0%d-%d", mYear, mMonth + 1, mDay);
                    }
                } else {
                    if (mYear < Y) {
                        toast3.show();
                    } else if (mYear == Y && mMonth  + 1< M) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 == M && mDay < D) {
                        toast3.show();
                    } else if (mDay < 10) {
                        endDate.setText(String.format(" %d년 %d월 0%d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        endDate.setText(String.format(" %d년 %d월 %d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                    }
                }
            }
        });

        final Button[] day = new Button[7];
        final Integer[] dayID = {R.id.mon, R.id.tue, R.id.wed, R.id.thu, R.id.fri, R.id.sat, R.id.sun};
        int i;
        day_array = new Integer[7];
        for (i = 0; i < dayID.length; i++) {
            day[i] = (Button) findViewById(dayID[i]);
            day_array[i] = 0;
        }

        for (i = 0; i < dayID.length; i++) {
            final int index;
            index = i;
            day[index].setOnClickListener(view -> {
                if (day[index].getCurrentTextColor() == Color.BLACK) {
                    day[index].setBackgroundResource(R.drawable.primary_border_fill_4);
                    day[index].setTextColor(Color.WHITE);
                    day_array[index] = 1;
                }
                else{
                    day[index].setBackgroundResource(R.drawable.primary_border_fill3);
                    day[index].setTextColor(Color.BLACK);
                    day_array[index] = 0;
                }
            });
        }

        timeplus = (Button) findViewById(R.id.timeplus);
        registerForContextMenu(timeplus);

        number = (EditText) findViewById(R.id.number);

        timelayout1 = (LinearLayout) findViewById(R.id.timelayout1);
        timelayout2 = (LinearLayout) findViewById(R.id.timelayout2);
        timelayout3 = (LinearLayout) findViewById(R.id.timelayout3);
        timelayout4 = (LinearLayout) findViewById(R.id.timelayout4);
        timelayout5 = (LinearLayout) findViewById(R.id.timelayout5);


        final Button[] timeSet = new Button[5];
        final Integer[] timeSetID = {R.id.modi1, R.id.modi2, R.id.modi3, R.id.modi4, R.id.modi5};
        int j;
        for (j = 0; j < timeSetID.length; j++) {
            timeSet[j] = (Button) findViewById(timeSetID[j]);
        }

        final View[] timePick = new View[5];
        final Integer[] timePickID = {R.layout.timepicker1, R.layout.timepicker2, R.layout.timepicker3, R.layout.timepicker4, R.layout.timepicker5};
        final TimePicker[] times = new TimePicker[5];
        final Integer[] timesID = {R.id.timepicker1, R.id.timepicker2, R.id.timepicker3, R.id.timepicker4, R.id.timepicker5};
        final TextView[] tv = new TextView[5];
        final Integer[] tvID = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5};
        int k;
        for (k = 0; k < timePickID.length; k++) {
            timePick[k] = (View) View.inflate(SettingActivity.this, timePickID[k], null);
            times[k] = (TimePicker) timePick[k].findViewById(timesID[k]);
            tv[k] = (TextView) findViewById(tvID[k]);
        }

        time_array = new String[5];
        for (k = 0; k < 5; k ++) {
            time_array[k] = null;
        }

        for (k = 0; k < timePickID.length; k++) {
            final int index;
            index = k;
            timeSet[index].setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                public void onClick(View view) {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(SettingActivity.this, R.style.DialogTheme);
                    dlg1.setTitle("시간선택");
                    if (timePick[index].getParent() != null) {
                        ((ViewGroup) timePick[index].getParent()).removeView(timePick[index]);
                    }
                    times[index].setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                    dlg1.setView(timePick[index]);
                    dlg1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (times[index].getCurrentHour() < 12) {
                                if(times[index].getCurrentHour() <10){
                                    if(times[index].getCurrentMinute() < 10){
                                        tv[index].setText("오전 " + 0 + Integer.toString(times[index].getCurrentHour()) + " : " +
                                                0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = 0 + Integer.toString(times[index].getCurrentHour()) + ":" +
                                                0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("오전 " + 0 + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = 0 + Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                } else {
                                    if(times[index].getCurrentMinute() < 10){
                                        tv[index].setText("오전 " + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + 0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + 0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("오전 " + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                }
                            } else {
                                if(times[index].getCurrentHour() - 12 < 10){
                                    if(times[index].getCurrentMinute() < 10) {
                                        tv[index].setText("오후 " + 0 + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                                + 0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + 0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("오후 " + 0 + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                } else if(times[index].getCurrentMinute() < 10){
                                    tv[index].setText("오후 " + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                            + 0 + Integer.toString(times[index].getCurrentMinute()));
                                    timeSet[index].setText("수정");
                                    time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                            + 0 + Integer.toString(times[index].getCurrentMinute());
                                } else {
                                    tv[index].setText("오후 " + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                            + Integer.toString(times[index].getCurrentMinute()));
                                    timeSet[index].setText("수정");
                                    time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                            + Integer.toString(times[index].getCurrentMinute());
                                }
                            }
                        }
                    });
                    dlg1.setNegativeButton("취소", null);
                    dlg1.show();
                }
            });

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (name == null || startday == null || endday == null || timesPerDay == null ||
                            (time_array[0] == null && time_array[1] == null && time_array[2] == null &&
                                    time_array[3] == null && time_array[4] == null)
                            || (day_array[0] == 0 && day_array[1] ==0 && day_array[2] ==0 && day_array[3] ==0
                            && day_array[4] ==0 && day_array[5] ==0 && day_array[6] ==0)){
                        Toast toast1 = new Toast(SettingActivity.this);
                        View toastView1 = (View) View.inflate(SettingActivity.this, R.layout.toast, null);
                        TextView toastText1 = (TextView) toastView1.findViewById(R.id.toast1);
                        toastText1.setText("모든 항목을 입력하세요");
                        toast1.setView(toastView1);
                        toast1.show();
                    } else {
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO class (className, startDate, endDate, timesPerDay," +
                                "mon, tue, wed, thu, fri, sat, sun) VALUES ('" +
                                name.getText().toString() + "', '" + startday + "', '" +
                                endday + "', '" + timesPerDay + "', '" +
                                day_array[0] + "','" + day_array[1] + "','" + day_array[2] + "','" +
                                day_array[3] + "','" + day_array[4] + "','" + day_array[5] + "','" +
                                day_array[6] + "');");
                        sqlDB.execSQL("INSERT INTO time (oneTime, twoTime, threeTime, fourTime, fiveTime) VALUES ('" +
                                time_array[0] + "', '" +
                                time_array[1] + "', '" +
                                time_array[2] + "', '" +
                                time_array[3] + "', '" +
                                time_array[4] + "');");
                        sqlDB.close();

                        startService(new Intent(SettingActivity.this, MyService.class));
                        Intent intent = new Intent(getApplicationContext(), SelectingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mInflater = getMenuInflater();
        if (v == timeplus) {
            mInflater.inflate(R.menu.time_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        timesPerDay = null;
        switch (item.getItemId()) {
            case R.id.item1:
                number.setText("1일 1회 설정");
                timesPerDay = 1;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.GONE);
                timelayout3.setVisibility(View.GONE);
                timelayout4.setVisibility(View.GONE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item2:
                number.setText("1일 2회 설정");
                timesPerDay = 2;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.GONE);
                timelayout4.setVisibility(View.GONE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item3:
                number.setText("1일 3회 설정");
                timesPerDay = 3;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.VISIBLE);
                timelayout4.setVisibility(View.GONE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item4:
                number.setText("1일 4회 설정");
                timesPerDay = 4;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.VISIBLE);
                timelayout4.setVisibility(View.VISIBLE);
                timelayout5.setVisibility(View.GONE);
                break;
            case R.id.item5:
                number.setText("1일 5회 설정");
                timesPerDay = 5;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.VISIBLE);
                timelayout3.setVisibility(View.VISIBLE);
                timelayout4.setVisibility(View.VISIBLE);
                timelayout5.setVisibility(View.VISIBLE);
                break;
        }

        return false;
    }
}
