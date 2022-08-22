package com.as.photoapp.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.as.photoapp.MainActivity;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    private int PRIVATE_MODE = 0;
    // Shared pref file name
    private static final String PREF_NAME = "SessionPref";
    // make variable public to access from outside
    public static final String KEY_ID = "id";

    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(int id){
        // Storing id in pref
        editor.putInt(KEY_ID, id);
        // commit changes
        editor.commit();
    }

    public HashMap<String, Integer> getUserDetails() {
        HashMap<String, Integer> user = new HashMap<>();

        user.put(KEY_ID, pref.getInt(KEY_ID, 0));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent intent = new Intent(context, MainActivity.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(intent);
    }
}
