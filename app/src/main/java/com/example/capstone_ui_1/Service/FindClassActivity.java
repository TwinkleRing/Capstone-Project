package com.example.capstone_ui_1.Service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_ui_1.Adapter.CustomAdapter;
import com.example.capstone_ui_1.Adapter.OnClassItemClickListener;
import com.example.capstone_ui_1.FireDB.junkonglist;
import com.example.capstone_ui_1.HomeFragment;
import com.example.capstone_ui_1.R;
import com.github.tlaabs.timetableview.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class FindClassActivity extends AppCompatActivity{
    private static final String TAG = ".FindClassActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<junkonglist> arrayList;
    private ArrayList<junkonglist> array_get;
    private ArrayList<String> old_array_get;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private boolean lastItemVisibleFlag = false;
    private ProgressBar progressBar;
    private final int OFFSET = 20;
    private int page = 0;
    private boolean mLockListView = false;
    private String oldestPostId;
    private Context context;
    private Query query;
    private String stored_grade;
    private Schedule schedule;
    private CustomAdapter myadapter;
    private androidx.appcompat.widget.SearchView autoCompleteTextView;

    // EditActivity와 통신
    public static final int FIND_OK_CODE = 1000;

    // PopupActivity와 통신
    public static final int FIND_REQUEST_CODE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        array_get = new ArrayList<>();
        old_array_get = new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
        autoCompleteTextView = (androidx.appcompat.widget.SearchView) findViewById(R.id.autoCompleteText);
        databaseReference = database.getReference().child("junkong");
        autoCompleteTextView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                query = databaseReference.orderByChild("classname").equalTo(s);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            arrayList.add(0,snapshot.getValue(junkonglist.class));
                            array_get.add(0,snapshot.getValue(junkonglist.class));
                            old_array_get.add(snapshot.getKey());
                        }
//                oldestPostId = old_array_get.get(0);
                        myadapter = new CustomAdapter(arrayList, context);
                        recyclerView.setAdapter(myadapter);
                        myadapter.setOnItemClickListener(new OnClassItemClickListener() {
                            @Override
                            public void onItemClick(CustomAdapter.CustomViewHolder holder, View view, int position){
                                junkonglist list = arrayList.get(position);
                                // 시간표에 추가 버튼 클릭시 실행됨
                                Log.e("Findclass_list", list.toString());
                                Log.e("Findclass_list_des", list.getClassname());
                                Log.e("Findclass_list_des", list.getClassroom());
                                // 데이터 저장 진행하고
//                                Intent popupIntent = new Intent(getApplicationContext(), PopupActivity.class);
//                                popupIntent.putExtra("classname", list.getClassname());
//                                popupIntent.putExtra("classroom", list.getClassroom());
//                                popupIntent.putExtra("professor", list.getProfessor());
//                                popupIntent.putExtra("time", list.getTime());
//                                popupIntent.putExtra("schedule", schedule);
//                                startActivityForResult(popupIntent, FIND_REQUEST_CODE);
//
                                // 이거로 EditActivity랑 연결은 성립은 됨 우선
                                // 이제 success code를 EditActivity에 돌려줘야 함.
                                // TODO : Popup 안 쓰는 방법 생각해보자
                                // Edit으로 다시 돌려보내기
                                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                                i.putExtra("classname", list.getClassname());
                                i.putExtra("classroom", list.getClassroom());
                                i.putExtra("professor", list.getProfessor());

                                i.putExtra("day1", list.getDay_1());
                                i.putExtra("day1_start_time", list.getDay1_start_time());
                                i.putExtra("day1_end_time", list.getDay1_end_time());

                                i.putExtra("day2", list.getDay_2());
                                i.putExtra("day2_start_time", list.getDay2_start_time());
                                i.putExtra("day2_end_time", list.getDay2_end_time());



                                i.putExtra("schedule", schedule);
                                Log.e("find_intent", (String) i.getExtras().get("classname"));
                                Log.e("find_intent", (String) i.getExtras().get("professor"));
                                Log.e("find_intent", "" + (list.getDay_1()));
                                setResult(FIND_OK_CODE, i);
                                // 여기는 전부 아무 문제 없이 실행됨

                                Log.e("Find_startActivity", "find에서 setResult 실행됨");
                                Log.e("Find_startActivity", String.valueOf(FIND_OK_CODE));

                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("FindClassActivity", String.valueOf(databaseError.toException()));
                    }
                });

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if(!recyclerView.canScrollVertically(1)){
                            if(array_get.size()==10){
                                Log.d(TAG, "LAST POSI");
                            }

                            databaseReference.orderByKey().endAt(oldestPostId).limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@Nullable DataSnapshot datasnapshot) {
                                    array_get.clear();
                                    old_array_get.clear();
                                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                                        array_get.add(0,snapshot.getValue(junkonglist.class));
                                        old_array_get.add(snapshot.getKey());
                                    }
                                    if(array_get.size()>1){
                                        array_get.remove(0);
                                        arrayList.addAll(array_get);
                                        oldestPostId = old_array_get.get(0);
                                        myadapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    arrayList.add(0,snapshot.getValue(junkonglist.class));
//                    array_get.add(0,snapshot.getValue(junkonglist.class));
//                    old_array_get.add(snapshot.getKey());
//                }
////                oldestPostId = old_array_get.get(0);
//                adapter = new CustomAdapter(arrayList, context);
//
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("FindClassActivity", String.valueOf(databaseError.toException()));
//            }
//        });
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(!recyclerView.canScrollVertically(1)){
//                    if(array_get.size()==10){
//                        Log.d(TAG, "LAST POSI");
//                    }
//
//                    databaseReference.orderByKey().endAt(oldestPostId).limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@Nullable DataSnapshot datasnapshot) {
//                            array_get.clear();
//                            old_array_get.clear();
//                            for(DataSnapshot snapshot : datasnapshot.getChildren()){
//                                array_get.add(0,snapshot.getValue(junkonglist.class));
//                                old_array_get.add(snapshot.getKey());
//                            }
//                            if(array_get.size()>1){
//                                array_get.remove(0);
//                                arrayList.addAll(array_get);
//                                oldestPostId = old_array_get.get(0);
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("FindClass_onActResult", "onActivityResult 실행됨");
        if (resultCode == PopupActivity.POP_REQUEST_ADD) {
            switch (requestCode) {
                case FIND_REQUEST_CODE:
                    Intent i = new Intent();
                    i.putExtra("result", "done");
                    break;
            }
        }
    }
}