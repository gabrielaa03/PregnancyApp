package com.gabrielaangebrandt.pregnancyapp.newQuestionActivity.view;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.forumActivity.view.ForumActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAnswerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_question_title)
    TextView question;

    @BindView(R.id.et_answer)
    EditText text;

    private DatabaseReference mDatabase;
    private Map<String, List<Map<String, String>>> qa = new HashMap<>();
    private String questionString;
    private List<Map<String, String>> answerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_answer);
        ButterKnife.bind(this);
        mDatabase = App.getFirebaseDb().getReference();
        toolbar.setTitle(R.string.open_new_question);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        questionString = getIntent().getStringExtra("question");
        question.setText(questionString);

        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            App.getFirebaseDb().getReference("forum").child("questionsAndAnswers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            qa.put(dataSnapshot1.getKey(), (List<Map<String, String>>) dataSnapshot1.getValue());
                            if (dataSnapshot1.getKey().equals(questionString))
                                answerList.addAll((List<Map<String, String>>) dataSnapshot1.getValue());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(NewAnswerActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btn_save_answer)
    public void addNewQuestion() {
        if (question.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.insertAllData, Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Map<String, String> answer = new HashMap<>();
            if (user != null) {
                answer.put("username", user.getDisplayName());
                answer.put("answer", text.getText().toString());
                answer.put("date", new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
                answerList.add(answer);
                qa.put(questionString, answerList);
            }
            App.getFirebaseDb().getReference("forum").child("questionsAndAnswers").removeValue();
            App.getFirebaseDb().getReference("forum").child("questionsAndAnswers").setValue(qa);
            startActivity(new Intent(this, ForumActivity.class));
        }
    }
}
