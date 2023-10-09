package com.winbee.quizhat.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.balsikandar.crashreporter.CrashReporter;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.material.navigation.NavigationView;
import com.winbee.quizhat.Adapter.AllPerminumTestAdapter;
import com.winbee.quizhat.Adapter.InformationAdapter;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.AppUpdateChecker;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.SectionDetailsDataModel;
import com.winbee.quizhat.model.SectionDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ShimmerLayout shimmerLayout;
    ImageView img_share;
    private ProgressBarUtil progressBarUtil;
    ImageSlider img_video_thumbails;
    private RecyclerView recycle_test;
    SwipeRefreshLayout refresh_main;
    ImageView WebsiteHome;
    String UserID;
    TextView txt_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            WebsiteHome = findViewById(R.id.WebsiteHome);
            txt_nodata = findViewById(R.id.txt_nodata);
            UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
            System.out.println("1234567890=======" + OnlineTestData.payment_key);
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
        Call<SectionDetailsMainModel> call = apiClient.fetchSectionDetails(OnlineTestData.org_code, UserID, OnlineTestData.auth_code, "L1", OnlineTestData.Overview_id);
        call.enqueue(new Callback<SectionDetailsMainModel>() {
            @Override
            public void onResponse(Call<SectionDetailsMainModel> call, Response<SectionDetailsMainModel> response) {
                progressBarUtil.hideProgress();
                apiCalled();
                SectionDetailsMainModel sectionDetailsMainModel = response.body();
                if (sectionDetailsMainModel != null) {
                    if (sectionDetailsMainModel.getMessage().equalsIgnoreCase("TRUE")) {
                        List<SectionDetailsDataModel> sectionDetailsDataModelList = new ArrayList<>(Arrays.asList(sectionDetailsMainModel.getData()));
                        if (sectionDetailsDataModelList.isEmpty()) {
                            txt_nodata.setVisibility(View.VISIBLE);
                        } else {

                            txt_nodata.setVisibility(View.GONE);
                            // recycle_test.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                            InformationAdapter allPerminumTestAdapter = new InformationAdapter(MainActivity.this, sectionDetailsDataModelList);
                            recycle_test.setAdapter(allPerminumTestAdapter);
                        }

                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SectionDetailsMainModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
}