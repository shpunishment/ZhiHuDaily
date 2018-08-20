package com.shpun.zhihudaily;

import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shpun.zhihudaily.Gson.Editor;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String storyType = "";

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 引入MainActivity布局文件activity_main.xml
        setContentView(R.layout.activity_main);

        // 显示toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);


        // 显示home 替换图标为ic_menu
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setTitle("首页");
        }

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        // navigationView 默认选中 首页
        navigationView.setCheckedItem(R.id.dailyHome);
        // navigationView 选项 选中事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.dailyHome:
                        actionBar.setTitle("首页");
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.dailyPsychology:
                        // 13
                        actionBar.setTitle("日常心理学");
                        storyType = "13";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.userRecommendDaily:
                        // 12
                        actionBar.setTitle("用户推荐日报");
                        storyType = "12";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.movieDaily:
                        // 3
                        actionBar.setTitle("电影日报");
                        storyType = "3";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.dontBored:
                        // 11
                        actionBar.setTitle("不许无聊");
                        storyType = "11";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.designDaily:
                        // 4
                        actionBar.setTitle("设计日报");
                        storyType = "4";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.companyDaily:
                        // 5
                        actionBar.setTitle("大公司日报");
                        storyType = "5";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.financeDaily:
                        // 6
                        actionBar.setTitle("财经日报");
                        storyType = "6";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.internetSecurity:
                        // 10
                        actionBar.setTitle("互联网安全");
                        storyType = "10";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.startGame:
                        // 2
                        actionBar.setTitle("开始游戏");
                        storyType = "2";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.musicDaily:
                        // 7
                        actionBar.setTitle("音乐日报");
                        storyType = "7";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.animeDaily:
                        // 9
                        actionBar.setTitle("动画日报");
                        storyType = "9";
                        replaceFragment(new TypeFragment());
                        break;
                    case R.id.sportsDaily:
                        // 8
                        actionBar.setTitle("体育日报");
                        storyType = "8";
                        replaceFragment(new TypeFragment());
                        break;
                    default:
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });



        replaceFragment(new HomeFragment());

    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_framelayout,fragment);
        transaction.commit();

    }



    // toolbar 上 菜单 点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }

        return true;
    }

    public String getStoryType(){
        return storyType;
    }

}
