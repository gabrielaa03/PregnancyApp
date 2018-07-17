package com.gabrielaangebrandt.pregnancyapp.models.interactors;

import com.gabrielaangebrandt.pregnancyapp.base.BaseInteractor;

public interface MainInteractor extends BaseInteractor{
    void getWorkouts(final WorkoutListener listener) ;

    void getMeals(final MealListener listener);
}
