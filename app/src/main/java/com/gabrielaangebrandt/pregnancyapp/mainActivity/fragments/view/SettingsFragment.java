package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.view.MainActivity;
import com.gabrielaangebrandt.pregnancyapp.utils.Utils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsFragment extends Fragment {

    @BindView(R.id.et_childbirth)
    EditText childBirth;

    @BindView(R.id.et_other)
    EditText otherDisease;

    @BindView(R.id.et_labProtocol)
    EditText labProtocol;

    @BindView(R.id.spinner_blood_type)
    Spinner spinnerBloodType;

    @BindView(R.id.spinner_rh)
    Spinner spinnerRH;

    @BindView(R.id.spinner_coombs)
    Spinner spinnerCoombs;

    @BindView(R.id.save_data)
    Button saveData;


    private Unbinder unbinder;
    private DatePickerDialog mDatePickerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.blood_type_array, android.R.layout.simple_list_item_1);
        spinnerBloodType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterRH = ArrayAdapter.createFromResource(getContext(), R.array.rh_type_array, android.R.layout.simple_list_item_1);
        spinnerRH.setAdapter(adapterRH);

        ArrayAdapter<CharSequence> adapterCoombs = ArrayAdapter.createFromResource(getContext(), R.array.coombs_type_array, android.R.layout.simple_list_item_1);
        spinnerCoombs.setAdapter(adapterCoombs);

        setDateTimeField();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        spinnerBloodType.setSelection(Utils.getFromArray(getResources().getStringArray(R.array.blood_type_array), Utils.getSharedPrefs("bloodType", getContext())));
        spinnerRH.setSelection(Utils.getFromArray(getResources().getStringArray(R.array.rh_type_array), Utils.getSharedPrefs("rh", getContext())));
        spinnerCoombs.setSelection(Utils.getFromArray(getResources().getStringArray(R.array.coombs_type_array), Utils.getSharedPrefs("coombsTest", getContext())));
        labProtocol.setText(Utils.getSharedPrefs("labProtocolNumber", getContext()));
        otherDisease.setText(Utils.getSharedPrefs("other", getContext()));
        childBirth.setText(Utils.getSharedPrefs("birthDate", getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.save_data)
    public void saveData() {
        if (labProtocol.getText().toString().isEmpty() || childBirth.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.insertAllData, Toast.LENGTH_SHORT).show();
        } else {
            String bloodtype = spinnerBloodType.getSelectedItem().toString();
            String rh = spinnerRH.getSelectedItem().toString();
            String coombsTest = spinnerCoombs.getSelectedItem().toString();
            String labProtocolNumber = labProtocol.getText().toString();
            String birthDate = childBirth.getText().toString();
            String other = otherDisease.getText().toString();

            Utils.setSharedPrefs("bloodType", bloodtype, getContext());
            Utils.setSharedPrefs("rh", rh, getContext());
            Utils.setSharedPrefs("coombsTest", coombsTest, getContext());
            Utils.setSharedPrefs("labProtocolNumber", labProtocolNumber, getContext());
            Utils.setSharedPrefs("other", other, getContext());
            Utils.setSharedPrefs("birthDate", birthDate, getContext());

            FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
            if (user != null) {
                App.getFirebaseDb().getReference().child("users").child(user.getEmail().replaceAll("\\.", ",")).child("bloodType").setValue(bloodtype + " RH" + rh);
                App.getFirebaseDb().getReference().child("users").child(user.getEmail().replaceAll("\\.", ",")).child("coombsTest").setValue(coombsTest);
                App.getFirebaseDb().getReference().child("users").child(user.getEmail().replaceAll("\\.", ",")).child("labProtocolNumber").setValue(labProtocolNumber);
                App.getFirebaseDb().getReference().child("users").child(user.getEmail().replaceAll("\\.", ",")).child("birthDate").setValue(birthDate);
                App.getFirebaseDb().getReference().child("users").child(user.getEmail().replaceAll("\\.", ",")).child("otherDisease").setValue(other);
                Toast.makeText(getContext(), R.string.dataSaved, Toast.LENGTH_SHORT).show();

                Utils.setSharedPrefs("settings", "set", getApplicationContext());

                MainFragment nextFrag= new MainFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, nextFrag)
                        .addToBackStack(null)
                        .commit();
                getActivity().setTitle(R.string.homepage);
            }
        }
    }

    @OnTouch(R.id.et_childbirth)
    public boolean showCalendar() {
        mDatePickerDialog.show();
        return false;
    }

    private String setDataInFields(String key) {
        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        final String[] value = new String[1];
        if (user != null) {
            App.getFirebaseDb().getReference("users").child(user.getEmail().replaceAll("\\.", ",")).child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    value[0] = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                    Toast.makeText(getContext(), R.string.failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return value[0];
    }

    private void setDateTimeField() {

        Calendar newCalendar = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newCalendar = Calendar.getInstance();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar newDate = null;
                    String fdate = "";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                        final Date startDate = newDate.getTime();
                        fdate = sd.format(startDate);
                    }
                    childBirth.setText(fdate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }
}
