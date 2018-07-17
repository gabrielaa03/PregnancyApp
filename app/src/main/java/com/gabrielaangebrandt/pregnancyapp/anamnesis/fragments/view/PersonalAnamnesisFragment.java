package com.gabrielaangebrandt.pregnancyapp.anamnesis.fragments.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.anamnesis.fragments.adapters.PersonalAnamnesisAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonalAnamnesisFragment extends Fragment {

    @BindView(R.id.listView)
    ListView listView;

    private Unbinder unbinder;
    private PersonalAnamnesisAdapter adapter;
    final List<String> databaseItems = new ArrayList<>();

    public static PersonalAnamnesisFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        PersonalAnamnesisFragment fragment = new PersonalAnamnesisFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anamnesis, container, false);
        unbinder = ButterKnife.bind(this, view);

        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            App.getFirebaseDb().getReference("users").child(user.getEmail().replaceAll("\\.", ",")).child("personalAnamnesis").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        databaseItems.add(dataSnapshot1.getValue().toString());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(getContext(), R.string.failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
        adapter = new PersonalAnamnesisAdapter(getContext(),
                android.R.layout.simple_list_item_1, Arrays.asList(getResources().getStringArray(R.array.personalAnamnesis)), databaseItems);
        listView.setAdapter(adapter);
        listView.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_save_anamnesis_data)
    public void saveData() {
        List<String> checkedItems = adapter.saveCheckedData();
        ;
        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            App.getFirebaseDb().getReference("users").child(user.getEmail().replaceAll("\\.", ",")).child("personalAnamnesis").removeValue();
            App.getFirebaseDb().getReference("users").child(user.getEmail().replaceAll("\\.", ",")).child("personalAnamnesis").setValue(checkedItems);
            Toast.makeText(getContext(), R.string.dataSaved, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), R.string.dataCannotBeASaved, Toast.LENGTH_SHORT).show();
    }
}

