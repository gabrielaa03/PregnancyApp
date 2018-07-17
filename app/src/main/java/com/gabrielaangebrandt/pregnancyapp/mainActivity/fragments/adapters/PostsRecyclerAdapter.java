package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.MedicalRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class PostsRecyclerAdapter extends RecyclerView.Adapter<MedicalRecordsViewHolder> {
    private List<MedicalRecord> records = new ArrayList<>();

    @Override
    public MedicalRecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_med_record, parent, false);
        return new MedicalRecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicalRecordsViewHolder holder, int position) {
        MedicalRecord item = records.get(position);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void addData(List<MedicalRecord> records) {
        this.records.clear();
        this.records.addAll(records);
        notifyDataSetChanged();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {
        PostsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

