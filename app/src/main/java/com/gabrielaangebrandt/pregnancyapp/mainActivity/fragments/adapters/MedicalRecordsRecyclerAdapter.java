package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.MedicalRecord;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordsRecyclerAdapter extends RecyclerView.Adapter<MedicalRecordsViewHolder> {
    private List<MedicalRecord> records = new ArrayList<>();
    private Context context;

    public MedicalRecordsRecyclerAdapter(Context context, List<MedicalRecord> records) {
        this.records = records;
        this.context = context;
    }

    @Override
    public MedicalRecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_med_record, parent, false);
        return new MedicalRecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicalRecordsViewHolder holder, int position) {
        MedicalRecord item = records.get(position);
        holder.date.setText(item.getDate());
        holder.pregnancyWeek.setText(item.getWeekOfPregnancy());
        holder.weight.setText(item.getWeight());
        holder.rr.setText(item.getRR());
        holder.edema.setText(item.getEdema());
        holder.proteins.setText(item.getProteins());
        holder.glucose.setText(item.getGlocuse());
        holder.leukocytes.setText(item.getLeukocytes());
        holder.kcs.setText(item.getKCS());
        holder.medResults.setText(item.getMedicalResults());
        holder.nextEx.setText(item.getNextEx());
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
}
