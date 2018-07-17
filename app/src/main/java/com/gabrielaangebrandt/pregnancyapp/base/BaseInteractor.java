package com.gabrielaangebrandt.pregnancyapp.base;

import io.reactivex.disposables.Disposable;

public interface BaseInteractor {
    void addObserver(Disposable disposable);

    void disposeCompositeD();
}
