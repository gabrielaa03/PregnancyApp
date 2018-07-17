package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.presenter;

import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.HealthFragmentContract;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.MainInteractor;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.MainInteractorImpl;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.WorkoutListener;

import java.util.List;

public class WorkoutPresenterImpl implements HealthFragmentContract.WorkoutPresenter, WorkoutListener {
    private HealthFragmentContract.WorkoutView view;
    private MainInteractor interactor;
    public WorkoutPresenterImpl(HealthFragmentContract.WorkoutView view) {
        this.view = view;
        interactor = new MainInteractorImpl();
    }

    @Override
    public void onStart() {
        interactor.getWorkouts(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSuccess(List<Workout> workoutList) {
        view.sendData(workoutList);
    }

    @Override
    public void onError() {
        view.showError();
    }
}
