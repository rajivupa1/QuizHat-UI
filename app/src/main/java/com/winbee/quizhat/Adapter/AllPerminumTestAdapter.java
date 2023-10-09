package com.winbee.quizhat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.quizhat.R;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.activity.SecondLevelActivity;
import com.winbee.quizhat.activity.TestSubscriptionActivity;
import com.winbee.quizhat.model.SectionDetailsDataModel;

import java.util.List;

public class AllPerminumTestAdapter extends RecyclerView.Adapter<AllPerminumTestAdapter.ViewHolder> {
    private Context context;
    private List<SectionDetailsDataModel> list1;

    public AllPerminumTestAdapter(Context context, List<SectionDetailsDataModel> horizontalList){
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


      
      if (list1.get(position).getIsCommingSoon().equalsIgnoreCase("No")){
          if (list1.get(position).getIsPremium().equalsIgnoreCase("1") && list1.get(position).getIsPaid().equals(0)){
              //premium hai but not purchased
              holder.txt_course.setText(list1.get(position).getBucketName());
              holder.txt_discount.setText(list1.get(position).getDisplayPrice());
              holder.txt_actual_price.setText(list1.get(position).getDiscountPrice());
              Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
              holder.layout_buy.setVisibility(View.VISIBLE);
              holder.img_rupee.setVisibility(View.VISIBLE);
              holder.txt_discount.setVisibility(View.VISIBLE);
              holder.layout_buy_test.setVisibility(View.GONE);
              holder.layout_free.setVisibility(View.GONE);
              holder.txt_type.setVisibility(View.GONE);
              holder.layout_download.setVisibility(View.GONE);
              holder.layout_view.setVisibility(View.GONE);
              holder.layout_purchased.setVisibility(View.GONE);
              holder.layout_coming_soon.setVisibility(View.GONE);
              holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      OnlineTestData.TestBuckedId=list1.get(position).getBucketID();
                      OnlineTestData.TestName=list1.get(position).getBucketName();
                      OnlineTestData.TestDiscription=list1.get(position).getDecription();
                      OnlineTestData.TotalTest=list1.get(position).getTotalTest();
                      OnlineTestData.TestDisplayPrice=list1.get(position).getDisplayPrice();
                      OnlineTestData.TestDiscountPrice=list1.get(position).getDiscountPrice();
                      Intent intent = new Intent(context, TestSubscriptionActivity.class);
                      context.startActivity(intent);
                  }
              });
          }else if (list1.get(position).getIsPremium().equalsIgnoreCase("1")){
              if ( list1.get(position).getIsPaid().equals(1)) {
                  //premium hai aur purchase also
                  holder.txt_course.setText(list1.get(position).getBucketName());
                  holder.txt_discount.setText(list1.get(position).getDisplayPrice());
                  holder.txt_actual_price.setText(list1.get(position).getDiscountPrice());
                  Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
                  holder.layout_buy.setVisibility(View.GONE);
                  holder.layout_buy_test.setVisibility(View.GONE);
                  holder.layout_download.setVisibility(View.GONE);
                  holder.layout_free.setVisibility(View.GONE);
                  holder.layout_view.setVisibility(View.GONE);
                  holder.txt_type.setVisibility(View.GONE);
                  holder.layout_purchased.setVisibility(View.VISIBLE);
                  holder.layout_coming_soon.setVisibility(View.GONE);
                  holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                          OnlineTestData.bucketID=list1.get(position).getBucketID();
                          OnlineTestData.bucketName=list1.get(position).getBucketName();
                          OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                          OnlineTestData.logData=list1.get(position).getLogData();
                          OnlineTestData.status=list1.get(position).getStatus();
                          OnlineTestData.totalTest=list1.get(position).getTotalTest();
                          Intent intent=new Intent(context, SecondLevelActivity.class);
                          context.startActivity(intent);

                      }
                  });
              }else if (list1.get(position).getIsPaid().equals(0)){
                  holder.txt_course.setText(list1.get(position).getBucketName());
                  holder.txt_discount.setText(list1.get(position).getDisplayPrice());
                  holder.txt_actual_price.setText(list1.get(position).getDiscountPrice());
                  Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
                  holder.layout_buy_test.setVisibility(View.VISIBLE);
                  holder.layout_free.setVisibility(View.GONE);
                  holder.layout_buy.setVisibility(View.GONE);
                  holder.img_rupee.setVisibility(View.GONE);
                  holder.txt_type.setVisibility(View.GONE);
                  holder.txt_discount.setVisibility(View.GONE);
                  holder.layout_download.setVisibility(View.GONE);
                  holder.layout_view.setVisibility(View.GONE);
                  holder.layout_purchased.setVisibility(View.GONE);
                  holder.layout_coming_soon.setVisibility(View.GONE);
                  holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          OnlineTestData.TestBuckedId = list1.get(position).getBucketID();
                          OnlineTestData.TotalTest = list1.get(position).getTotalTest();
                          OnlineTestData.TestName = list1.get(position).getBucketName();
                          Intent intent = new Intent(context, TestSubscriptionActivity.class);
                          context.startActivity(intent);
                      }
                  });
              }
          }else if (list1.get(position).getIsPremium().equalsIgnoreCase("0") && list1.get(position).getIsPaid().equals(0)){
              //free test
              holder.txt_course.setText(list1.get(position).getBucketName());
              holder.txt_discount.setText(list1.get(position).getDisplayPrice());
              holder.txt_actual_price.setText(list1.get(position).getDiscountPrice());
              Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
              holder.layout_buy.setVisibility(View.GONE);
              holder.layout_buy_test.setVisibility(View.GONE);
              holder.layout_free.setVisibility(View.VISIBLE);
              holder.txt_type.setVisibility(View.GONE);
              holder.layout_view.setVisibility(View.GONE);
              holder.img_rupee.setVisibility(View.GONE);
              holder.txt_discount.setVisibility(View.GONE);
              holder.layout_download.setVisibility(View.GONE);
              holder.layout_purchased.setVisibility(View.GONE);
              holder.layout_coming_soon.setVisibility(View.GONE);
              holder.img_rupee1.setVisibility(View.GONE);
              holder.txt_actual_price.setVisibility(View.GONE);
              holder.img_rupee.setVisibility(View.GONE);
              holder.txt_discount.setVisibility(View.GONE);
              holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                      OnlineTestData.bucketID=list1.get(position).getBucketID();
                      OnlineTestData.bucketName=list1.get(position).getBucketName();
                      OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                      OnlineTestData.logData=list1.get(position).getLogData();
                      OnlineTestData.status=list1.get(position).getStatus();
                      OnlineTestData.totalTest=list1.get(position).getTotalTest();
                      Intent intent=new Intent(context, SecondLevelActivity.class);
                      context.startActivity(intent);

                  }
              });
          }
          else if (list1.get(position).getIsPremium().equalsIgnoreCase("0") && list1.get(position).getIsPaid().equals(1)){
              //free test
              holder.txt_course.setText(list1.get(position).getBucketName());
              holder.txt_discount.setText(list1.get(position).getDisplayPrice());
              holder.txt_actual_price.setText(list1.get(position).getDiscountPrice());
              Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
              holder.layout_buy.setVisibility(View.GONE);
              holder.layout_buy_test.setVisibility(View.GONE);
              holder.layout_free.setVisibility(View.VISIBLE);
              holder.layout_purchased.setVisibility(View.GONE);
              holder.layout_coming_soon.setVisibility(View.GONE);
              holder.layout_view.setVisibility(View.GONE);
              holder.img_rupee.setVisibility(View.GONE);
              holder.txt_discount.setVisibility(View.GONE);
              holder.layout_download.setVisibility(View.GONE);
              holder.img_rupee1.setVisibility(View.GONE);
              holder.txt_type.setVisibility(View.GONE);
              holder.txt_actual_price.setVisibility(View.GONE);
              holder.img_rupee.setVisibility(View.GONE);
              holder.txt_discount.setVisibility(View.GONE);
              holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      OnlineTestData.CoachingID=list1.get(position).getCoachingID();
                      OnlineTestData.bucketID=list1.get(position).getBucketID();
                      OnlineTestData.bucketName=list1.get(position).getBucketName();
                      OnlineTestData.bucketInfo=list1.get(position).getBucketInfo();
                      OnlineTestData.logData=list1.get(position).getLogData();
                      OnlineTestData.status=list1.get(position).getStatus();
                      OnlineTestData.totalTest=list1.get(position).getTotalTest();
                      Intent intent=new Intent(context, SecondLevelActivity.class);
                      context.startActivity(intent);

                  }
              });
          }

      }else{
          holder.Course_layout.setVisibility(View.VISIBLE);
          holder.txt_course.setText(list1.get(position).getBucketName());
          holder.txt_course.setText(list1.get(position).getBucketName());
          holder.txt_discount.setText(list1.get(position).getDisplayPrice());
          holder.txt_actual_price.setText(list1.get(position).getDiscountPrice());
          Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
          holder.txt_type.setText("Test Series");
          holder.layout_coming_soon.setVisibility(View.VISIBLE);
          holder.layout_free.setVisibility(View.GONE);
          holder.layout_view.setVisibility(View.GONE);
          holder.layout_buy.setVisibility(View.GONE);
          holder.img_rupee.setVisibility(View.GONE);
          holder.txt_type.setVisibility(View.GONE);
          holder.txt_discount.setVisibility(View.GONE);
          holder.layout_buy_test.setVisibility(View.GONE);
          holder.layout_download.setVisibility(View.GONE);
          holder.layout_purchased.setVisibility(View.GONE);
          holder.img_rupee1.setVisibility(View.GONE);
          holder.layout_view.setVisibility(View.GONE);
          holder.txt_actual_price.setVisibility(View.GONE);
          holder.img_rupee.setVisibility(View.GONE);
          holder.txt_discount.setVisibility(View.GONE);
      }




    }

    @Override
    public int getItemCount() {
        return list1==null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_course,txt_discount,txt_actual_price,txt_type;
        private ImageView course_image,img_rupee1,img_rupee;
        private RelativeLayout Course_layout,layout_coming_soon,layout_free,layout_buy,layout_view,layout_purchased,layout_buy_test,layout_download;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course = itemView.findViewById(R.id.txt_course);
            img_rupee1 = itemView.findViewById(R.id.img_rupee1);
            img_rupee = itemView.findViewById(R.id.img_rupee);
            layout_coming_soon = itemView.findViewById(R.id.layout_coming_soon);
            layout_download = itemView.findViewById(R.id.layout_download);
            layout_free = itemView.findViewById(R.id.layout_free);
            layout_purchased = itemView.findViewById(R.id.layout_purchased);
            layout_buy_test = itemView.findViewById(R.id.layout_buy_test);
            layout_buy = itemView.findViewById(R.id.layout_buy);
            layout_view = itemView.findViewById(R.id.layout_view);
            Course_layout = itemView.findViewById(R.id.Course_layout);
            course_image=itemView.findViewById(R.id.course_image);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            txt_actual_price=itemView.findViewById(R.id.txt_actual_price);
            txt_type=itemView.findViewById(R.id.txt_type);
        }
    }
}

