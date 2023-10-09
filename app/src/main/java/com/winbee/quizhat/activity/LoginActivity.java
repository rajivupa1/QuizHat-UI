package com.winbee.quizhat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.Constants;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.RefCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    private long backPressedTime;
    private ProgressBarUtil progressBarUtil;
    LinearLayout forgetPassword;

    // private String username,android_id,UserID,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, OverViewActivity.class));
            return;
        }
        initViews();
    }

    private void initViews() {
        progressBarUtil = new ProgressBarUtil(this);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        // forgetPassword = (LinearLayout) findViewById(R.id.link_forget_password);
//        forgetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
//                startActivity(intent);
//            }
//        });


        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();

            }
        });

        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn) {

            if (editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Show Password
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void userValidation() {
        final String mobile = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(mobile)) {
            editTextUsername.setError("Please enter your mobile no");
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        if (mobile.length()!=10) {
            editTextUsername.setError("Please enter valid mobile number");
            editTextUsername.requestFocus();
            return;
        }
        RefCode refCode = new RefCode();
        refCode.setUsername(mobile);
        refCode.setPassword(password);
        callRefCodeSignInApi(refCode);
    }

    private void callRefCodeSignInApi(final RefCode refCode) {
        progressBarUtil.showProgress();
        ClientApi mService = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeSignIn(refCode.getUsername(), refCode.getPassword());
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().isLogin_status()) {
                    progressBarUtil.hideProgress();
                    Constants.CurrentUser = response.body();


                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(Constants.CurrentUser);
                    SharedPreferences sharedPreferences =getSharedPreferences("UserPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("name",response.body().getName());
                   // editor.putString("isSubscribe",response.body().getRole_encode());
                    editor.commit();


                    Intent intent = new Intent(LoginActivity.this, OverViewActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressBarUtil.hideProgress();
                    Snackbar.make(findViewById(R.id.main_layout), response.body().getError_message(),
                            Snackbar.LENGTH_LONG).setBackgroundTint(Color.WHITE).show();
//                    Toast.makeText(LoginActivity.this, response.body().getError_message().toString(), Toast.LENGTH_LONG).show();
                    Log.d("TAG", "onResponse: " + response.body().getError_message());
                }


            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press Exit again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}