package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealRecyclerAdapter extends RecyclerView.Adapter<MealViewHolder> {

    private List<Meal> mealList = new ArrayList<>();
    private Context context;

    public MealRecyclerAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.title.setText(meal.getTitle());
        holder.description.setText(meal.getDescrtiption());
        holder.category.setText(meal.getCategory());
        holder.preparationTime.setText(meal.getTimeForPreparation());

        switch (meal.getCategory()) {
            case "Breakfast":
                holder.category.setTextColor(context.getResources().getColor(R.color.lightGreen));
                break;
            case "Lunch":
                holder.category.setTextColor(context.getResources().getColor(R.color.blue));
                break;
            case "Dinner":
                holder.category.setTextColor(context.getResources().getColor(R.color.brown));
                break;
            case "Snack":
                holder.category.setTextColor(context.getResources().getColor(R.color.yellow));
                break;
            case "Doručak":
                holder.category.setTextColor(context.getResources().getColor(R.color.lightGreen));
                break;
            case "Ručak":
                holder.category.setTextColor(context.getResources().getColor(R.color.blue));
                break;
            case "Večera":
                holder.category.setTextColor(context.getResources().getColor(R.color.brown));
                break;
            case "Međuobrok":
                holder.category.setTextColor(context.getResources().getColor(R.color.yellow));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }
}
