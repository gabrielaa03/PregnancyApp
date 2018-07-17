package com.gabrielaangebrandt.pregnancyapp.anamnesis.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.anamnesis.fragments.view.FamilyAnamnesisFragment;
import com.gabrielaangebrandt.pregnancyapp.anamnesis.fragments.view.PersonalAnamnesisFragment;
import com.gabrielaangebrandt.pregnancyapp.anamnesis.fragments.view.ThreatenedPregnancyFragment;

public class AnamnesisPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public AnamnesisPagerAdapter(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FamilyAnamnesisFragment.newInstance(position);
            case 1:
                return PersonalAnamnesisFragment.newInstance(position);
            case 2:
                return ThreatenedPregnancyFragment.newInstance(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.famAnamnesis);
            case 1:
                return context.getString(R.string.perAnamnesis);
            case 2:
                return context.getString(R.string.thrPregnancy);
            default:
                return "";
        }
    }
}
