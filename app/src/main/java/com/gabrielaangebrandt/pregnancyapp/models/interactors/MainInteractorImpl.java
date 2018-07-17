package com.gabrielaangebrandt.pregnancyapp.models.interactors;

import com.gabrielaangebrandt.pregnancyapp.base.BaseInteractorImpl;
import com.gabrielaangebrandt.pregnancyapp.base.RestUtils;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Meal;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.Workout;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainInteractorImpl extends BaseInteractorImpl implements MainInteractor {
    @Override
    public void getMeals(final MealListener listener) {
        addObserver(getMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Meal>>() {
            @Override
            public void onNext(List<Meal> meals) {
                listener.onSuccess(meals);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void getWorkouts(final WorkoutListener listener) {
        addObserver(getWorkouts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Workout>>() {
            @Override
            public void onNext(List<Workout> workouts) {
                listener.onSuccess(workouts);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    private Observable<List<Meal>> getMeals() {
        if (Locale.getDefault().getLanguage().equals("hr"))
            return RestUtils.getAPI().getMealsHr();
        else
            return RestUtils.getAPI().getMeals();
    }

    private Observable<List<Workout>> getWorkouts() {
        if (Locale.getDefault().getLanguage().equals("hr"))
            return RestUtils.getAPI().getWorkoutsHr();
        else
            return RestUtils.getAPI().getWorkouts();
    }
}
