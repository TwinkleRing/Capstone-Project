package com.example.capstone_ui_1.Service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_ui_1.Adapter.CustomAdapter;
import com.example.capstone_ui_1.FireDB.junkonglist;
import com.example.capstone_ui_1.R;
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
    private androidx.appcompat.widget.SearchView autoCompleteTextView;
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
//        stored_grade = "2학년";
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
                        adapter = new CustomAdapter(arrayList, context);

                        recyclerView.setAdapter(adapter);

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
                                        adapter.notifyDataSetChanged();
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

}