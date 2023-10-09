package com.winbee.quizhat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.winbee.quizhat.Adapter.AllPerminumTestAdapter;
import com.winbee.quizhat.Adapter.OverviewAdapter;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.AppUpdateChecker;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.OverviewDataModel;
import com.winbee.quizhat.model.OverviewMainModel;
import com.winbee.quizhat.model.RazorPayModel;
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

public class OverViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ShimmerLayout shimmerLayout;
    ImageView img_share;
    private ProgressBarUtil progressBarUtil;
    ImageSlider img_video_thumbails;
    private RecyclerView home_recycle;
    SwipeRefreshLayout refresh_main;
    ImageView WebsiteHome;
    String UserID;
    private boolean doubleBackToExitPressedOnce = false;
    CardView currentAffairs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        AppUpdateChecker appUpdateChecker = new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("QuizHat", "QuizHat", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        FirebaseMessaging.getInstance().subscribeToTopic("QuizHat")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        //Toast.makeText(GecHomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shimmerLayout = findViewById(R.id.shimmerLayout);
        progressBarUtil = new ProgressBarUtil(this);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        home_recycle = findViewById(R.id.home_recycle);
        WebsiteHome = findViewById(R.id.WebsiteHome);
        currentAffairs = findViewById(R.id.currentAfair);
        img_share = findViewById(R.id.img_share);

        currentAffairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(OverViewActivity.this, CurrentAfair.class);
                startActivity(profile);
            }
        });

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
        callRazorPayService();
        callTestApiService();
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.txt_user);
        TextView nav_user_nuber = (TextView) hView.findViewById(R.id.txt_user_number);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        nav_user.setText(sharedPreferences.getString("name", ""));
        nav_user_nuber.setText(SharedPrefManager.getInstance(this).refCode().getUsername());
        refresh_main = findViewById(R.id.refresh_main);
        refresh_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callTestApiService();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_main.setRefreshing(false);
                    }
                }, 4000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(OverViewActivity.this, OverViewActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_quiz) {
            Intent profile = new Intent(OverViewActivity.this, TodayQuizActivity.class);
            startActivity(profile);


        }
        else if (id == R.id.aboutUs) {
            Intent profile = new Intent(OverViewActivity.this, AboutUsActivity.class);
            startActivity(profile);
        }
        else if (id == R.id.nav_purchase) {
            Intent course = new Intent(OverViewActivity.this, SubscriptionActivity.class);
            startActivity(course);
        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_version) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callTestApiService() {
        progressBarUtil.showProgress();
        apiCall();
        ClientApi apiClient = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<OverviewMainModel> call = apiClient.fetchOverviewDetails(OnlineTestData.org_code, UserID, OnlineTestData.auth_code, "L1");
        call.enqueue(new Callback<OverviewMainModel>() {
            @Override
            public void onResponse(Call<OverviewMainModel> call, Response<OverviewMainModel> response) {
                progressBarUtil.hideProgress();
                apiCalled();
                OverviewMainModel overviewMainModel = response.body();
                if (overviewMainModel != null) {
                    if (overviewMainModel.getMessage().equalsIgnoreCase("TRUE")) {
                        List<OverviewDataModel> overviewDataModelList = new ArrayList<>(Arrays.asList(overviewMainModel.getData()));
                        //home_recycle.setHasFixedSize(true);
                        home_recycle.setLayoutManager(new GridLayoutManager(OverViewActivity.this, 2));
                        home_recycle.addItemDecoration(new DividerItemDecoration(OverViewActivity.this, LinearLayoutManager.VERTICAL));
                        OverviewAdapter overviewAdapter = new OverviewAdapter(OverViewActivity.this, overviewDataModelList);
                        home_recycle.setAdapter(overviewAdapter);
                    } else {
                        Toast.makeText(OverViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(OverViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OverviewMainModel> call, Throwable t) {
                Toast.makeText(OverViewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("call fail " + t);
                progressBarUtil.hideProgress();
            }
        });
    }

    private void callRazorPayService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<RazorPayModel> call = apiCAll.getRazorPay();
        call.enqueue(new Callback<RazorPayModel>() {
            @Override
            public void onResponse(Call<RazorPayModel> call, Response<RazorPayModel> response) {
                int statusCode = response.code();

                if (statusCode == 200 && response.body() != null) {
                    progressBarUtil.hideProgress();
                    OnlineTestData.payment_key = response.body().getAPI_Key();
                    System.out.println("==================================" + response.body());
                } else {
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RazorPayModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
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