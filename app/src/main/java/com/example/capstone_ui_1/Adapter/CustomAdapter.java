package com.example.capstone_ui_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_ui_1.FireDB.junkonglist;
import com.example.capstone_ui_1.R;
import com.example.capstone_ui_1.Service.EditActivity;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_EDIT = 2;
    public static final int RESULT_OK_DELETE = 3;
    private ArrayList<junkonglist> arrayList;
    private Context context;
    private Schedule schedule;
    private Toast toast;
    OnClassItemClickListener listener;
    public CustomAdapter(ArrayList<junkonglist> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
        schedule = new Schedule();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.junkong_listview,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.layout_classname.setText(arrayList.get(position).getClassname());
        holder.layout_major.setText(arrayList.get(position).getMajor());
        holder.layout_point.setText(arrayList.get(position).getPoint());
        holder.layout_professor.setText(arrayList.get(position).getProfessor());
        holder.layout_grade.setText(arrayList.get(position).getGrade());
        holder.layout_time.setText(arrayList.get(position).getTime());
        holder.layout_classroom.setText(arrayList.get(position).getClassroom());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public void setOnItemClickListener(OnClassItemClickListener listener) {
        this.listener = listener;
    }


    public void onItemClick(CustomViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder,view,position); }
    }

//    public junkonglist getlist(int position){
//        return
//    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView layout_classroom;
        TextView layout_time;
        TextView layout_professor;
        TextView layout_grade;
        TextView layout_classname;
        TextView layout_major;
        TextView layout_point;
        Button layout_legister;
        public CustomViewHolder(@NonNull View itemView, final CustomAdapter listener) {
            super(itemView);
            this.layout_classroom = itemView.findViewById(R.id.layout_classroom);
            this.layout_classname = itemView.findViewById(R.id.layout_classname);
            this.layout_major = itemView.findViewById(R.id.layout_major);
            this.layout_point = itemView.findViewById(R.id.layout_point);
            this.layout_grade = itemView.findViewById(R.id.layout_grade);
            this.layout_professor = itemView.findViewById(R.id.layout_professor);
            this.layout_time = itemView.findViewById(R.id.layout_time);
            this.layout_legister = itemView.findViewById(R.id.layout_legister);
            layout_legister.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CustomViewHolder.this, v, position);
                    }
                }
            });
        }
    }
//    private void inputDataProcessing(TextView a, TextView b, TextView c){
//        schedule.setClassTitle(a.getText().toString());
//        schedule.setClassPlace(b.getText().toString());
//        schedule.setProfessorName(c.getText().toString());
//    }

}
