package com.gabrielaangebrandt.pregnancyapp.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

    public static void setSharedPrefs(String key, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String getSharedPrefs(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static boolean checkCredentials(String username, String password, String email, String answer, String address, String date) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || answer.isEmpty() || address.isEmpty() || date.isEmpty()) {
            return true;
        } else {
            Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return !matcher.matches();

        }
    }

    public static int getFromArray(String[] array, String string) {
        int position = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i].equals(string)) {
                position = i;
                break;
            }
        }
        return position;
    }
}
