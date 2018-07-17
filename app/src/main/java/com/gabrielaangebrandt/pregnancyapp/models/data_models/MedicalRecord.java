package com.gabrielaangebrandt.pregnancyapp.models.data_models;

import android.os.Parcel;
import android.os.Parcelable;

public class MedicalRecord implements Parcelable {
    private String date;
    private String weekOfPregnancy;
    private String weight;
    private String RR;
    private String edema;
    private String proteins;
    private String glocuse;
    private String leukocytes;
    private String KCS;
    private String medicalResults;
    private String nextEx;

    public MedicalRecord() {

    }

    public MedicalRecord(String date, String weekOfPregnancy, String weight, String RR, String edema, String proteins, String glocuse, String leukocytes, String KCS, String medicalResults, String nextEx) {
        this.date = date;
        this.weekOfPregnancy = weekOfPregnancy;
        this.weight = weight;
        this.RR = RR;
        this.edema = edema;
        this.proteins = proteins;
        this.glocuse = glocuse;
        this.leukocytes = leukocytes;
        this.KCS = KCS;
        this.medicalResults = medicalResults;
        this.nextEx = nextEx;
    }

    protected MedicalRecord(Parcel in) {
        date = in.readString();
        weekOfPregnancy = in.readString();
        weight = in.readString();
        RR = in.readString();
        edema = in.readString();
        proteins = in.readString();
        glocuse = in.readString();
        leukocytes = in.readString();
        KCS = in.readString();
        medicalResults = in.readString();
        nextEx = in.readString();
    }

    public static final Creator<MedicalRecord> CREATOR = new Creator<MedicalRecord>() {
        @Override
        public MedicalRecord createFromParcel(Parcel in) {
            return new MedicalRecord(in);
        }

        @Override
        public MedicalRecord[] newArray(int size) {
            return new MedicalRecord[size];
        }
    };

    public String getWeekOfPregnancy() {
        return weekOfPregnancy;
    }

    public void setWeekOfPregnancy(String weekOfPregnancy) {
        this.weekOfPregnancy = weekOfPregnancy;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRR() {
        return RR;
    }

    public void setRR(String RR) {
        this.RR = RR;
    }

    public String getEdema() {
        return edema;
    }

    public void setEdema(String edema) {
        this.edema = edema;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getGlocuse() {
        return glocuse;
    }

    public void setGlocuse(String glocuse) {
        this.glocuse = glocuse;
    }

    public String getLeukocytes() {
        return leukocytes;
    }

    public void setLeukocytes(String leukocytes) {
        this.leukocytes = leukocytes;
    }

    public String getKCS() {
        return KCS;
    }

    public void setKCS(String KCS) {
        this.KCS = KCS;
    }

    public String getMedicalResults() {
        return medicalResults;
    }

    public void setMedicalResults(String medicalResults) {
        this.medicalResults = medicalResults;
    }

    public String getNextEx() {
        return nextEx;
    }

    public void setNextEx(String nextEx) {
        this.nextEx = nextEx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(weekOfPregnancy);
        dest.writeString(weight);
        dest.writeString(RR);
        dest.writeString(edema);
        dest.writeString(proteins);
        dest.writeString(glocuse);
        dest.writeString(leukocytes);
        dest.writeString(KCS);
        dest.writeString(medicalResults);
        dest.writeString(nextEx);
    }
}
