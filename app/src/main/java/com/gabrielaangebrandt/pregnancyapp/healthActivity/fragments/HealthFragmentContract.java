package com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments;

import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;

import java.util.List;

public interface HealthFragmentContract {

    interface MealView {

        void sendData(List<Meal> meals);

        void showError();
    }

    interface MealPresenter {
        void onStart();

        void onStop();
    }

    interface WorkoutView {

        void showError();

        void sendData(List<Workout> workoutList);
    }

    interface WorkoutPresenter {
        void onStart();

        void onStop();
    }
}
