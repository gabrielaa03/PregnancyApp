package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRecyclerAdapter extends RecyclerView.Adapter<WorkoutViewHolder> {

    private List<Workout> workoutList = new ArrayList<>();
    private Context context;

    public WorkoutRecyclerAdapter(Context context, List<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout item = workoutList.get(position);
        holder.description.setMovementMethod(new ScrollingMovementMethod());
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.category.setText(item.getCategory());
        holder.months.setText(item.getMonths());

        switch (item.getCategory()) {
            case "Hard":
                holder.category.setTextColor(context.getResources().getColor(R.color.red));
                holder.category.setText(context.getResources().getString(R.string.hard));
                break;
            case "Normal":
                holder.category.setTextColor(context.getResources().getColor(R.color.yellow));
                holder.category.setText(context.getResources().getString(R.string.normal));
                break;
            case "Easy":
                holder.category.setTextColor(context.getResources().getColor(R.color.lightGreen));
                holder.category.setText(context.getResources().getString(R.string.easy));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
