package com.gabrielaangebrandt.pregnancyapp.mainActivity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.gabrielaangebrandt.pregnancyapp.anamnesis.view.AnamnesisActivity;
import com.gabrielaangebrandt.pregnancyapp.healthActivity.view.HealthActivity;
import com.gabrielaangebrandt.pregnancyapp.forumActivity.view.ForumActivity;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.loginActivity.view.LoginActivity;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view.MainFragment;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view.MyMedicalRecordsFragment;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view.SettingsFragment;
import com.gabrielaangebrandt.pregnancyapp.photoAlbumActivity.PhotoActivity;
import com.gabrielaangebrandt.pregnancyapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.getSharedPrefs("settings", this).equals("")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new SettingsFragment()).commit();
            toolbar.setTitle(R.string.settings);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new MainFragment()).commit();
            toolbar.setTitle(R.string.homepage);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_medical_records:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new MyMedicalRecordsFragment()).commit();
                toolbar.setTitle(R.string.my_medical_records);
                break;
            case R.id.nav_anamnesis:
                startActivity(new Intent(this, AnamnesisActivity.class));
                break;
            case R.id.nav_healthy_pregnancy:
                startActivity(new Intent(this, HealthActivity.class).putExtra("title", R.string.healthy_pregnancy));
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new MainFragment()).commit();
                toolbar.setTitle(R.string.homepage);
                break;
            case R.id.nav_forum:
                startActivity(new Intent(this, ForumActivity.class));
                toolbar.setTitle(R.string.forum);
                break;
            case R.id.nav_photo_album:
                startActivity(new Intent(this, PhotoActivity.class));
                toolbar.setTitle(R.string.photo_album);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new SettingsFragment()).commit();
                toolbar.setTitle(R.string.settings);
                break;
            case R.id.nav_logout:
                Utils.setSharedPrefs("user", "", this);
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
