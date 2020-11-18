package com.example.capstone_ui_1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_ui_1.R;
import com.example.capstone_ui_1.Service.ListItem;
import com.example.capstone_ui_1.Service.ModifyActivity;
import com.example.capstone_ui_1.Service.MyDBHelper;

import java.util.ArrayList;

public class ListItemRecyclerViewAdapter extends RecyclerView.Adapter<ListItemRecyclerViewAdapter.ListItemRecyclerViewHolder> {
    private ArrayList<ListItem> sList;
    private LayoutInflater sInflate;
    private Context sContext;
    MyDBHelper myHelper;

    public ListItemRecyclerViewAdapter(ArrayList<ListItem> sList, Context sContext) {
        this.sList = sList;
        this.sInflate = LayoutInflater.from(sContext);
        this.sContext = sContext;
    }

    @NonNull
    @Override
    public ListItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = sInflate.inflate(R.layout.rv_item_list, viewGroup, false);
        ListItemRecyclerViewHolder viewHolder = new ListItemRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemRecyclerViewHolder holder, final int i) {
        holder.className.setText(sList.get(i).getClassName());
        holder.startDate.setText(sList.get(i).getStartDate());
        holder.endDate.setText(sList.get(i).getEndDate());
        if (sList.get(i).getMon() == 1) {
            holder.day_mon.setText("월");
        } else {
            holder.day_mon.setText("");
        }

        if (sList.get(i).getTue() == 1) {
            holder.day_tue.setText("화");
        } else {
            holder.day_tue.setText("");
        }

        if (sList.get(i).getWed() == 1) {
            holder.day_wed.setText("수");
        } else {
            holder.day_wed.setText("");
        }

        if (sList.get(i).getTue() == 1) {
            holder.day_thu.setText("목");
        } else {
            holder.day_thu.setText("");
        }

        if (sList.get(i).getFri() == 1) {
            holder.day_fri.setText("금");
        } else {
            holder.day_fri.setText("");
        }

        if (sList.get(i).getSat() == 1) {
            holder.day_sat.setText("토");
        } else {
            holder.day_sat.setText("");
        }

        if (sList.get(i).getSun() == 1) {
            holder.day_sun.setText("일");
        } else {
            holder.day_sun.setText(" ");
        }
        holder.timesPerDay.setText(String.valueOf(sList.get(i).getTimesPerDay()));
        holder.lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(sContext, R.style.MyAlertDialogStyle);
                dlg.setTitle("다음 활동");
                dlg.setMessage("수행할 일을 선택하세요");
                dlg.setPositiveButton("강의 설정 수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(sContext, ModifyActivity.class);
                        intent.putExtra("className", sList.get(i).getClassName());
                        intent.putExtra("startDate", sList.get(i).getStartDate());
                        intent.putExtra("endDate", sList.get(i).getEndDate());
                        intent.putExtra("timesPerDay", sList.get(i).getTimesPerDay());
                        intent.putExtra("mon", sList.get(i).getMon());
                        intent.putExtra("tue", sList.get(i).getTue());
                        intent.putExtra("wed", sList.get(i).getWed());
                        intent.putExtra("thu", sList.get(i).getThu());
                        intent.putExtra("fri", sList.get(i).getFri());
                        intent.putExtra("sat", sList.get(i).getSat());
                        intent.putExtra("sun", sList.get(i).getSun());
                        intent.putExtra("oneTime", sList.get(i).getOneTime());
                        intent.putExtra("twoTime", sList.get(i).getTwoTime());
                        intent.putExtra("threeTime", sList.get(i).getThreeTime());
                        intent.putExtra("fourTime", sList.get(i).getFourTime());
                        intent.putExtra("fiveTime", sList.get(i).getFiveTime());
                        intent.putExtra("classId", sList.get(i).getClassId());
                        sContext.startActivity(intent);
                    }
                });
                dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper = new MyDBHelper(sContext);
                        myHelper.deleteItem(sList.get(i).getClassId());
                        sList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, sList.size());
                    }
                });
                dlg.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class ListItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView className, startDate, endDate, day_mon, day_tue, day_wed, day_thu, day_fri, day_sat, day_sun, timesPerDay;
        public LinearLayout lay1;

        public ListItemRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.txt_rv_item_list_className);
            startDate = itemView.findViewById(R.id.txt_rv_item_list_startDate);
            endDate = itemView.findViewById(R.id.txt_rv_item_list_endDate);
            day_mon = itemView.findViewById(R.id.txt_rv_item_list_mon);
            day_tue = itemView.findViewById(R.id.txt_rv_item_list_tue);
            day_wed = itemView.findViewById(R.id.txt_rv_item_list_wed);
            day_thu = itemView.findViewById(R.id.txt_rv_item_list_thu);
            day_fri = itemView.findViewById(R.id.txt_rv_item_list_fri);
            day_sat = itemView.findViewById(R.id.txt_rv_item_list_sat);
            day_sun = itemView.findViewById(R.id.txt_rv_item_list_sun);
            timesPerDay = itemView.findViewById(R.id.txt_rv_item_list_timesPerDay);
            lay1 = itemView.findViewById(R.id.lay1);
        }
    }
}
