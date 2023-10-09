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

import com.winbee.quizhat.Adapter.CurrentAffairAdapter;
import com.winbee.quizhat.Adapter.CurrentAffairDetailsAdapter;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.CurrentAffairDataModel;
import com.winbee.quizhat.model.CurrentAffairDetailsDataModel;
import com.winbee.quizhat.model.CurrentAffairDetailsMainModel;
import com.winbee.quizhat.model.CurrentAffairModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentAffairDetails extends AppCompatActivity {
    ImageView WebsiteHome,img_share,companylogo,img_noti;
    private Toast toast_msg;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    String monthID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affair_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        img_share=findViewById(R.id.img_share);
        recyclerView = findViewById(R.id.recycle_test);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            monthID = getIntent().getStringExtra("MONTH_ID");
            getList();
        }
        doToast("Please Wait....");
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentAffairDetails.this, OverViewActivity.class);
                startActivity(intent);
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "QuizHat");
                    String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });

    }

    private void getList() {

        ClientApi apiClient = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<CurrentAffairDetailsMainModel> call = apiClient.getBlogList(monthID);

        call.enqueue(new Callback<CurrentAffairDetailsMainModel>() {
            @Override
            public void onResponse(Call<CurrentAffairDetailsMainModel> call, Response<CurrentAffairDetailsMainModel> response) {
                //  apiCalled();
                CurrentAffairDetailsMainModel currentAffairModel = response.body();
                if (currentAffairModel != null) {
                    if (currentAffairModel.getSuccess()) {
                        List<CurrentAffairDetailsDataModel> currentAffairDataModel = new ArrayList<>(Arrays.asList(currentAffairModel.getData()));
                        if (currentAffairDataModel.isEmpty()) {
                            doToast("No Data Found!");
                            // txt_nodata.setVisibility(View.VISIBLE);
                        } else
                        {
                            //  txt_nodata.setVisibility(View.GONE);
                            CurrentAffairDetailsAdapter testListAdapter = new CurrentAffairDetailsAdapter(CurrentAffairDetails.this, currentAffairDataModel);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CurrentAffairDetails.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(testListAdapter);
                        }

                    } else
                        doToast(currentAffairModel.getMessage());
                } else
                    doToast("data null");
            }

            @Override
            public void onFailure(Call<CurrentAffairDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
//                System.out.println("call fail " + t);
//                apiCalled();
            }
        });
    }


    private void doToast(String msg) {
        if (toast_msg != null) {
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(CurrentAffairDetails.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}