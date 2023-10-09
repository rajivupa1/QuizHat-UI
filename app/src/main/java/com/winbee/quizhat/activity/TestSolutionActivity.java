package com.winbee.quizhat.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.winbee.quizhat.Adapter.TestSolutionAdapter;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.ProgressBarUtil;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.AboutUsModel;
import com.winbee.quizhat.model.GetPdfModel;
import com.winbee.quizhat.model.SIADDSolutionDataModel;
import com.winbee.quizhat.model.SIADSolutionDataModel;
import com.winbee.quizhat.model.SIADSolutionMainModel;
import com.winbee.quizhat.model.ViewResult;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSolutionActivity extends AppCompatActivity {
    private RecyclerView test_recycler;
    private ArrayList<SIADDSolutionDataModel> list;
    private TestSolutionAdapter adapter;
    String UserID;
    PieChart pieChart;
    String isSubscribe,paperId;
    CardView downloadPdfBtn;
    private ProgressBarUtil progressBarUtil;
    private ImageView WebsiteHome,img_share,img_noti;
    private TextView tv_paper_name,tv_section_name,tv_total_question,tv_total_attempt,
            tv_total_correct,tv_total_review,tv_total_wrong,tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_solution);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        SharedPreferences sharedPreferences = getSharedPreferences("subscribePref", Context.MODE_PRIVATE);
        isSubscribe = sharedPreferences.getString("isSubscribe", "");
        progressBarUtil = new ProgressBarUtil(this);
        test_recycler = findViewById(R.id.test_solution_recycle);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        pieChart=findViewById(R.id.piechart);
        img_share=findViewById(R.id.img_share);
        tv_paper_name=findViewById(R.id.tv_paper_name);
        tv_section_name=findViewById(R.id.tv_section_name);
        tv_total_question=findViewById(R.id.tv_total_question);
        tv_total_attempt=findViewById(R.id.tv_total_attempt);
        tv_total_correct=findViewById(R.id.tv_total_correct);
        tv_total_review=findViewById(R.id.tv_total_review);
        tv_total_wrong=findViewById(R.id.tv_total_wrong);
        tv_user_name=findViewById(R.id.tv_user_name);
        downloadPdfBtn = findViewById(R.id.downloadpdf);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestSolutionActivity.this, OverViewActivity.class);
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
                    String shareMessage= "\nQuizHat download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });

        downloadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSubscribe.trim().equals("1"))
                {
                    //Call API to get the PDF URL
                    getPDFUrlToDownload();
                }
                else
                {
                    //Show subscribe dialog box if not subscribed
                    callSubscriptionDialog();
                }
            }
        });
        callApiService();
        callResultService();


    }

    private void getPDFUrlToDownload()
    {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<GetPdfModel> call = apiCAll.getPdfUrl(OnlineTestData.PaperID,UserID,"WB_010");
        call.enqueue(new Callback<GetPdfModel>() {
            @Override
            public void onResponse(Call<GetPdfModel> call, Response<GetPdfModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 ) {
                    System.out.println("Suree body: " + response.body());
                    if(response.body().getSuccess())
                    {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getUrl()));
                        startActivity(browserIntent);
                    }
                    else
                    {
                        Toast.makeText(TestSolutionActivity.this,"PDF not available!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPdfModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }

    private void callSubscriptionDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestSolutionActivity.this);
        alertDialogBuilder.setTitle("Download PDF");
        alertDialogBuilder
                .setMessage("आप क्विज़हैट के सब्सक्राइबर नहीं हैं। क्या आप ऐप के सभी टेस्ट और पीडीऍफ़ अनलॉक करना चाहते हैं?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //    OnlineQuestionActivity.this.finish();
                        Intent intent =new Intent(TestSolutionActivity.this,SubscriptionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void callResultService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ViewResult> call = apiCAll.viewResult("WB_010", OnlineTestData.paperID,UserID);
        call.enqueue(new Callback<ViewResult>() {
            @Override
            public void onResponse(Call<ViewResult> call, Response<ViewResult> response) {
                list = new ArrayList();
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    System.out.println("Suree body: "+response.body());
                    OnlineTestData.PaperID=response.body().getPaperID();
                    String Question= String.valueOf(response.body().getTotalQuestion());
                    tv_total_question.setText(Question);
                    String Attempt= String.valueOf(response.body().getAttempt());
                    tv_total_attempt.setText(Attempt);
                    String Correct= String.valueOf(response.body().getCorrect());
                    tv_total_correct.setText(Correct);
                    String Review= String.valueOf(response.body().getReview());
                    tv_total_review.setText(Review);
                    String Wrong= String.valueOf(response.body().getWrong());
                    tv_total_wrong.setText(Wrong);
                    String Total=response.body().getTotalMarks();
                    tv_section_name.setText(Total);
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Attempt",
                                    Integer.parseInt(tv_total_attempt.getText().toString()),
                                    Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Correct",
                                    Integer.parseInt(tv_total_correct.getText().toString()),
                                    Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Wrong++",
                                    Integer.parseInt(tv_total_wrong.getText().toString()),
                                    Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Total Review",
                                    Integer.parseInt(tv_total_review.getText().toString()),
                                    Color.parseColor("#29B6F6")));

                    // To animate the pie chart
                    pieChart.startAnimation();



                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
        tv_paper_name.setText(OnlineTestData.paperName);
        SharedPreferences sharedPreferences =getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        tv_user_name.setText(sharedPreferences.getString("name","name"));

    }


    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIADSolutionMainModel> call = apiCAll.getTestSolution(OnlineTestData.paperID, UserID);
        call.enqueue(new Callback<SIADSolutionMainModel>() {
            @Override
            public void onResponse(Call<SIADSolutionMainModel> call, Response<SIADSolutionMainModel> response) {
                SIADSolutionMainModel purchasedMainModel = response.body();
                int statusCode = response.code();
                // list = new ArrayList();
                if (statusCode == 200) {
                    if (response.body().getMessage().equalsIgnoreCase("true")) {
                        //courses.setVisibility(View.VISIBLE);
                        SIADSolutionDataModel siadSolutionDataModel = purchasedMainModel.getData();
                        list = new ArrayList<>(Arrays.asList(siadSolutionDataModel.getQuestionData()));
                        System.out.println("Suree body: " + response.body());
                        adapter = new TestSolutionAdapter(TestSolutionActivity.this, list);
                        test_recycler.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        //nocourse.setVisibility(View.VISIBLE);
                    }
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SIADSolutionMainModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }
}