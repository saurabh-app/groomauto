package com.example.hp.groomauto.fragament;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.groomauto.Adapter.ImageAdapter;
import com.example.hp.groomauto.Activitiy.HomeActivity;
import com.example.hp.groomauto.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hp on 4/9/2018.
 */

public class HomeFragment extends Fragment {
    LinearLayout line1, line2, line3;
    Activity activity;
    DrawerLayout drawer;
    FragmentTransaction ft;
    FragmentManager fragmentManager;
    private View view, view1;
    private int currentPage;
    private ViewPager mViewPager;
    private TextView[] dots;
    private LinearLayout ll_dots;
    private boolean condition = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefragment, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        ImageAdapter adapterView = new ImageAdapter(getActivity());
        mViewPager.setAdapter(adapterView);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
        line1 = (LinearLayout) view.findViewById(R.id.line1);
        line2 = (LinearLayout) view.findViewById(R.id.line2);
        line3 = (LinearLayout) view.findViewById(R.id.line3);
        view1 = inflater.inflate(R.layout.homefragment, null);



        addBottomDots(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == 3) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);

            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!condition)
                {
                    handler.removeCallbacks(this);
                }
                else
                {
                    handler.post(Update);
                }
            }
        }, 200, 5000);



        line1.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                Fragment fragment = new ServiceFragament();
                HomeActivity home = (HomeActivity) view.getContext();
                fragmentManager = home.getSupportFragmentManager();
                home.toolbar.setTitle("Service");
                FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment);
                ft.commit();
                // toolbar.setTitle("Service");
//               drawer.closeDrawer(GravityCompat.START);

            }
            });
        line2.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                Fragment fragment = new RepairFragment();
                HomeActivity home = (HomeActivity) view.getContext();
                fragmentManager = home.getSupportFragmentManager();
                home.toolbar.setTitle("Repair");
                FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment);
                ft.commit();
                //toolbar.getContext().
            }
            });
        line3.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                Fragment fragment = new InsuranceFragment();
                HomeActivity home = (HomeActivity) view.getContext();
                fragmentManager = home.getSupportFragmentManager();
                home.toolbar.setTitle("Insurance");
                FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment);
                ft.commit();

            }

            });
        return view;

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY));
            } else {
                dots[i].setText(Html.fromHtml("&#8226;"));
            }
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#0AFF12"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFDD19"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        condition = false;
    }
}
