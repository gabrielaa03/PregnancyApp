package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters.HomeFactsAdapter;
import com.gabrielaangebrandt.pregnancyapp.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_progressBarTite)
    TextView progressBarTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private double pregnancyProgress;
    private HomeFactsAdapter adapter;
    private List<String> list = new ArrayList<>();

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, parent, false);
        unbinder = ButterKnife.bind(this, view);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date birthDate = myFormat.parse(Utils.getSharedPrefs("birthDate", getContext()));
            Date today = Calendar.getInstance().getTime();
            double days = TimeUnit.DAYS.convert(birthDate.getTime() - today.getTime(), TimeUnit.MILLISECONDS);
            pregnancyProgress = (280 - days) / 280 * 100;
            progressBar.setProgress((int) pregnancyProgress);
            progressBarTitle.setText(String.format("%.1f", pregnancyProgress) + "%");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        list = Arrays.asList(getResources().getStringArray(R.array.homeFacts));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeFactsAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
