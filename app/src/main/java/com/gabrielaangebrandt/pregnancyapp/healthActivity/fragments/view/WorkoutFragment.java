package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.HealthFragmentContract;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.adapters.WorkoutRecyclerAdapter;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.presenter.WorkoutPresenterImpl;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.WorkoutListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WorkoutFragment extends Fragment implements HealthFragmentContract.WorkoutView {
    @BindView(R.id.workouts_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private List<Workout> workoutList = new ArrayList<>();
    private WorkoutRecyclerAdapter adapter;
    private Unbinder unbinder;
    private HealthFragmentContract.WorkoutPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workouts, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new WorkoutRecyclerAdapter(getContext(), workoutList);
        recyclerView.setAdapter(adapter);

        presenter = new WorkoutPresenterImpl(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        presenter.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onStop();
        unbinder.unbind();
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.cannotGetData, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendData(List<Workout> workoutList) {
        this.workoutList.clear();
        this.workoutList.addAll(workoutList);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}
