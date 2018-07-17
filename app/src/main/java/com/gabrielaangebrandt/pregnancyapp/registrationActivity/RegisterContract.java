package com.gabrielaangebrandt.pregnancyapp.registrationActivity;

import android.content.Context;

import com.gabrielaangebrandt.pregnancyapp.models.data_models.User;
import com.google.firebase.auth.FirebaseAuth;

public interface RegisterContract {

    interface RegisterView {
        void showErrorToast(String error);

        void startMainActivity();

        void saveUserInformationsToDB(User user);
    }

    interface RegisterPresenter {
        void createUser(final User user, final Context context);
    }
}

