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

import com.winbee.quizhat.R;
import com.winbee.quizhat.Utils.OnlineTestData;
import com.winbee.quizhat.activity.OnlineTestActivity;
import com.winbee.quizhat.model.SLDetailsDataModel;
import com.winbee.quizhat.model.TLDetailsDataModel;

import java.util.List;

public class ThirdLevelTestListAdapter extends RecyclerView.Adapter<ThirdLevelTestListAdapter.CustomViewHolder> {
    private Context context;
    private List<TLDetailsDataModel> slDetailsDataModelList;
    private View view;

    public ThirdLevelTestListAdapter(Context context, List<TLDetailsDataModel> slDetailsDataModelList) {
        this.context = context;
        this.slDetailsDataModelList = slDetailsDataModelList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_second_level, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        final TLDetailsDataModel siacDetailsDataModel = slDetailsDataModelList.get(position);
        viewHolder.online_testname.setText(siacDetailsDataModel.getExamName());
        viewHolder.branch_live1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineTestData.bucketIDThirdLevel = slDetailsDataModelList.get(position).getChildId();
                Intent intent = new Intent(context, OnlineTestActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return slDetailsDataModelList.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout branch_live1;
        ImageView live_image;
        TextView online_testname;

        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            branch_live1 = itemView.findViewById(R.id.branch_live1);
            live_image = itemView.findViewById(R.id.live_image);
            online_testname = itemView.findViewById(R.id.online_testname);

        }

    }
}
