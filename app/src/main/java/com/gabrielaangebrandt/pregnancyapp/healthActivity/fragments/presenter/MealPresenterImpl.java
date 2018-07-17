package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.presenter;

import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.HealthFragmentContract;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.MainInteractor;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.MainInteractorImpl;
import com.gabrielaangebrandt.pregnancyapp.models.interactors.MealListener;

import java.util.List;

public class MealPresenterImpl implements HealthFragmentContract.MealPresenter, MealListener{
    private HealthFragmentContract.MealView view;
    private MainInteractor interactor;

    public MealPresenterImpl(HealthFragmentContract.MealView view) {
        this.view = view;
        interactor = new MainInteractorImpl();
    }

    @Override
    public void onStart() {
        interactor.getMeals(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSuccess(List<Meal> meals) {
        view.sendData(meals);
    }

    @Override
    public void onError() {
        view.showError();
    }
}
