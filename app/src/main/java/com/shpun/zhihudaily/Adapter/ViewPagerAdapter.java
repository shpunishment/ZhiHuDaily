package com.shpun.zhihudaily.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Gson.TopStory;
import com.shpun.zhihudaily.R;

import java.util.List;

/**
 *
 * HomeFragment 中的 ViewPager 适配器
 * 用于显示图片
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<TopStory> mTopStoryList;

    public ViewPagerAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setAdapter(List<TopStory> topStoryList){
        mTopStoryList = topStoryList;
    }

    @Override
    public int getCount() {
        return mTopStoryList == null?0:mTopStoryList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        Log.d("ViewPagerAdapater","mContext:"+mContext);

        View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item,null);
        ImageView topImage = (ImageView)view.findViewById(R.id.top_image);
        Glide.with(mContext)
                .load(mTopStoryList.get(position).image)
                .into(topImage);
        TextView topTitle = (TextView)view.findViewById(R.id.top_title);
        topTitle.setText(mTopStoryList.get(position).title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storyId = mTopStoryList.get(position).id;
                Intent intent = new Intent("com.shpun.zhihudaily.STORY_DETAIL");
                intent.putExtra("story_id",storyId);
                mContext.startActivity(intent);
            }
        });

        ((ViewPager)container).addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
