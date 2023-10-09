package com.winbee.quizhat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.model.AboutUsModel;
import com.winbee.quizhat.model.InstructionsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {
    private TextView tv_subject_name;
    ImageView WebsiteHome,img_share,companylogo,img_noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        tv_subject_name=findViewById(R.id.tv_subject_name);
        img_share=findViewById(R.id.img_share);

        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this, OverViewActivity.class);
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

        callInstructionService();

    }

    private void callInstructionService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<AboutUsModel> call = apiCAll.getAboutUs();
        call.enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 ) {
                    System.out.println("Suree body: " + response.body().getUpdate_user_status());
                    // Picasso.get().load(response.body().getFile()).into(img_video_thumbails);
                    tv_subject_name.setText(Html.fromHtml(response.body().getUpdate_user_status()));
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}