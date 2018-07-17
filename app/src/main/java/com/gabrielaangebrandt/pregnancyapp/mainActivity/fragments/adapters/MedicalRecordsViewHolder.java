package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class MedicalRecordsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_custom_date)
    TextView date;
    @BindView(R.id.tv_custom_edema)
    TextView edema;
    @BindView(R.id.tv_custom_glucose)
    TextView glucose;
    @BindView(R.id.tv_custom_kcs)
    TextView kcs;
    @BindView(R.id.tv_custom_rr)
    TextView rr;
    @BindView(R.id.tv_custom_next_ex)
    TextView nextEx;
    @BindView(R.id.tv_custom_leukocytes)
    TextView leukocytes;
    @BindView(R.id.tv_custom_medical_results)
    TextView medResults;
    @BindView(R.id.tv_custom_proteins)
    TextView proteins;
    @BindView(R.id.tv_custom_weight)
    TextView weight;
    @BindView(R.id.tv_custom_pregnancy_week)
    TextView pregnancyWeek;

    MedicalRecordsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
