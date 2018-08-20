package com.shpun.zhihudaily;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Adapter.EditorActivityAdapter;
import com.shpun.zhihudaily.Adapter.EditorAdapter;
import com.shpun.zhihudaily.Adapter.StoryAdapter;
import com.shpun.zhihudaily.Gson.Editor;
import com.shpun.zhihudaily.Gson.Story;
import com.shpun.zhihudaily.Gson.TypeStory;
import com.shpun.zhihudaily.Retrofit.ZhiHuDailyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class TypeFragment extends Fragment {

    private static final String TAG = "TypeFragment";

    private Context mContext;

    private ImageView typeBackground;
    private TextView typeDescription;

    private StoryAdapter storyAdapter;
    private EditorAdapter editorAdapter;

    private String storyType;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.type_fragment,container,false);

        typeBackground = (ImageView)view.findViewById(R.id.type_background);
        typeDescription = (TextView)view.findViewById(R.id.type_description);

        RecyclerView editorRecyclerView = (RecyclerView)view.findViewById(R.id.type_editor_recyclerview);
        editorRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        editorRecyclerView.setLayoutManager(layoutManager1);
        editorAdapter = new EditorAdapter();
        editorRecyclerView.setAdapter(editorAdapter);

        RecyclerView typeRecyclerView = (RecyclerView)view.findViewById(R.id.type_recyclerview);
        typeRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(mContext);
        typeRecyclerView.setLayoutManager(layoutManager2);
        storyAdapter = new StoryAdapter();
        typeRecyclerView.setAdapter(storyAdapter);

        MainActivity mainActivity = (MainActivity)getActivity();
        storyType = mainActivity.getStoryType();

        final SwipeRefreshLayout typeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.type_refresh);
        typeRefresh.setColorSchemeColors(Color.BLUE,Color.GREEN);
        typeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initType();
                typeRefresh.setRefreshing(false);
            }
        });

        initType();



        return view;
    }

    private void initType(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZhiHuDailyService zhiHuDailyService = retrofit.create(ZhiHuDailyService.class);

        Call<TypeStory> typeStoryCall = zhiHuDailyService.getTypeStory(storyType);

        typeStoryCall.enqueue(new Callback<TypeStory>() {
            @Override
            public void onResponse(Call<TypeStory> call, Response<TypeStory> response) {
                Glide.with(mContext).load(response.body().background).into(typeBackground);
                typeDescription.setText(response.body().description);

                editorAdapter.setAdapter((ArrayList<Editor>) response.body().editors);
                editorAdapter.notifyDataSetChanged();

                storyAdapter.setAdapter(response.body().stories);
                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TypeStory> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"retrofit type fragment fail");
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }
}
