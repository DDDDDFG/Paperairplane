package com.example.tangzhifeng.paperairplane.homepager;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.tangzhifeng.paperairplane.R;
import com.example.tangzhifeng.paperairplane.about.AboutActivity;
import com.example.tangzhifeng.paperairplane.data.zhihu.source.ZHihuDataRepository;
import com.example.tangzhifeng.paperairplane.data.zhihu.source.local.ZHihuLocalDataSource;
import com.example.tangzhifeng.paperairplane.data.zhihu.source.remote.ZhihuRemoteDataSource;
import com.example.tangzhifeng.paperairplane.homepager.guoke.GuokeFragment;
import com.example.tangzhifeng.paperairplane.homepager.zhihu.ZhiHuHomePresenter;
import com.example.tangzhifeng.paperairplane.homepager.zhihu.ZhihuHomeFagment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者: tangzhifeng on 2017/2/15.
 * 邮箱: tzfjobmail@gmail.com
 */

public class HomepagerActivity extends AppCompatActivity {


    ViewPagerAdapter viewPagerAdapter;

    ZhihuHomeFagment mZhihuHomeFagment;
    GuokeFragment mGuokeFragment;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepageractivity);
        //取消标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.inject(this);
        mZhihuHomeFagment = new ZhihuHomeFagment();
        new ZhiHuHomePresenter(mZhihuHomeFagment,
                new ZHihuDataRepository(ZhihuRemoteDataSource.getInstance(getApplicationContext()),
                        ZHihuLocalDataSource.getInstance(getApplicationContext())));
        mGuokeFragment = new GuokeFragment();


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_about:
                        Intent intent=new Intent(HomepagerActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        setupViewPager(mViewpager);

        mTabs.setupWithViewPager(mViewpager);
        //这段代码有预加载的功能但是会受到果壳的影响
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setCurrentItem(0);
        mTabs.setId(0);


        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer,menu);
        return true;
    }



    public void setupViewPager(ViewPager upViewPager) {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragmentAndTitle(mZhihuHomeFagment, "知乎日报");
        viewPagerAdapter.addFragmentAndTitle(new ZhihuHomeFagment(), "豆瓣时刻");
        viewPagerAdapter.addFragmentAndTitle(mGuokeFragment, "果壳分钟");
        mViewpager.setAdapter(viewPagerAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mTitle = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }

        public void addFragmentAndTitle(Fragment zhihuHomeFagment, String mTitle) {
            mFragmentList.add(zhihuHomeFagment);
            this.mTitle.add(mTitle);
        }

    }
}
