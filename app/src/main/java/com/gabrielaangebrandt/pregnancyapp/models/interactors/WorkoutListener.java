package com.gabrielaangebrandt.pregnancyapp.models.interactors;

import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;

import java.util.List;

public interface WorkoutListener {

    void onSuccess(List<Workout> workoutList);

    void onError();
}
