package com.winbee.quizhat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balsikandar.crashreporter.CrashReporter;
import com.denzcoskun.imageslider.ImageSlider;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.winbee.quizhat.Adapter.InformationAdapter;
import com.winbee.quizhat.Adapter.SubscriptionAdapter;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.SectionDetailsDataModel;
import com.winbee.quizhat.model.SectionDetailsMainModel;
import com.winbee.quizhat.model.SubscriptionDataModel;
import com.winbee.quizhat.model.SubscriptionMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    private ShimmerLayout shimmerLayout;
    ImageView img_share;
    private ProgressBarUtil progressBarUtil;
    ImageSlider img_video_thumbails;
    private RecyclerView recycle_test;
    SwipeRefreshLayout refresh_main;
    ImageView WebsiteHome;
    String UserID;
    RelativeLayout layout_success, layout_failed, layout_view;
    Button go_back, btn_course, go_back_failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        try {
            WebsiteHome = findViewById(R.id.WebsiteHome);
            layout_success = findViewById(R.id.layout_success);
            layout_failed = findViewById(R.id.layout_failed);
            layout_view = findViewById(R.id.layout_view);
            go_back = findViewById(R.id.go_back);
            go_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            btn_course = findViewById(R.id.btn_course);
            btn_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubscriptionActivity.this, OverViewActivity.class);
                    startActivity(intent);
                }
            });
            go_back_failed = findViewById(R.id.go_back_failed);
            go_back_failed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubscriptionActivity.this, SubscriptionActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
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

            iniIDs();
            callTestApiService();

        } catch (Exception e) {
            CrashReporter.logException(e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void iniIDs() {
        shimmerLayout = findViewById(R.id.shimmerLayout);
        recycle_test = findViewById(R.id.recycle_test);
        progressBarUtil = new ProgressBarUtil(this);
    }

    private void callTestApiService() {
        progressBarUtil.showProgress();
        apiCall();
        ClientApi apiClient = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SubscriptionMainModel> call = apiClient.fetchSubscriptionDetailsForApp("WB_010",
                OnlineTestData.auth_code, UserID);
        call.enqueue(new Callback<SubscriptionMainModel>() {
            @Override
            public void onResponse(Call<SubscriptionMainModel> call, Response<SubscriptionMainModel> response) {
                progressBarUtil.hideProgress();
                apiCalled();
                SubscriptionMainModel sectionDetailsMainModel = response.body();
                if (sectionDetailsMainModel != null) {
                    if (sectionDetailsMainModel.getMessage().equalsIgnoreCase("TRUE")) {
                        List<SubscriptionDataModel> sectionDetailsDataModelList = new ArrayList<>(Arrays.asList(sectionDetailsMainModel.getData()));
                        //recycle_test.setLayoutManager(new GridLayoutManager(SubscriptionActivity.this, 2));
                        SubscriptionAdapter allPerminumTestAdapter = new SubscriptionAdapter(SubscriptionActivity.this, sectionDetailsDataModelList);
                        recycle_test.setAdapter(allPerminumTestAdapter);
                    } else {
                        Toast.makeText(SubscriptionActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(SubscriptionActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SubscriptionMainModel> call, Throwable t) {
                Toast.makeText(SubscriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("call fail " + t);
                progressBarUtil.hideProgress();
            }
        });
    }

    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }

    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        layout_success.setVisibility(View.VISIBLE);
        layout_view.setAlpha((float) 0.2);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        layout_failed.setVisibility(View.VISIBLE);
        layout_view.setAlpha((float) 0.2);
    }
}