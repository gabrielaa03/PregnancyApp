package com.gabrielaangebrandt.pregnancyapp.healthActivity.view;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.view.MealFragment;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.fragments.view.WorkoutFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.containerHealth)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        ButterKnife.bind(this);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(R.string.food));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.workout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerHealth, new MealFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerHealth, new WorkoutFragment()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerHealth, new MealFragment()).commit();
    }
}
