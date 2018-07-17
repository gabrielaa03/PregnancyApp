package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.forumActivity.view.ForumActivity;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.MedicalRecord;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters.MedicalRecordsRecyclerAdapter;
import com.gabrielaangebrandt.pregnancyapp.newExaminationActivity.view.NewExamination;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyMedicalRecordsFragment extends Fragment {

    @BindView(R.id.medicalRecordsRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private MedicalRecordsRecyclerAdapter recyclerViewAdapter;
    private List<MedicalRecord> records = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_medical_records, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            App.getFirebaseDb().getReference("users").child(user.getEmail().replaceAll("\\.", ",")).child("medicalRecords").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        records.add(dataSnapshot1.getValue(MedicalRecord.class));
                        recyclerViewAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(getContext(), R.string.failed, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        recyclerViewAdapter = new MedicalRecordsRecyclerAdapter(getContext(), records);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @OnClick(R.id.fab)
    public void addNew() {
        startActivity(new Intent(getContext(), NewExamination.class).putParcelableArrayListExtra("medRecords", (ArrayList<? extends Parcelable>) records));
    }
}
