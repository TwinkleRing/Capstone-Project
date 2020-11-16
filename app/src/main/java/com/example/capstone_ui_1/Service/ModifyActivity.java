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

public class ModifyActivity extends AppCompatActivity {
    ImageView btn_back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button timeplus, btn_modify, start, end;
    EditText number, name;
    LinearLayout timelayout1, timelayout2, timelayout3, timelayout4, timelayout5;
    SQLiteDatabase sqlDB;
    MyDBHelper myHelper;
    Integer timesPerDay;
    Integer[] day_array;
    String[] time_array;
    String startday, endday;
    TextView[] tv = new TextView[5];
    Integer Y, M, D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        myHelper = new MyDBHelper(this);

        Intent intent = getIntent();
        String mName = intent.getStringExtra("className");
        String mStart = intent.getStringExtra("startDate");
        String mEnd = intent.getStringExtra("endDate");

        final int ClassId = intent.getIntExtra("classId", 0);

        final int iMon = intent.getIntExtra("mon", 0);
        final int iTue = intent.getIntExtra("tue", 0);
        final int iWed = intent.getIntExtra("wed", 0);
        final int iThu = intent.getIntExtra("thu", 0);
        final int iFri = intent.getIntExtra("fri", 0);
        final int iSat = intent.getIntExtra("sat", 0);
        final int iSun = intent.getIntExtra("sun", 0);

