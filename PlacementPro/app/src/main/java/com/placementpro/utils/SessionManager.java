package com.placementpro.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "PlacementProSession";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COLLEGE = "college";
    private static final String KEY_TARGET_COMPANY = "targetCompany";
    private static final String KEY_STREAK = "streak";
    private static final String KEY_LAST_LOGIN = "lastLogin";
    private static final String KEY_XP = "xp";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createSession(String userId, String name, String email, String college, String targetCompany) {
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_COLLEGE, college);
        editor.putString(KEY_TARGET_COMPANY, targetCompany);
        editor.apply();
    }

    public boolean isLoggedIn() { return pref.getBoolean(KEY_LOGGED_IN, false); }
    public String getUserId() { return pref.getString(KEY_USER_ID, ""); }
    public String getName() { return pref.getString(KEY_NAME, "Student"); }
    public String getEmail() { return pref.getString(KEY_EMAIL, ""); }
    public String getCollege() { return pref.getString(KEY_COLLEGE, ""); }
    public String getTargetCompany() { return pref.getString(KEY_TARGET_COMPANY, "Top MNC"); }

    public int getStreak() { return pref.getInt(KEY_STREAK, 0); }
    public void incrementStreak() { editor.putInt(KEY_STREAK, getStreak() + 1); editor.apply(); }

    public int getXP() { return pref.getInt(KEY_XP, 0); }
    public void addXP(int points) { editor.putInt(KEY_XP, getXP() + points); editor.apply(); }

    public String getLastLogin() { return pref.getString(KEY_LAST_LOGIN, ""); }
    public void setLastLogin(String date) { editor.putString(KEY_LAST_LOGIN, date); editor.apply(); }

    public void logout() { editor.clear(); editor.apply(); }
}
