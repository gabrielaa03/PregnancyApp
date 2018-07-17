package com.gabrielaangebrandt.pregnancyapp.forumActivity.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.forumActivity.adapters.ExpandableListAdapter;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.view.MainActivity;
import com.gabrielaangebrandt.pregnancyapp.newQuestionActivity.view.NewQuestionActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForumActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.et_search)
    EditText search;

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;

    @BindView(R.id.btn_new_question)
    Button btnNewQuestion;

    private ExpandableListAdapter adapter;
    private List<String> questionList = new ArrayList<>();
    private Map<String, List<Map<String, String>>> questionsAndAnswers = new HashMap<>();
    private List<String> filterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.forum);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList.clear();
                for (String string : questionList) {
                    if (string.contains(s)) {
                        filterList.add(string);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        setUpUI();

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
    }

    @OnClick(R.id.btn_new_question)
    public void addNewQuestion() {
        startActivity(new Intent(this, NewQuestionActivity.class));
    }

    private void setUpUI() {
        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            App.getFirebaseDb().getReference("forum").child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            questionList.add(dataSnapshot1.getValue(String.class));
                        }
                    }
                    filterList.addAll(questionList);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(ForumActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            });

            App.getFirebaseDb().getReference("forum").child("questionsAndAnswers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            questionsAndAnswers.put(dataSnapshot1.getKey(), (List<Map<String, String>>) dataSnapshot1.getValue());
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(ForumActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
        adapter = new ExpandableListAdapter(this, filterList, questionsAndAnswers);
        expandableListView.setAdapter(adapter);
        expandableListView.setChildIndicator(null);
        expandableListView.setGroupIndicator(null);
    }
}


