package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class WorkoutViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_description)
    TextView description;

    @BindView(R.id.tv_category)
    TextView category;

    @BindView(R.id.tv_months)
    TextView months;

    WorkoutViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
