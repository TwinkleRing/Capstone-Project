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
import android.widget.LinearLayout;
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
import com.google.android.material.snackbar.Snackbar;
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
    RecyclerView recyclerView = null;
    final RecyclerView.Adapter adapter = null;
    private LinearLayoutManager layoutManager;
    private ArrayList<junkonglist> arrayList;
    private ArrayList<junkonglist> array_get;
    private ArrayList<String> old_array_get;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private boolean lastItemVisibleFlag = false;

    private int page = 0;
    private boolean mLockListView = false;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private Context context;
    private Query query;
    private String stored_grade;
    private Schedule schedule;
    CustomAdapter myadapter = null;
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    myadapter.notifyDataSetChanged();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });
        autoCompleteTextView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = databaseReference.orderByChild("classname").startAt(s).endAt(s + "\uf8ff");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            arrayList.add(0, snapshot.getValue(junkonglist.class));
                        }

                        myadapter = new CustomAdapter(arrayList);
                        recyclerView.setAdapter(myadapter);

                        myadapter.setOnItemClickListener(new OnClassItemClickListener() {
                            @Override
                            public void onItemClick(CustomAdapter.CustomViewHolder holder, View view, int position){
                                junkonglist list = arrayList.get(position);
                                // 시간표에 추가 버튼 클릭시 실행됨
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
                                ;
                                setResult(FIND_OK_CODE, i);
                                // 여기는 전부 아무 문제 없이 실행됨



                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("FindClassActivity", String.valueOf(databaseError.toException()));
                    }
                });



//                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                    @Override
//                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                        super.onScrolled(recyclerView, dx, dy);
//
//                        if(!recyclerView.canScrollVertically(1)){
//                            if(array_get.size()==100){
//                                Log.d(TAG, "LAST POSI");
//                            }
//
//                                databaseReference.orderByKey().startAt(0).endAt(oldestPostId).limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@Nullable DataSnapshot datasnapshot) {
//                                    array_get.clear();
//                                    old_array_get.clear();
//                                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
//                                        array_get.add(0,snapshot.getValue(junkonglist.class));
//                                        old_array_get.add(snapshot.getKey());
//                                    }
//                                    if(array_get.size()>1){
//                                        array_get.remove(0);
//                                        arrayList.addAll(array_get);
//                                        oldestPostId = old_array_get.get(0);
//                                        myadapter.notifyDataSetChanged();
//                                    }
//                                    else {
//                                        Snackbar.make(getWindow().getDecorView().getRootView(), "마지막 이슈입니다.", Snackbar.LENGTH_SHORT)
//                                                .setAction("닫기", new View.OnClickListener() {@Override public void onClick(View view) {}}).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                        super.onScrollStateChanged(recyclerView, newState);
//                    }
//                });


                return true;
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