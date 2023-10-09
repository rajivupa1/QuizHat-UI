package com.winbee.quizhat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.balsikandar.crashreporter.CrashReporter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.RefUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextname, editTextEmail, editTextPassword, editTextPhone, editTextRePassword;
    Button register;
    private ProgressBarUtil progressBarUtil;
    private String username, email, password, phone, re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        try {
            initView();
        } catch (Exception e) {
            CrashReporter.logException(e);
        }


    }

    private void initView() {
        progressBarUtil = new ProgressBarUtil(this);
        editTextname = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextRePassword = findViewById(R.id.editTextre_Password);

        register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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

    public void ReShowHidePass(View view) {

        if (view.getId() == R.id.show_repass_btn) {

            if (editTextRePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Show Password
                editTextRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                editTextRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }


    private void userValidation() {
        username = editTextname.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        re_password = editTextRePassword.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            editTextname.setError("Please enter username");
            editTextname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Please enter your mobile number");
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length()!=10) {
            editTextPhone.setError("Please enter valid mobile number");
            editTextPhone.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(re_password)) {
            editTextRePassword.setError("Enter a password");
            editTextRePassword.requestFocus();
            return;
        }

        if (password.equals(re_password)) {

        } else {
            editTextRePassword.setError("Password are not matching");
            editTextRePassword.requestFocus();
            return;
        }
        RefUser refUser = new RefUser();
        refUser.setName(username);
        refUser.setPassword(password);
        refUser.setEmail(email);
        refUser.setMobile(phone);


        CallSignupApi(refUser);


    }

    private void CallSignupApi(final RefUser refUser) {
        progressBarUtil.showProgress();
        ClientApi mService = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<RefUser> call = mService.refUserSignIn(refUser.getName(),
                refUser.getMobile(), refUser.getEmail(), refUser.getPassword());
        call.enqueue(new Callback<RefUser>() {
            @Override
            public void onResponse(Call<RefUser> call, Response<RefUser> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().isLogin_status()) {
                    Toast.makeText(RegisterActivity.this, response.body().getError_message(), Toast.LENGTH_LONG).show();
                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(RegisterActivity.this, response.body().getError_message(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<RefUser> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}