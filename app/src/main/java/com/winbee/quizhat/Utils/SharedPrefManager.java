package com.winbee.quizhat.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.winbee.quizhat.activity.LoginActivity;
import com.winbee.quizhat.model.RefCode;
import com.winbee.quizhat.model.RefUser;


public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "serverloginsharedpref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_ROLE_ENCODE = "role_Encode";
    private static final String KEY_STATUS = "Status";
    private static final String KEY_USERID = "UserId";
    private static final String KEY_ORG_CODE = "Org_Code";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REF_CODE = "ref_code";
    private static final String KEY_REFERAL_CODE = "rEFERRAL_CODE";

    private static final String KEY_CRED = "Cred";



    private static final String SHARED_PREF_NAME1 = "serverloginsharedpref1";
    private static final String KEY_USERNAME1 = "Username";
    private static final String KEY_NAME1 = "Name";
    private static final String KEY_EMAIL1 = "Email";
    private static final String KEY_ROLE_ENCODE1 = "role_Encode";
    private static final String KEY_REFERAL_CODE1 = "referal_code";
    private static final String KEY_USERID1 = "UserId";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(RefCode refCode) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, refCode.getUsername());
        editor.putString(KEY_USERID, refCode.getUserId());
        editor.putString(KEY_PASSWORD, refCode.getPassword());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
    public RefCode refCode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new RefCode(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_USERID, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_NAME, null)
        );

    }
        public RefUser refUser1() {
            SharedPreferences sharedPreferences1 = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
            return new RefUser(
                    sharedPreferences1.getString(KEY_NAME1, null),
                    sharedPreferences1.getString(KEY_EMAIL1, null),
                    sharedPreferences1.getString(KEY_USERID1, null)

            );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));

    }
}
