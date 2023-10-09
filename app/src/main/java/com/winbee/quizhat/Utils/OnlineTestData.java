package com.winbee.quizhat.Utils;

import com.winbee.quizhat.model.SectionDetailsDataModel;
import com.winbee.quizhat.model.StudentQAModel;

import java.util.ArrayList;
import java.util.List;

public class OnlineTestData {
    public static String org_code="WB_010";
    public static String auth_code="1";
    public static String auth_code2="2";
    public static  String TestBuckedId="";
    public static  String TestName="";
    public static  String TestDisplayPrice="";
    public static  String TestDiscountPrice="";
    public static  String TestDiscription="";
    public static  String TotalTest="";
    public static String RazorpayOrderId="";
    public static String payment_key="";
    public static String CourseId="";
    public static String razorPayKey="";
    public static String razorPayKeyTest="";
    public static String Org_id="";
    public static String Overview_id="";
    public static String SubscriptionId="";


    //fetch-section-details.php data
    public static String CoachingID="";
    public static String bucketID="";
    public static String bucketIDThirdLevel="";
    public static String bucketName="";
    public static String bucketInfo="";
    public static String logData="";
    public static String status="";
    public static String totalTest="";
    public static String test_closed="";
    public static Boolean test_publish;

    //fetch-section-individual-assessment-cover-details.php data
    public static String bucketIDre="";
    public static String paperID="";
    public static String paperName="";
    public static String paperSection_Encode="";
    public static String isNegativeMarking_encode="";
    public static String time="";
    public static String isOpen="";
    public static String openDate="";
    public static String isNegativeMarking_decode="";
    public static String isPremium_encode="";
    public static String isPremium_decode="";
    public static String description="";
    //fetch-section-individual-assessment-data.php data

    public static String sectionCode="";
    public static String questionID="";
    public static String questionTitle="";
    public static String option1="";
    public static String option2="";
    public static String option3="";
    public static String option4="";
    public static String questionGUID="";

    public static String c="";
    public static String n="";

    //view-result.php data
    public static String PaperID="";
    public static String TotalQuestion="";
    public static String Attempt="";
    public static String Correct="";
    public static String Wrong="";
    public static String Review="";
    public static String TotalMarks="";

    public static List<StudentQAModel> studentQAModels=new ArrayList<>();

    public static SectionDetailsDataModel dataModel;
}
