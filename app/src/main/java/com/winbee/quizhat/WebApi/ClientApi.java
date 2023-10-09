package com.winbee.quizhat.WebApi;



import com.winbee.quizhat.model.AboutUsModel;
import com.winbee.quizhat.model.CurrentAffairDetailsMainModel;
import com.winbee.quizhat.model.CurrentAffairModel;
import com.winbee.quizhat.model.GetPdfModel;
import com.winbee.quizhat.model.InstructionsModel;
import com.winbee.quizhat.model.OverviewMainModel;
import com.winbee.quizhat.model.PaymentModel;
import com.winbee.quizhat.model.RazorPayModel;
import com.winbee.quizhat.model.RefCode;
import com.winbee.quizhat.model.RefUser;
import com.winbee.quizhat.model.ResultModel;
import com.winbee.quizhat.model.SIACDetailsMainModel;
import com.winbee.quizhat.model.SIADMainModel;
import com.winbee.quizhat.model.SIADSolutionMainModel;
import com.winbee.quizhat.model.SLDetailsMainModel;
import com.winbee.quizhat.model.SectionDetailsMainModel;
import com.winbee.quizhat.model.StartTestModel;
import com.winbee.quizhat.model.SubscriptionMainModel;
import com.winbee.quizhat.model.TLDetailsMainModel;
import com.winbee.quizhat.model.TestSeriesPayment;
import com.winbee.quizhat.model.TestTopRanker;
import com.winbee.quizhat.model.ViewResult;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApi {

    //login
    @FormUrlEncoded
    @POST("authorization.php")
    Call<RefCode> refCodeSignIn(
            @Field("username") String username,
            @Field("password") String password
    );

    //register
    @FormUrlEncoded
    @POST("register.php")
    Call<RefUser> refUserSignIn(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password
    );

    //test start
    @POST("fetch-section-details.php")
    Call<SectionDetailsMainModel> fetchSectionDetails(
            @Query("org_code") String org_code,
            @Query("userId") String userId,
            @Query("auth_code") String auth_code,
            @Query("level") String level,
            @Query("parent_id") String parent_id
    );

    //subscription plan
    @POST("fetch-section-subscription-plan.php")
    Call<SubscriptionMainModel> fetchSubscriptionDetails(
            @Query("ExamSectionId") String ExamSectionId,
            @Query("auth_code") String auth_code,
            @Query("user_id") String user_id
    );
    @POST("fetch-app-subscription-plan.php")
    Call<SubscriptionMainModel> fetchSubscriptionDetailsForApp(
            @Query("ExamSectionId") String ExamSectionId,
            @Query("auth_code") String auth_code,
            @Query("user_id") String user_id
    );


    //overview test
    @POST("fetch-overview.php")
    Call<OverviewMainModel> fetchOverviewDetails(
            @Query("org_code") String org_code,
            @Query("userId") String userId,
            @Query("auth_code") String auth_code,
            @Query("level") String level
    );

    //level 2
    @POST("fetch-section-star-link.php")
    Call<SLDetailsMainModel> fetchSLDetails(
            @Query("org_code") String org_code,
            @Query("auth_code") String auth_code,
            @Query("userId") String userId,
            @Query("parent_bucket_id") String parent_bucket_id,
            @Query("parent_bucket_level") String parent_bucket_level,
            @Query("level") String level
    );

    //level 3
    @POST("fetch-section-star-link.php")
    Call<TLDetailsMainModel> fetchTLDetails(
            @Query("org_code") String org_code,
            @Query("auth_code") String auth_code,
            @Query("userId") String userId,
            @Query("parent_bucket_id") String parent_bucket_id,
            @Query("parent_bucket_level") String parent_bucket_level,
            @Query("level") String level
    );

    //test list
    @POST("fetch-section-individual-assessment-cover-details.php")
    @FormUrlEncoded
    Call<SIACDetailsMainModel> fetchSIACDetails(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("user_code") String user_code
    );

    //test top rankers
    @POST("fetch-top-scorers.php")
    @FormUrlEncoded
    Call<TestTopRanker> fetchTopRanker(
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );

    //test instructions
    @POST("fetch-exam-instruction.php")
    @FormUrlEncoded
    Call<InstructionsModel> getInstruction(
            @Field("PaperID") String PaperID
    );

    //start test
    @POST("Start-Exam-Paper.php")
    @FormUrlEncoded
    Call<StartTestModel> getStartTest(
            @Field("OrgID") String OrgID,
            @Field("UserID") String UserID,
            @Field("PaperID") String PaperID,
            @Field("ExamStatus") String ExamStatus,
            @Field("Read_Instruction") String Read_Instruction
    );
    //test result
    @POST("view-result.php")
    @FormUrlEncoded
    Call<ViewResult> viewResult(
            @Field("OrgID") String OrgID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );

    //test solution
    @POST("view-solutions.php")
    @FormUrlEncoded
    Call<SIADSolutionMainModel> getTestSolution(
            @Field("paper_code") String paper_code,
            @Field("UserID") String UserID
    );

    //test solution
    @GET("getAboutUs.php")
    Call<AboutUsModel> getAboutUs();

    //test solution
    @GET("fetch-current-affair.php?level=L1")
    Call<CurrentAffairModel> getMonthList();

    //test solution
    @GET("fetch-current-affair.php?level=L2")
    Call<CurrentAffairDetailsMainModel> getBlogList(@Query("month_id") String month_id);

    //test solution
    @GET("getPDFSolution.php")
    Call<GetPdfModel> getPdfUrl(@Query("PaperID") String PaperID,
                                @Query("UserID") String UserID,
                                @Query("OrgID") String OrgID);


    //fetch test question
    @POST("fetch-section-individual-assessment-data.php")
    @FormUrlEncoded
    Call<SIADMainModel> fetchSIADDATA(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("paper_code") String paper_code
    );

    //submit test response
    @POST("Submit-Exam-Paper.php")
    @FormUrlEncoded
    Call<ResultModel> submitResponse(
            @Field("CoachingID") String CoachingID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID,
            @Field("Response") JSONArray jsonArray,
            @Field("Staticstics") String Staticstics,
            @Field("Submit_Exam_Paper") boolean Submit_Exam_Paper
    );

    @POST("get-order-id-api.php")
    @FormUrlEncoded
    Call<TestSeriesPayment> fetchPaymentData(
            @Field("course_id") String course_id,
            @Field("user_id") String user_id,
            @Field("amount_org_id") String amount_org_id,
            @Field("org_id") String org_id,
            @Field("type") String type,
            @Field("subscription_id") String subcription_id
    );
    @POST("api-config.php")
    Call<RazorPayModel> getRazorPay();
}
