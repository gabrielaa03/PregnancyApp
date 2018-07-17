package com.gabrielaangebrandt.pregnancyapp;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class App extends Application {
    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static App getAppContext() {
        return context;
    }

    public static FirebaseDatabase getFirebaseDb() {
        return FirebaseDatabase.getInstance();
    }

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseStorage getFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }
}
