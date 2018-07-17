package com.gabrielaangebrandt.pregnancyapp.newExaminationActivity.view;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.MedicalRecord;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class NewExamination extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_date)
    EditText date;
    @BindView(R.id.spinner_weeks)
    Spinner weeks;
    @BindView(R.id.spinner_days)
    Spinner days;
    @BindView(R.id.et_weight)
    EditText weight;
    @BindView(R.id.spinner_sistolic)
    Spinner sistolic;
    @BindView(R.id.spinner_diastolic)
    Spinner disatolic;
    @BindView(R.id.spinner_edema)
    Spinner edema;
    @BindView(R.id.spinner_proteins)
    Spinner proteins;
    @BindView(R.id.spinner_leukocytes)
    Spinner leukocytes;
    @BindView(R.id.spinner_glucose)
    Spinner glucose;
    @BindView(R.id.et_kcs)
    EditText kcs;
    @BindView(R.id.et_medical_results)
    EditText medicalResults;
    @BindView(R.id.et_next_ex)
    EditText nextExamination;


    private DatePickerDialog mDatePickerDialog;
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_examination);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.newEx));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.edema_array, android.R.layout.simple_list_item_1);
        edema.setAdapter(adapter);
        proteins.setAdapter(adapter);
        glucose.setAdapter(adapter);
        leukocytes.setAdapter(adapter);

        sistolic.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, setArray(1, 250)));
        disatolic.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, setArray(1, 120)));
        weeks.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, setArray(1, 42)));
        days.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, setArray(1, 7)));

        medicalRecords = getIntent().getParcelableArrayListExtra("medRecords");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnTouch(R.id.et_date)
    public boolean onTouchDate() {
        setDateTimeField(date);
        mDatePickerDialog.show();
        return false;
    }

    @OnTouch(R.id.et_next_ex)
    public boolean onTouchNextEx() {
        setDateTimeField2(nextExamination);
        mDatePickerDialog.show();
        return false;
    }

    @OnClick(R.id.btn_save_examination_data)
    public void saveData() {
        MedicalRecord medicalRecord = new MedicalRecord(date.getText().toString(), weeks.getSelectedItem().toString(), weight.getText().toString(), sistolic.getSelectedItem().toString() + "/" + disatolic.getSelectedItem().toString(), edema.getSelectedItem().toString(), proteins.getSelectedItem().toString(), glucose.getSelectedItem().toString(), leukocytes.getSelectedItem().toString(), kcs.getText().toString(), medicalResults.getText().toString(), nextExamination.getText().toString());
        medicalRecords.add(medicalRecord);
        FirebaseUser user = App.getFirebaseAuth().getCurrentUser();
        if (user != null){
            App.getFirebaseDb().getReference("users").child(user.getEmail().replaceAll("\\.", ",")).child("medicalRecords").setValue(medicalRecords);
            Toast.makeText(this, R.string.dataSaved, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.dataCannotBeASaved, Toast.LENGTH_SHORT).show();
        }
        getFragmentManager().popBackStackImmediate();
    }

    private void setDateTimeField(final EditText editText) {

        Calendar newCalendar = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newCalendar = Calendar.getInstance();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

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
                        mDatePickerDialog.dismiss();
                    }
                    editText.setText(fdate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void setDateTimeField2(final EditText editText) {

        Calendar newCalendar = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newCalendar = Calendar.getInstance();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

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
                        mDatePickerDialog.dismiss();
                    }
                    editText.setText(fdate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private List<Integer> setArray(int first, int second) {
        List<Integer> integers = new ArrayList<>();
        for (int i = first; i < second + 1; i++) {
            integers.add(i);
        }
        return integers;
    }
}
