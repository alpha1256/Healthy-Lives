package com.example.healthylives.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthylives.R;

import java.util.List;

/**
 * workoutAdapter for the recycler view
 */
public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.MyViewHolder> {
    private List<workoutPlan> workoutList;

     public class MyViewHolder extends RecyclerView.ViewHolder{
         public TextView Date, Time, Note;
         public CheckBox check;

         public MyViewHolder(View v)
         {
             super(v);
             Date = (TextView) v.findViewById(R.id.datePlan);
             Time = (TextView) v.findViewById(R.id.timePlan);
             Note = (TextView) v.findViewById(R.id.notePlan);
             check = (CheckBox) v.findViewById(R.id.checkBox);
             check.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (check.isChecked())
                     {
                         removeAt(getAdapterPosition());
                         notifyDataSetChanged();
                     }
                 }
             });
         }
     }
    public workoutAdapter(List<workoutPlan> wlist){workoutList = wlist;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.planner,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        workoutPlan work= workoutList.get(position);
        holder.Date.setText(work.getDate());
        holder.Time.setText(work.getTime());
        holder.Note.setText(work.getNote());
        boolean temp = work.getCheckMark();
        if (temp == false)
            holder.check.setChecked(false);
        else
        {
            holder.check.setChecked(true);
            workoutList.remove(work);
        }
    }

    @Override
    public int getItemCount()
    {
        return workoutList.size();
    }

    /**
     * remove from arraylist and file
     * @param position
     */
    public void removeAt(int position)
    {
        workoutList.remove(position);
    }
}
