package com.gabrielaangebrandt.pregnancyapp.forgotenPassAcitivty.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgottenPasswordAcitivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.et_email)
    EditText email;

    @BindView(R.id.et_answer)
    EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password_acitivity);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.forgottenPass);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.save_data)
    public void getPassword() {
        progressBar.setVisibility(View.VISIBLE);
        Query query = App.getFirebaseDb().getReference().child("users").orderByChild("email").equalTo(email.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.child("secureAnswer").getValue().toString().equals(answer.getText().toString())) {
                            sendEmail(email.getText().toString());
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgottenPasswordAcitivity.this, R.string.wrongAnswer, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgottenPasswordAcitivity.this, R.string.cannotGetPass, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendEmail(String email) {
        App.getFirebaseAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Log.d("success", "Email sent.");
                            Toast.makeText(ForgottenPasswordAcitivity.this, R.string.emailSent, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
