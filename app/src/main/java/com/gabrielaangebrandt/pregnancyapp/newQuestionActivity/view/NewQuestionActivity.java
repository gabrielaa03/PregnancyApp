package com.gabrielaangebrandt.pregnancyapp.newQuestionActivity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.forumActivity.view.ForumActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewQuestionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_answer)
    EditText question;

    private DatabaseReference mDatabase;
    private List<String> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        ButterKnife.bind(this);
        mDatabase = App.getFirebaseDb().getReference();
        toolbar.setTitle(R.string.open_new_question);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForumActivity.class));
            }
        });

        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            App.getFirebaseDb().getReference("forum").child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        questions.add(dataSnapshot1.getValue(String.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(NewQuestionActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {

    }

    @OnClick(R.id.btn_save_question)
    public void addNewQuestion() {
        if (question.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.insertAllData, Toast.LENGTH_SHORT).show();
        } else {
            if(question.getText().toString().contains("[].$#/")){
                Toast.makeText(this, R.string.keyMustNotContain, Toast.LENGTH_SHORT).show();
            }else {
                questions.add(question.getText().toString());
                App.getFirebaseDb().getReference("forum").child("questions").removeValue();
                App.getFirebaseDb().getReference("forum").child("questions").setValue(questions);
                startActivity(new Intent(this, ForumActivity.class));
            }
        }
    }
}
