package com.shpun.zhihudaily;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shpun.zhihudaily.Adapter.StoryAdapter;
import com.shpun.zhihudaily.Adapter.ViewPagerAdapter;
import com.shpun.zhihudaily.Gson.Latest;
import com.shpun.zhihudaily.Retrofit.ZhiHuDailyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.shpun.zhihudaily.R;

/**
 *
 * 显示主页
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private Context mContext;


    private List<View> viewPagerItem;

    private ViewPagerAdapter viewPagerAdapter;
    private StoryAdapter storyAdapter;

    private ViewPager viewPager;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment,container,false);

        //initViewpager();

        viewPager = (ViewPager)view.findViewById(R.id.home_viewPager);
        viewPagerAdapter = new ViewPagerAdapter(mContext);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout dotIndicator = (TabLayout)view.findViewById(R.id.dot_indicator);
        dotIndicator.setupWithViewPager(viewPager,true);

        //initStory();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.home_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        storyAdapter =  new StoryAdapter();
        recyclerView.setAdapter(storyAdapter);


        final SwipeRefreshLayout homeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.home_refresh);
        homeRefresh.setColorSchemeColors(Color.BLUE,Color.GREEN);
        homeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLatest();
                homeRefresh.setRefreshing(false);
            }
        });

        initLatest();

        return view;
    }

    private void initLatest(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZhiHuDailyService zhiHuDailyService = retrofit.create(ZhiHuDailyService.class);

        Call<Latest> latestCall = zhiHuDailyService.getLatest();

        latestCall.enqueue(new Callback<Latest>() {
            @Override
            public void onResponse(Call<Latest> call, Response<Latest> response) {
                viewPagerAdapter.setAdapter(response.body().topStories);
                viewPagerAdapter.notifyDataSetChanged();
                storyAdapter.setAdapter(response.body().stories);
                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Latest> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"retrofit Call<Latest> fail");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }
}
