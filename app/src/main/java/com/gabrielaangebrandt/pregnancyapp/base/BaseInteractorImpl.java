package com.gabrielaangebrandt.pregnancyapp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseInteractorImpl implements BaseInteractor {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void addObserver(Disposable disposable) {
        if (compositeDisposable == null || compositeDisposable.isDisposed())
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    @Override
    public void disposeCompositeD() {
        compositeDisposable.dispose();
    }
}
