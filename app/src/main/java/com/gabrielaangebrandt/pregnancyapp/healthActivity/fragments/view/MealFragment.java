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
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.adapters.MealRecyclerAdapter;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.presenter.MealPresenterImpl;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MealFragment extends Fragment implements HealthFragmentContract.MealView{
    @BindView(R.id.meals_recycler_view)
    RecyclerView mealsRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private List<Meal> mealList = new ArrayList<>();
    private MealRecyclerAdapter adapter;
    private Unbinder unbinder;
    private HealthFragmentContract.MealPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new MealRecyclerAdapter(getContext(), mealList);
        mealsRecyclerView.setAdapter(adapter);

        presenter = new MealPresenterImpl(this);
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
    public void sendData(List<Meal> meals) {
        mealList.clear();
        mealList.addAll(meals);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.cannotGetData, Toast.LENGTH_SHORT).show();
    }
}
