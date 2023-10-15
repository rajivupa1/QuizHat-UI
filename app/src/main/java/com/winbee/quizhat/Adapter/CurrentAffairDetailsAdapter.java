package com.winbee.quizhat.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.quizhat.R;
import com.winbee.quizhat.Utils.URLImageParser;
import com.winbee.quizhat.model.CurrentAffairDataModel;
import com.winbee.quizhat.model.CurrentAffairDetailsDataModel;

import java.util.List;

public class CurrentAffairDetailsAdapter extends RecyclerView.Adapter<CurrentAffairDetailsAdapter.ViewHolder> {
    private Context context;
    private List<CurrentAffairDetailsDataModel> list1;

    public CurrentAffairDetailsAdapter(Context context, List<CurrentAffairDetailsDataModel> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public CurrentAffairDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_affair_details, parent, false);
        return new CurrentAffairDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentAffairDetailsAdapter.ViewHolder holder, final int position) {

        holder.title.setText(list1.get(position).getTitle());
        holder.date.setText(list1.get(position).getDate());
        holder.txt_htmlcontent.loadData(list1.get(position).getDescription(), "text/html", "utf-8");
        if(!list1.get(position).getImg_url().isEmpty())
        {
            holder.blogImg.setVisibility(View.VISIBLE);
            String blogimgUrl = list1.get(position).getImg_url().trim();
            Picasso.get().load(blogimgUrl.replace("https://","https://www.")).placeholder(R.drawable.logo).fit().into(holder.blogImg);
           // Picasso.get().load(list1.get(position).getImg_url()).into(holder.blogImg);
        }
        else
        {
            holder.blogImg.setVisibility(View.GONE);
        }

        //holder.htmlcontent.setText(Html.fromHtml(list1.get(position).getDescription()));

    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date,title,htmlcontent;
        WebView txt_htmlcontent;
        private ImageView blogImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txt_date);
            title = itemView.findViewById(R.id.txt_course);
            txt_htmlcontent = itemView.findViewById(R.id.txt_htmlcontent);
            blogImg = itemView.findViewById(R.id.blogImg);
        }
    }
}

