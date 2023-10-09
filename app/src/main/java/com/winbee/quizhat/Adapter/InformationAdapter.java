package com.winbee.quizhat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.winbee.quizhat.activity.MainActivity;
import com.winbee.quizhat.activity.SecondLevelActivity;
import com.winbee.quizhat.activity.SubscriptionActivity;
import com.winbee.quizhat.activity.TestSubscriptionActivity;
import com.winbee.quizhat.model.OverviewDataModel;
import com.winbee.quizhat.model.SectionDetailsDataModel;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private Context context;
    private List<SectionDetailsDataModel> list1;

    public InformationAdapter(Context context, List<SectionDetailsDataModel> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txt_course.setText(list1.get(position).getBucketName());
        Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);

        SharedPreferences sharedPreferences =context.getSharedPreferences("subscribePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("isSubscribe",list1.get(position).getIsPaid().toString());
        editor.commit();

        if (list1.get(position).getIsPaid() == 0) {
            holder.textButton.setText("Demo");

        } else {
            holder.textButton.setText("View");

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineTestData.bucketID = list1.get(position).getBucketID();
                OnlineTestData.dataModel=list1.get(position);
                Intent intent = new Intent(context, SecondLevelActivity.class);
                context.startActivity(intent);
            }
        });


//            holder.layout_buy_test.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    OnlineTestData.Overview_id = list1.get(position).getBucketID();
//                    Intent intent = new Intent(context, SubscriptionActivity.class);
//                    context.startActivity(intent);
//                }
//            });


//        holder.layout_view_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnlineTestData.bucketID = list1.get(position).getBucketID();
//                Intent intent = new Intent(context, SecondLevelActivity.class);
//                context.startActivity(intent);
//            }
//        });
//
//        holder.layout_demo_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnlineTestData.bucketID = list1.get(position).getBucketID();
//                Intent intent = new Intent(context, SecondLevelActivity.class);
//                context.startActivity(intent);
//            }
//        });
//


    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout relativeButton;
        private final TextView textButton;
        private TextView txt_course;
        private ImageView course_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_image = itemView.findViewById(R.id.course_image);
            txt_course = itemView.findViewById(R.id.txt_course);
            textButton = itemView.findViewById(R.id.textInfo);
            relativeButton = itemView.findViewById(R.id.relativeInfo);


        }
    }
}

