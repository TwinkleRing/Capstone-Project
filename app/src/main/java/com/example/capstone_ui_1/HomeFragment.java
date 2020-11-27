package com.example.capstone_ui_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.capstone_ui_1.Service.FindClassActivity;
import com.example.capstone_ui_1.Service.PopupActivity;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.example.capstone_ui_1.Service.EditActivity;
import com.example.capstone_ui_1.R;

import java.util.ArrayList;

import static android.view.ViewGroup.*;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context;
    public static final int HOME_REQUEST_ADD = 1;
    public static final int HOME_REQUEST_EDIT = 2;

    private Button addBtn;
    private Button clearBtn;
    private Button saveBtn;
    private Button loadBtn;

    private  TimetableView timetable;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);
        init(view);
        return view;
    }



    private void init(View v){
        addBtn = (Button) v.findViewById(R.id.add_btn);
        clearBtn = (Button) v.findViewById(R.id.clear_btn);
        saveBtn = (Button) v.findViewById(R.id.save_btn);
        loadBtn = (Button) v.findViewById(R.id.load_btn);

        timetable = (TimetableView) v.findViewById(R.id.timetable);
//        timetable.setHeaderHighlight(2);
        initView();
    }

    private void initView(){
        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(getView().getContext(), EditActivity.class);
                i.putExtra("mode",HOME_REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i, HOME_REQUEST_EDIT);
                Log.e("Home_timetable", "HomeFragment에서 timetable 클릭됨");
            }
        });


        // 11/17 추가
//        timetable.setOnStickerSelectEventListener((idx, schedules) -> {
//            Intent j = new Intent(getView().getContext(), FindClassActivity.class);
//            j.putExtra("mode",REQUEST_EDIT);
//            j.putExtra("idx", idx);
//            j.putExtra("schedules", schedules);
//            startActivityForResult(j,REQUEST_EDIT);
//        });
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                Intent i = new Intent(getActivity(),EditActivity.class);
                i.putExtra("mode", HOME_REQUEST_ADD);
                Log.e("Log test", "HomeFragment에서 added");
                startActivityForResult(i, HOME_REQUEST_ADD); // 여기는 문제 없음
                // 11/17 추가
//                Intent j = new Intent(getActivity(),FindClassActivity.class);
//                j.putExtra("mode",REQUEST_ADD);
//                startActivityForResult(j,REQUEST_ADD);
                break;
            case R.id.clear_btn:
                timetable.removeAll();
                break;
            case R.id.save_btn:
                saveByPreference(timetable.createSaveData());
                break;
            case R.id.load_btn:
                loadSavedData();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    // EditActivity에서 처리된 결과를 받는 메소드
    // 처리된 결과 (resultCode)가 EditActivity.RESULT_OK_ADD이면 requestCode를 판별해 결과 처리를 진행
    // 처리 결과를 item으로 선언해서 timetable에 더한다.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Home_onActivityResult", "onActivityResult 실행됨");
        switch (requestCode){
            case HOME_REQUEST_ADD:
                if(resultCode == EditActivity.EDIT_OK_ADD){
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    Log.e("HomeFragment_resultCode", String.valueOf(resultCode));
                    timetable.add(item);
                }
                break;
            case HOME_REQUEST_EDIT:
                /** Edit -> Submit */
                if(resultCode == EditActivity.EDIT_OK_EDIT){
                    int idx = data.getIntExtra("idx",-1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetable.edit(idx,item);
                }
                /** Edit -> Delete */
                else if(resultCode == EditActivity.EDIT_OK_DELETE){
                    int idx = data.getIntExtra("idx",-1);
                    timetable.remove(idx);
                }
                break;
        }
    }

    /** save timetableView's data to SharedPreferences in json format */
    private void saveByPreference(String data){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("timetable_demo",data);
        editor.commit();
        Toast.makeText(getActivity(),"saved!",Toast.LENGTH_SHORT).show();
    }

    /** get json data from SharedPreferences and then restore the timetable */
    private void loadSavedData(){
        timetable.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String savedData = mPref.getString("timetable_demo","");
        if(savedData == null && savedData.equals("")) return;
        timetable.load(savedData);
        Toast.makeText(getActivity(),"loaded!",Toast.LENGTH_SHORT).show();
    }
}