        final int mTimes = intent.getIntExtra("timesPerDay", 0);
        String one = intent.getStringExtra("oneTime");
        String two = intent.getStringExtra("twoTime");
        String three = intent.getStringExtra("threeTime");
        String four = intent.getStringExtra("fourTime");
        String five = intent.getStringExtra("fiveTime");

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_modify = (Button) findViewById(R.id.btn_modify);
        start = (Button) findViewById(R.id.startBtn);
        end = (Button) findViewById(R.id.endBtn);

        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);

        final TextView startDate = (TextView) findViewById(R.id.startDate);
        final TextView endDate = (TextView) findViewById(R.id.endDate);

        startDate.setText(" " + mStart.substring(0,4) + "년 " + mStart.substring(5, 7) +"월 " +
                mStart.substring(8, 10) + "일");
        endDate.setText(" " + mEnd.substring(0, 4) + "년 " + mEnd.substring(5, 7) +"월 " +
                mEnd.substring(8, 10) + "일");

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        startday = mStart;
        endday = mEnd;

        Y = Integer.parseInt(mStart.substring(0,4));
        M = Integer.parseInt(mStart.substring(5, 7));
        D = Integer.parseInt(mStart.substring(8, 10));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ModifyActivity.this, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay).show();
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
                new DatePickerDialog(ModifyActivity.this, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay).show();
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
                Toast toast3 = new Toast(ModifyActivity.this);
                View toastView3 = (View) View.inflate(ModifyActivity.this, R.layout.toast, null);
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

        for(i = 0; i < dayID.length; i++) {
            day[i] = (Button) findViewById(dayID[i]);
        }

        for(i = 0; i < 7; i++) {
            day_array[i] = 0;
        }

        day_array[0] = iMon;
        day_array[1] = iTue;
        day_array[2] = iWed;
        day_array[3] = iThu;
        day_array[4] = iFri;
        day_array[5] = iSat;
        day_array[6] = iSun;

        for (i = 0; i < 7; i++) {
            if (day_array[i] == 1) {
                day[i].setBackgroundResource(R.drawable.primary_border_fill_4);
                day[i].setTextColor(Color.WHITE);
            }
        }

        for(i = 0; i < dayID.length; i++) {
            final int index;
            index = i;
            day[index].setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                public void onClick(View view) {
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
                }
            });
        }

        timeplus = (Button) findViewById(R.id.timeplus);
        registerForContextMenu(timeplus);

        timelayout1 = (LinearLayout) findViewById(R.id.timelayout1);


        final Button[] timeSet = new Button[5];
        final Integer[] timeSetID = {R.id.modi1, R.id.modi2, R.id.modi3, R.id.modi4, R.id.modi5};
        int j;
        for(j = 0; j < timeSetID.length; j++) {
            timeSet[j] = (Button) findViewById(timeSetID[j]);
        }

        final View[] timePick = new View[5];
        final Integer[] timePickID = {R.layout.timepicker1};
        final TimePicker[] times = new TimePicker[5];
        final Integer[] timesID = {R.id.timepicker1};

        final Integer[] tvID = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5};
        int k;
        for (k = 0; k < timePickID.length; k++) {
            timePick[k] = (View) View.inflate(ModifyActivity.this, timePickID[k], null);
            times[k] = (TimePicker) timePick[k].findViewById(timesID[k]);
            tv[k] = (TextView) findViewById(tvID[k]);
        }

        time_array = new String[5];
        for(k = 0; k < 5; k ++) {
            time_array[k] = null;
        }

        time_array[0] = one;
        time_array[1] = two;
        time_array[2]= three;
        time_array[3] = four;
        time_array[4] = five;

        LinearLayout[] timelayout = {timelayout1, timelayout2, timelayout3, timelayout4, timelayout5};
        for(int o = 0; o < mTimes; o++){
            if(Integer.parseInt(time_array[o].substring(0, 2)) < 12){
                tv[o].setText("오전 " + time_array[o].substring(0,2) + " : "
                        + time_array[o].substring(3, 5));
                timelayout[o].setVisibility(View.VISIBLE);
            } else if(Integer.parseInt(time_array[o].substring(0, 2)) > 12){
                int n = Integer.parseInt(time_array[o].substring(0,2));
                if(n - 12 <10) {
                    tv[o].setText("오후 0" + Integer.toString(n - 12) + " : " + time_array[o].substring(3,5));
                    timelayout[o].setVisibility(View.VISIBLE);
                } else{
                    tv[o].setText("오후 " + Integer.toString(n - 12) + " : " + time_array[o].substring(3,5));
                    timelayout[o].setVisibility(View.VISIBLE);
                }
            } else {
                tv[o].setText("오후 " + time_array[o].substring(0,2) + " : " + time_array[o].substring(3, 5));
                timelayout[o].setVisibility(View.VISIBLE);
            }
        }

        for(k = 0; k < timePickID.length; k++) {
            final int index;
            index = k;
            timeSet[index].setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                public void onClick(View view) {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(ModifyActivity.this, R.style.DialogTheme);
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
        }

        name.setText(mName);
        number.setText("1일 " + mTimes + "회 복용");

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == null || startday == null || endday == null ||
                        (time_array[0] == null && time_array[1] == null && time_array[2] == null &&
                                time_array[3] == null && time_array[4] == null)
                        || (day_array[0] == 0 && day_array[1] == 0 && day_array[2] == 0 && day_array[3] == 0
                        && day_array[4] == 0 && day_array[5] == 0 && day_array[6] == 0)) {
                    Toast toast1 = new Toast(ModifyActivity.this);
                    View toastView1 = (View) View.inflate(ModifyActivity.this, R.layout.toast, null);
                    TextView toastText1 = (TextView) toastView1.findViewById(R.id.toast1);
                    toastText1.setText("모든 항목을 입력하세요");
                    toast1.setView(toastView1);
                    toast1.show();
                } else if (timesPerDay == null) {
                    timesPerDay = mTimes;
                } else {
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("UPDATE class SET className = '" + name.getText().toString() + "', " +
                            "startDate = '" + startday + "', endDate = '" + endday + "', " +
                            "timesPerDay = " + timesPerDay + ", mon = " + day_array[0] + ", " +
                            "tue = " + day_array[1] + ", wed = " + day_array[2] + ", " +
                            "thu = " + day_array[3] + ", fri = " + day_array[4] + ", " +
                            "sat = " + day_array[5] + ", sun = " + day_array[6] + " WHERE classId = " + ClassId + ";");

                    sqlDB.execSQL("UPDATE time SET oneTime = '" + time_array[0] + "', " +
                            "twoTime = '" + time_array[1] + "', threeTime = '" + time_array[2] + "', " +
                            "fourTime = '" + time_array[3] + "', fiveTime = '" + time_array[4] + "' " +
                            "WHERE timeId = " + ClassId + ";");
                    sqlDB.close();

                    startService(new Intent(ModifyActivity.this, MyService.class));
                    Intent intent = new Intent(getApplicationContext(), SelectingActivity.class);
                    startActivity(intent);
                }
            }
        });
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
                number.setText("1일 1회 복용");
                timesPerDay = 1;
                timelayout1.setVisibility(View.VISIBLE);
                timelayout2.setVisibility(View.GONE);
                time_array[1] = null;
                timelayout3.setVisibility(View.GONE);
                time_array[2] = null;
                timelayout4.setVisibility(View.GONE);
                time_array[3] = null;
                timelayout5.setVisibility(View.GONE);
                time_array[4] = null;
                break;
        }

        return false;
    }
}
