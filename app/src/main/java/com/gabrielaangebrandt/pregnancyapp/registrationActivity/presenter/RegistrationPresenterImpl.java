package com.gabrielaangebrandt.pregnancyapp.registrationActivity.presenter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.User;
import com.gabrielaangebrandt.pregnancyapp.registrationActivity.RegisterContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationPresenterImpl implements RegisterContract.RegisterPresenter {
    private RegisterContract.RegisterView view;

    public RegistrationPresenterImpl(RegisterContract.RegisterView view) {
        this.view = view;
    }

    public void createUser(final User user, final Context context) {
        App.getFirebaseAuth().createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("success", "createUserWithEmail:success");
                            FirebaseUser firebaseUser = App.getFirebaseAuth().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getName()).build();

                            firebaseUser.updateProfile(profileUpdates);
                            view.saveUserInformationsToDB(user);

                        } else {
                            Log.w("error", "createUserWithEmail:failure", task.getException());
                            view.showErrorToast(String.valueOf(task.getException().getMessage()));
                        }
                    }
                });
    }
}