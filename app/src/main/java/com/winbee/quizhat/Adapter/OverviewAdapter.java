package com.winbee.quizhat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.quizhat.R;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.activity.MainActivity;
import com.winbee.quizhat.activity.SecondLevelActivity;
import com.winbee.quizhat.activity.TestSubscriptionActivity;
import com.winbee.quizhat.model.OverviewDataModel;
import com.winbee.quizhat.model.SectionDetailsDataModel;

import java.util.List;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {
    private Context context;
    private List<OverviewDataModel> list1;

    public OverviewAdapter(Context context, List<OverviewDataModel> horizontalList){
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cstom_overview,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (list1.get(position).getStatus().equals("Active")) {
           // holder.layout_coming_soon.setVisibility(View.GONE);
            holder.Course_layout.setVisibility(View.VISIBLE);
           // holder.txt_comingsoon.setVisibility(View.GONE);
            holder.txt_course.setText(list1.get(position).getName());
          //  holder.txt_discription.setText(list1.get(position).getDescription());
            Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
            holder.Course_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnlineTestData.TestName=list1.get(position).getName();
                    OnlineTestData.Overview_id = list1.get(position).getID();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if (list1.get(position).getStatus().equals("Coming Soon")){
            holder.Course_layout.setVisibility(View.GONE);
            holder.txt_course.setText(list1.get(position).getName());
          //  holder.txt_discription.setText(list1.get(position).getDescription());
            Picasso.get().load(list1.get(position).getItemAttachment()).placeholder(R.drawable.logo).fit().into(holder.course_image);
          //  holder.layout_coming_soon.setVisibility(View.VISIBLE);
            //holder.txt_comingsoon.setVisibility(View.VISIBLE);


        }else{
            System.out.println("failed");
        }








    }

    @Override
    public int getItemCount() {
        return list1==null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_course;
        private ImageView course_image;
        private LinearLayout Course_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_image = itemView.findViewById(R.id.course_image);
            txt_course = itemView.findViewById(R.id.txt_course);
            Course_layout = itemView.findViewById(R.id.Course_layout);
//            layout_coming_soon = itemView.findViewById(R.id.layout_coming_soon);
//            txt_discription = itemView.findViewById(R.id.txt_discription);

        }
    }
}

