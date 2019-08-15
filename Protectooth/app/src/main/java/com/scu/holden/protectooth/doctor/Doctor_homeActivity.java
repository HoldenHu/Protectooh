package com.scu.holden.protectooth.doctor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scu.holden.protectooth.BaseActivity;
import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.adapter.MyPagerAdapter;
import com.scu.holden.protectooth.component.NoScrollViewPager;
import com.scu.holden.protectooth.doctor.fragment.FragmentDoctorDataset;
import com.scu.holden.protectooth.doctor.fragment.FragmentDoctorPersonalPage;

import com.scu.holden.protectooth.navigationbar.NavigationTabBar;

import java.util.ArrayList;
import com.scu.holden.protectooth.doctor.fragment.FragmentComment;
/**
 * Created by mirrorssssssss on 11/3/17.
 */

public class Doctor_homeActivity extends BaseActivity{

    private FragmentComment fragmentComment;
    private FragmentDoctorPersonalPage fragmentDoctorPersonalPage;
    private FragmentDoctorDataset fragmentDoctorDataset;
    private FragmentTransaction transaction;
    private FragmentTransaction beginTransaction;
    private ArrayList<Fragment> fgList;
    private ScaleAnimation animation;
    private TranslateAnimation translateAnimation;
    private boolean isLogin;
    private RadioButton main_my_button;
    private RadioGroup radioGroup;
    private  Context context;
    private NavigationView navigationView;
    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragmentpager();
        context= this;
        initDrawer();
        initUI();
        //initIcon();

    }

    private void initFragmentpager() {

        fgList = new ArrayList<Fragment>();
        fragmentComment = new FragmentComment();

        fragmentDoctorDataset = new FragmentDoctorDataset();
        fragmentDoctorPersonalPage=new FragmentDoctorPersonalPage();

        fgList.add(fragmentComment);
        fgList.add(fragmentDoctorDataset);
        fgList.add(fragmentDoctorPersonalPage);


    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void initUI() {
        final NoScrollViewPager viewPager = (NoScrollViewPager) findViewById(R.id.vp_horizontal_ntb);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fgList);
        viewPager.setAdapter(myPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        NavigationTabBar.OnTabBarSelectedIndexListener onTabBarSelectedIndexListener = navigationTabBar.getOnTabBarSelectedIndexListener();
        navigationTabBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Holden","被点击了"+v.getId());

            }
        });
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("Holden","开始被选择"+index);
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("Holden","结束被选择"+index);
            }
        });
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.doctor_list),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.doctor_list_select))
                        .title("诊断")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.doctor_dataset),
                        Color.parseColor(colors[1]))

                        .title("查阅病例")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.my_press),
                        Color.parseColor(colors[4]))

                        .title("个人主页")
                        .build()
        );



        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }
    /**
     * 初始化抽屉
     */
    private void initDrawer() {

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {

            View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
            RelativeLayout headerBackground = (RelativeLayout) headerLayout.findViewById(R.id.header_background);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        }
    }
}
