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
import com.winbee.quizhat.activity.CurrentAffairDetails;
import com.winbee.quizhat.activity.SecondLevelActivity;
import com.winbee.quizhat.model.CurrentAffairDataModel;
import com.winbee.quizhat.model.SectionDetailsDataModel;

import java.util.List;

public class CurrentAffairAdapter extends RecyclerView.Adapter<CurrentAffairAdapter.ViewHolder> {
    private Context context;
    private List<CurrentAffairDataModel> list1;

    public CurrentAffairAdapter(Context context, List<CurrentAffairDataModel> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_affair, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txt_course.setText(list1.get(position).getDisplay_name());

        holder.relativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CurrentAffairDetails.class);
                intent.putExtra("MONTH_ID", list1.get(position).getMonth_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout relativeButton;
//        private final TextView textButton;
        private TextView txt_course;
//        private ImageView course_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            course_image = itemView.findViewById(R.id.course_image);
            txt_course = itemView.findViewById(R.id.txt_course);
//            textButton = itemView.findViewById(R.id.textInfo);
            relativeButton = itemView.findViewById(R.id.Course_layout);


        }
    }
}

