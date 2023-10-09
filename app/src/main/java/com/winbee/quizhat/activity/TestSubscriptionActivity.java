package com.winbee.quizhat.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.RazorPayModel;
import com.winbee.quizhat.model.TestSeriesPayment;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;

public class TestSubscriptionActivity extends Activity implements PaymentResultListener {
    private static final String TAG = TestSubscriptionActivity.class.getSimpleName();
    RelativeLayout description_layout, layout_discription_details, image_layout, layout_success, layout_failed;
    private ImageView WebsiteHome, img_share, image_expand_more, image_expand_less, img_noti;
    String UserId, android_id, UserMobile, UserPassword;
    TextView txt_no_subject, course_name, txt_discription_click, txt_discription, txt_course_discription, txt_total_test,
            txt_amount, txt_course, txt_txn_id;
    private ProgressBarUtil progressBarUtil;
    Button btn_demo, go_back, btn_course, go_back_failed, btn_payment;
    private Toast toast_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subscription);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Checkout.preload(getApplicationContext());
        course_name = findViewById(R.id.course_name);
        course_name.setText(OnlineTestData.TestName);
        progressBarUtil = new ProgressBarUtil(this);
        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UserMobile = SharedPrefManager.getInstance(this).refCode().getUsername();
        UserPassword = SharedPrefManager.getInstance(this).refCode().getPassword();
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();

        description_layout = findViewById(R.id.description_layout);
        layout_discription_details = findViewById(R.id.layout_discription_details);
        image_layout = findViewById(R.id.image_layout);
        txt_discription_click = findViewById(R.id.txt_discription_click);
        txt_discription = findViewById(R.id.txt_discription);
        txt_course_discription = findViewById(R.id.txt_course_discription);
        txt_course_discription.setText(OnlineTestData.TestDiscription);
        image_expand_more = findViewById(R.id.image_expand_more);
        image_expand_less = findViewById(R.id.image_expand_less);
        layout_success = findViewById(R.id.layout_success);
        layout_failed = findViewById(R.id.layout_failed);
        txt_amount = findViewById(R.id.txt_amount);
        txt_amount.setText("Amount :-" + OnlineTestData.TestDiscountPrice);
        txt_course = findViewById(R.id.txt_course);
        txt_course.setText("Test Name :-" + OnlineTestData.TestName);
        txt_txn_id = findViewById(R.id.txt_txn_id);
        txt_txn_id.setText("Reference No :- " + OnlineTestData.Org_id);
        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        go_back_failed = findViewById(R.id.go_back_failed);
        go_back_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestSubscriptionActivity.this, TestSubscriptionActivity.class));
                finish();
            }
        });

        description_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discription_details.setVisibility(View.VISIBLE);
                txt_discription_click.setVisibility(View.VISIBLE);
                txt_discription.setVisibility(View.GONE);
                image_expand_more.setVisibility(View.GONE);
                image_expand_less.setVisibility(View.VISIBLE);

            }
        });

        image_expand_less = findViewById(R.id.image_expand_less);
        image_expand_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discription_details.setVisibility(View.GONE);
                image_expand_more.setVisibility(View.VISIBLE);
                txt_discription_click.setVisibility(View.GONE);
                txt_discription.setVisibility(View.VISIBLE);
                image_expand_less.setVisibility(View.GONE);
            }
        });


        WebsiteHome = findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        img_share = findViewById(R.id.img_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "QuizHat");
                    String shareMessage = "\nQuizHat download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id=" + getPackageName();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
            }
        });


        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(TestSubscriptionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_payment_alert_test);
                TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
                TextView txt_course = dialog.findViewById(R.id.txt_course);
                TextView txt_discount = dialog.findViewById(R.id.txt_discount);
                TextView txt_actual_price = dialog.findViewById(R.id.txt_actual_price);
                TextView txt_discount_price = dialog.findViewById(R.id.txt_discount_price);
                txt_discount_price.setText(OnlineTestData.TestDiscountPrice);
                txt_actual_price.setText(OnlineTestData.TestDiscountPrice);

                txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_discount.setText(OnlineTestData.TestDisplayPrice);

                txt_course.setText(OnlineTestData.TestName);
                TextView txt_pervious_attempt = dialog.findViewById(R.id.txt_pervious_attempt);
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_pervious_attempt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userValidation();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setCancelable(false);
            }
        });


    }

    private void userValidation() {
        final String Course_id = OnlineTestData.Overview_id;
        final String user_id = SharedPrefManager.getInstance(this).refCode().getUserId();
        final String Amount_org_id = OnlineTestData.TestDiscountPrice;
        //final String Amount_org_id ="100";
        final String Org_id = "WB_010";


        TestSeriesPayment testSeriesPayment = new TestSeriesPayment();
        testSeriesPayment.setUser_id(user_id);
        testSeriesPayment.setAmount_org_id(Amount_org_id);
        testSeriesPayment.setOrg_id(Org_id);


        callPayment(testSeriesPayment);

    }

    private void callPayment(final TestSeriesPayment testSeriesPayment) {
        progressBarUtil.showProgress();
        ClientApi apiCall = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<TestSeriesPayment> call = apiCall.fetchPaymentData(OnlineTestData.Overview_id, testSeriesPayment.getUser_id(),
                testSeriesPayment.getAmount_org_id(), testSeriesPayment.getOrg_id(), "2", OnlineTestData.SubscriptionId);
        Log.i("tag", "callPayment: " + testSeriesPayment.getCourse_id() + " " + UserId + " " + testSeriesPayment.getAmount_org_id());
        call.enqueue(new Callback<TestSeriesPayment>() {
            @Override
            public void onResponse(Call<TestSeriesPayment> call, Response<TestSeriesPayment> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    OnlineTestData.Org_id = response.body().getOrgOrderId();
                    OnlineTestData.RazorpayOrderId = response.body().getRazorpayOrderId();
                    Log.i("tag", "onResponse: " + response.body().getRazorpayOrderId());
                    Log.i("tag", "test api key: " + OnlineTestData.payment_key);
                    System.out.println("===============" + OnlineTestData.RazorpayOrderId);
                    System.out.println("===============" + UserMobile);
                    System.out.println("===============" + OnlineTestData.TestDiscountPrice);
                    Toast.makeText(TestSubscriptionActivity.this, "Payment Page Loading...", Toast.LENGTH_SHORT).show();
                    startPayment();
                    progressBarUtil.hideProgress();
                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(TestSubscriptionActivity.this, "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TestSeriesPayment> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());

                Toast.makeText(TestSubscriptionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void startPayment() {
        final Activity activity = this;
        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp__live_4zz9QXpagJaPu7");
//        checkout.setKeyID(OnlineTestData.payment_key);

        String str = OnlineTestData.TestDiscountPrice;
        double inum = Double.parseDouble(str);
        double sum = inum * 100;
        String str1 = Double.toString(sum);


        try {
            JSONObject options = new JSONObject();

            options.put("name", UserMobile);


            options.put("send_sms_hash", false);
            options.put("description", "Purchase Test series");
            options.put("order_id", OnlineTestData.RazorpayOrderId);
            options.put("currency", "INR");
            options.put("amount", str1);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("tag", "Error in starting Razorpay Checkout", e);
        }
    }


    //    @Override
//    public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
//        try{
//            String paymentId = data.getPaymentId();
//            String signature = data.getSignature();
//            String orderId = data.getOrderId();
//            layout_success.setVisibility(View.VISIBLE);
//            btn_payment.setVisibility(View.GONE);
//            image_layout.setAlpha((float) 0.2);
//        }catch (Exception e){
//            Log.e("payment", "Exception in onPaymentError", e);
//        }
//
//    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            layout_success.setVisibility(View.VISIBLE);
            btn_payment.setVisibility(View.GONE);
            image_layout.setAlpha((float) 0.2);
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    //    @Override
//    public void onPaymentError(int i, String s, PaymentData paymentData) {
//        try{
//            layout_failed.setVisibility(View.VISIBLE);
//            btn_payment.setVisibility(View.GONE);
//            image_layout.setAlpha((float) 0.2);
//            System.out.println(s);
//        }catch (Exception e){
//            Log.e("payment", "Exception in onPaymentError", e);
//        }
//
//    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            layout_failed.setVisibility(View.VISIBLE);
            btn_payment.setVisibility(View.GONE);
            image_layout.setAlpha((float) 0.2);
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

    private void doToast(String msg) {
        if (toast_msg != null) {
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(TestSubscriptionActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}