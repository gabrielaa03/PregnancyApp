package com.gabrielaangebrandt.pregnancyapp.anamnesis.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;

import java.util.ArrayList;
import java.util.List;

public class FamilyAnamnesisAdapter extends ArrayAdapter<String> {
    private List<String> listItems = new ArrayList<>();
    private List<String> databaseItems = new ArrayList<>();
    private Context context;

    public FamilyAnamnesisAdapter(@NonNull Context context, int resource, List<String> list, List<String> databaseItems) {
        super(context, resource);
        this.context = context;
        this.listItems = list;
        this.databaseItems = databaseItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_anamnesis, parent, false);

        final CheckBox checkBox = rowView.findViewById(R.id.checkbox);
        final TextView personalData = rowView.findViewById(R.id.tv_personal_data);

        String item = listItems.get(position);
        if (databaseItems.contains(item)) {
            checkBox.setChecked(true);
        }

        personalData.setText(item);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox.setChecked(true);
                    if (!databaseItems.contains(personalData.getText().toString())) {
                        databaseItems.add(personalData.getText().toString());
                    }
                } else {
                    checkBox.setChecked(false);
                    if (databaseItems.contains(personalData.getText().toString())) {
                        databaseItems.remove(personalData.getText().toString());
                    }
                }
            }
        });
        return rowView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    public List<String> saveCheckedData() {
        return databaseItems;
    }
}
