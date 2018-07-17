package com.gabrielaangebrandt.pregnancyapp.models.interactors;

import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;

import java.util.List;

public interface MealListener {

    void onSuccess(List<Meal> meal);

    void onError();
}
