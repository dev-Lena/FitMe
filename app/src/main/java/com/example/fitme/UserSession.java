package com.example.fitme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


public class UserSession {
    // Shared Preferences reference
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "sharedPreferences";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NICKNAME = "nickname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public UserSession(Context context) {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    //Create login session
    public void createUserLoginSession(Object uEmail, Object uPassword) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
//
//        // Storing nickname in preferences
//        editor.putString(KEY_NICKNAME, (String) uNickname);

        // Storing email in preferences
        editor.putString(KEY_EMAIL, (String) uEmail);

        // Storing email in preferences
        editor.putString(KEY_PASSWORD, (String) uPassword);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, feed.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

//        // user nickname
//        user.put(KEY_NICKNAME, sharedPreferences.getString(KEY_NICKNAME, null));

        // user email id
        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));

        // user password
        user.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, login.class);         // 로그아웃하면

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);  //feed 화면으로 넘어가도록 위에 설정해놓음
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }


    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        sharedPreferences = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            jsonArray.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, jsonArray.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        sharedPreferences = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);  // sharedPreference를 가져오고
        String json = sharedPreferences.getString(key, null);  // key값에 있는 걸 String json 변수에 넣어주고
        ArrayList<String> stringArrayList = new ArrayList<String>();  // ArrayList 객체 선언을 해서 (sharedPreference에서 가져온 arrayList를 넣어줄 것
        if (json != null) {                                           //위에 Stirng json변수에 넣어준 값이 null이 아닐 때
            try {
                JSONArray jsonArray = new JSONArray(json);        //JSONArray 에
                for (int i = 0; i < jsonArray.length(); i++) {   // 길이만큼 반복문을 돌려서
                    String user = jsonArray.optString(i);        // i번째에 있는 곳 정보 String을 String user 변수에 넣는다
                    stringArrayList.add(user);                    // 위에서 객체선언한 ArrayList에 정보를 가져와 넣어준 user변수를 추가한다
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringArrayList;
    }
}