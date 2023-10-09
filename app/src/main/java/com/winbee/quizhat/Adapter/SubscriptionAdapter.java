package com.winbee.quizhat.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;
import com.winbee.quizhat.R;
import com.winbee.quizhat.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.Utils.SharedPrefManager;
import com.winbee.quizhat.WebApi.ClientApi;
import com.winbee.quizhat.activity.SubscriptionActivity;
import com.winbee.quizhat.activity.TestSubscriptionActivity;
import com.winbee.quizhat.model.SectionDetailsDataModel;
import com.winbee.quizhat.model.SubscriptionDataModel;
import com.winbee.quizhat.model.TestSeriesPayment;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {
    private Context context;
    private List<SubscriptionDataModel> list1;

    public SubscriptionAdapter(Context context, List<SubscriptionDataModel> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_subscription, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // holder.branch_sem.setVisibility(View.VISIBLE);
        holder.txt_course.setText("Subscription Plan");
        holder.text_amount.setText("Amount :- ₹" + String.valueOf(list1.get(position).getDiscountPrice()));
        holder.text_tensure.setText("Tenure :- " + String.valueOf(list1.get(position).getTenure()) + "days");

    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_tensure, text_amount, txt_course;
        private ImageView branch_image;
        private RelativeLayout branch_name1, branch_sem, layout_buy_test, layout_purchased_test;
        String UserName, UserId;
        String Sub_id;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Checkout.preload(context.getApplicationContext());
            txt_course = itemView.findViewById(R.id.txt_course);
            text_tensure = itemView.findViewById(R.id.text_tensure);
            text_amount = itemView.findViewById(R.id.text_amount);
            UserName = SharedPrefManager.getInstance(context).refCode().getUsername();
            UserId = SharedPrefManager.getInstance(context).refCode().getUserId();
            layout_buy_test = itemView.findViewById(R.id.layout_buy_test);
            layout_buy_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_payment_alert_test);
                    TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
                    TextView txt_course = dialog.findViewById(R.id.txt_course);
                    TextView txt_discount = dialog.findViewById(R.id.txt_discount);
                    TextView txt_actual_price = dialog.findViewById(R.id.txt_actual_price);
                    TextView txt_discount_price = dialog.findViewById(R.id.txt_discount_price);
                    txt_discount_price.setText(String.valueOf(list1.get(getAdapterPosition()).getTotalDiscount()));
                    txt_actual_price.setText(String.valueOf(list1.get(getAdapterPosition()).getTotalDiscount()));

                    txt_discount.setPaintFlags(txt_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    txt_discount.setText(String.valueOf(list1.get(getAdapterPosition()).getDisplayPrice()));

                    txt_course.setText("Subscription Plan");
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
            final String user_id = SharedPrefManager.getInstance(context).refCode().getUserId();
            final String Amount_org_id = String.valueOf(list1.get(getAdapterPosition()).getTotalDiscount());
            //final String Amount_org_id ="100";
            final String Org_id = "WB_010";
            Sub_id = list1.get(getAdapterPosition()).getSubscriptionId();


            TestSeriesPayment testSeriesPayment = new TestSeriesPayment();
            testSeriesPayment.setUser_id(user_id);
            testSeriesPayment.setAmount_org_id(Amount_org_id);
            testSeriesPayment.setOrg_id(Org_id);


            //  callRazorPayService();
            callPayment(testSeriesPayment);

        }

        private void callPayment(final TestSeriesPayment testSeriesPayment) {
            ClientApi apiCall = OnlineTestApiClient.getClient().create(ClientApi.class);
            Call<TestSeriesPayment> call = apiCall.fetchPaymentData("WB_010", testSeriesPayment.getUser_id(),
                    testSeriesPayment.getAmount_org_id(), "WB_010", "2", Sub_id);
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
                        System.out.println("===============" + UserName);
                        System.out.println("===============" + String.valueOf(list1.get(getAdapterPosition()).getTotalDiscount()));
                        Toast.makeText(context, "Payment Page Loading...", Toast.LENGTH_SHORT).show();
                        startPayment();
                    } else {
                        System.out.println("Sur: response code" + response.message());
                        Toast.makeText(context, "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<TestSeriesPayment> call, Throwable t) {
                    System.out.println("Suree: " + t.getMessage());

                    Toast.makeText(context, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        public void startPayment() {
            final Activity activity = (Activity) context;
            Checkout checkout = new Checkout();
            // checkout.setKeyID("rzp__live_4zz9QXpagJaPu7");
            //checkout.setKeyID("rzp_live_jwrVc5Dy8uusHK");
            // checkout.setKeyID("rzp__live_4zz9QXpagJaPu7");
            checkout.setKeyID(OnlineTestData.payment_key);

            String str = String.valueOf(list1.get(getAdapterPosition()).getTotalDiscount());
            Double inum = Double.parseDouble(str);
            Double sum = inum * 100;
            String str1 = Double.toString(sum);


            try {
                JSONObject options = new JSONObject();

                options.put("name", "QuizHat Subscription");
                options.put("send_sms_hash", false);
                options.put("description", "Purchase Test series");
                options.put("order_id", OnlineTestData.RazorpayOrderId);
                options.put("currency", "INR");
                options.put("amount", str1);
                options.put("send_sms_hash", true);
                checkout.open(activity, options);
            } catch (Exception e) {
                Log.e("tag", "Error in starting Razorpay Checkout", e);
            }

        }
    }
}

