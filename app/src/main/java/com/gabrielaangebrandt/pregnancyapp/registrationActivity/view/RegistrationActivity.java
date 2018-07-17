package com.gabrielaangebrandt.pregnancyapp.registrationActivity.view;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.loginActivity.view.LoginActivity;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.User;
import com.gabrielaangebrandt.pregnancyapp.registrationActivity.RegisterContract;
import com.gabrielaangebrandt.pregnancyapp.registrationActivity.presenter.RegistrationPresenterImpl;
import com.gabrielaangebrandt.pregnancyapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class RegistrationActivity extends AppCompatActivity implements RegisterContract.RegisterView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.et_name)
    EditText name;

    @BindView(R.id.et_username)
    EditText username;

    @BindView(R.id.et_email)
    EditText email;

    @BindView(R.id.et_password)
    EditText password;

    @BindView(R.id.et_answer)
    EditText answer;

    @BindView(R.id.et_address)
    EditText address;

    @BindView(R.id.et_date_of_birth)
    EditText date;

    private FirebaseAuth mAuth;
    private RegisterContract.RegisterPresenter presenter;
    private DatePickerDialog mDatePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.register);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        presenter = new RegistrationPresenterImpl(this);
        setDateTimeField();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void saveUserInformationsToDB(User user) {
        String email = user.getEmail().replaceAll("\\.", ",");
        App.getFirebaseDb().getReference("users").child(email).setValue(user).addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("success", "createUserWithEmail:success");
                            startMainActivity();

                        } else {
                            Log.w("error", "createUserWithEmail:failure", task.getException());
                            showErrorToast(String.valueOf(task.getException().getMessage()));
                        }
                    }
                });
    }

    @Override
    public void showErrorToast(String error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(getApplicationContext(), R.string.successfulreg, Toast.LENGTH_SHORT).show();
    }

    @OnTouch(R.id.et_date_of_birth)
    public boolean showCalendar() {
        mDatePickerDialog.show();
        return false;
    }

    @OnClick(R.id.btn_register)
    public void register() {
        progressBar.setVisibility(View.VISIBLE);
        if (Utils.checkCredentials(username.getText().toString(), password.getText().toString(), email.getText().toString(), answer.getText().toString(), address.getText().toString(), date.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Incorrect inputs.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else {
            if (password.length() < 8) {
                Toast.makeText(getApplicationContext(), "Password must have at least 8 characters.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            } else {
                User user = new User(name.getText().toString(), username.getText().toString(), password.getText().toString(), answer.getText().toString(), email.getText().toString(), address.getText().toString(), date.getText().toString(), null, null, null, null, null, null, null, null);
                presenter.createUser(user, this);
            }
        }
    }

    private void setDateTimeField() {

        Calendar newCalendar = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newCalendar = Calendar.getInstance();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar newDate = null;
                    String fdate = "";
                    newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                    final Date startDate = newDate.getTime();
                    fdate = sd.format(startDate);
                    date.setText(fdate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
}
