package com.gabrielaangebrandt.pregnancyapp.anamnesis.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.anamnesis.adapters.AnamnesisPagerAdapter;
import com.gabrielaangebrandt.pregnancyapp.forumActivity.view.ForumActivity;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view.MainFragment;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnamnesisActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anamnesis);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.my_anamnesis);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle(R.string.homepage);
                startActivity(new Intent(AnamnesisActivity.this, MainActivity.class).putExtra("frag", "HomeFragment"));
            }
        });

        viewPager.setAdapter(new AnamnesisPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {

    }
}
