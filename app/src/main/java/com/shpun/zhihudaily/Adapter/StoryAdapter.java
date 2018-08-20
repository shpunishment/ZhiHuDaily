package com.shpun.zhihudaily.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Gson.Story;
import com.shpun.zhihudaily.R;

import java.util.List;

/**
 *
 * HomeFragment 中 RecyclerView 的适配器
 * 用于显示最新消息
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private List<Story> mStoryList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView newsTitle;
        ImageView newsImage;
        TextView newsJustTitle;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            newsTitle = (TextView)view.findViewById(R.id.story_title);
            newsImage = (ImageView)view.findViewById(R.id.story_image);
            newsJustTitle = (TextView)view.findViewById(R.id.story_just_title);
        }

    }

    public void setAdapter(List<Story> storyList){
        mStoryList = storyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String storyId = mStoryList.get(position).id;
                Intent intent = new Intent("com.shpun.zhihudaily.STORY_DETAIL");
                intent.putExtra("story_id",storyId);
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = mStoryList.get(position);
        if(story.images != null){
            holder.newsJustTitle.setVisibility(View.GONE);
            holder.newsTitle.setVisibility(View.VISIBLE);
            holder.newsImage.setVisibility(View.VISIBLE);

            holder.newsTitle.setText(story.title);
            Glide.with(mContext).load(story.images.get(0)).into(holder.newsImage);
        }else{
            holder.newsTitle.setVisibility(View.GONE);
            holder.newsImage.setVisibility(View.GONE);
            holder.newsJustTitle.setVisibility(View.VISIBLE);

            holder.newsJustTitle.setText(story.title);
        }
    }

    @Override
    public int getItemCount() {
        return mStoryList == null?0:mStoryList.size();
    }
}
