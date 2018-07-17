package com.gabrielaangebrandt.pregnancyapp.base;

import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestInterface {

    @GET("meals")
    Observable<List<Meal>> getMeals();

    @GET("mealshr")
    Observable<List<Meal>> getMealsHr();

    @GET("workouts")
    Observable<List<Workout>> getWorkouts();

    @GET("workoutshr")
    Observable<List<Workout>> getWorkoutsHr();
}
