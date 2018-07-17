package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_category)
    TextView category;

    @BindView(R.id.tv_descr)
    TextView description;

    @BindView(R.id.tv_preparation_time)
    TextView preparationTime;

    MealViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
