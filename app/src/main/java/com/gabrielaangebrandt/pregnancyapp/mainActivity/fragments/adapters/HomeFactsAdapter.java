package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFactsAdapter extends RecyclerView.Adapter<HomeFactsAdapter.PersonalAnamnesisViewHolder> {
    private List<String> listOfPersonalAnamnesisItems = new ArrayList<>();
    private Context context;

    public HomeFactsAdapter(List<String> listOfPersonalAnamnesisItems, Context context) {
        this.listOfPersonalAnamnesisItems = listOfPersonalAnamnesisItems;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonalAnamnesisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_facts, parent, false);
        return new PersonalAnamnesisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalAnamnesisViewHolder holder, int position) {
        String item = listOfPersonalAnamnesisItems.get(position);
        holder.personalData.setText(item);
    }

    @Override
    public int getItemCount() {
        return listOfPersonalAnamnesisItems.size();
    }

    class PersonalAnamnesisViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_personal_data)
        TextView personalData;

        PersonalAnamnesisViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
