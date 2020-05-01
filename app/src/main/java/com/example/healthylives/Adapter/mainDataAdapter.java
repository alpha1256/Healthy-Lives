package com.example.healthylives.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthylives.R;

import java.util.List;

public class mainDataAdapter extends RecyclerView.Adapter<mainDataAdapter.Viewholder> {
    private List <Day> allDatalist;

    public class Viewholder extends RecyclerView.ViewHolder
    {
        public TextView Date, activeMin, Sleep, Steps, waterCount;

        public Viewholder (View v)
        {
            super(v);
            Date = (TextView) v.findViewById(R.id.dateData);
            activeMin = (TextView) v.findViewById(R.id.activeData);
            Sleep = (TextView) v.findViewById(R.id.sleepData);
            Steps = (TextView) v.findViewById(R.id.stepsData);
            waterCount = (TextView) v.findViewById(R.id.waterData);
        }
    }

    public mainDataAdapter(List <Day> tlist){allDatalist = tlist;}

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewtype)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_layout,parent,false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder (Viewholder holder, int position)
    {
        Day temp = allDatalist.get(position);
        holder.Date.setText(temp.getDate());
        holder.activeMin.setText("You were active for" + temp.getMin());
        holder.Steps.setText("Steps: " + temp.getSteps());
        holder.waterCount.setText("You had " + temp.getCups() + " cups of water");
        holder.Sleep.setText("You were asleep for "+ temp.getSleep() + " minutes");
    }

    @Override
    public int getItemCount()
    {
        return allDatalist.size();
    }
}
