package com.winbee.quizhat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.balsikandar.crashreporter.CrashReporter;
import com.winbee.quizhat.Adapter.TestListAdapter;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.SIACDetailsDataModel;
import com.winbee.quizhat.model.SIACDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayQuizActivity extends AppCompatActivity {
    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_test;
    private Toast toast_msg;
    ImageView WebsiteHome,img_share;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        try{
            WebsiteHome=findViewById(R.id.WebsiteHome);
            UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
            WebsiteHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TodayQuizActivity.this,OverViewActivity.class);
                    startActivity(intent);
                }
            });
            img_share=findViewById(R.id.img_share);
            img_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "QuizHat");
                        String shareMessage= "\nQuizHat download the application.\n ";
                        shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch(Exception e) {
                    }
                }
            });

            iniIDs();
            getTestList();

        }catch (Exception e){
            CrashReporter.logException(e);
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_test=findViewById(R.id.recycle_test);
    }
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails(OnlineTestData.org_code,OnlineTestData.auth_code,
                "B_L2_000_024",UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        if (siacDetailsDataModelList.size() != 0) {
                            TestListAdapter testListAdapter=new TestListAdapter(TodayQuizActivity.this,siacDetailsDataModelList,true);
                            RecyclerView.LayoutManager layoutManager=new
                                    LinearLayoutManager(TodayQuizActivity.this, LinearLayoutManager.VERTICAL, false);
                            recycle_test.setLayoutManager(layoutManager);
                            recycle_test.setItemAnimator(new DefaultItemAnimator());
                            recycle_test.setAdapter(testListAdapter);
                        }else{
                            Toast.makeText(TodayQuizActivity.this, "No quiz found.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIACDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
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
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(TodayQuizActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}