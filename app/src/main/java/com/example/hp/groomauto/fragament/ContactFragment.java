package com.example.hp.groomauto.fragament;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.hp.groomauto.R;

import java.util.Collections;
import java.util.Random;

/**
 * Created by hp on 4/9/2018.
 */

public class ContactFragment extends Fragment {
    WebView webView;
    private ProgressDialog pd;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aboutsfragment,container,false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.Swiper);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setColorScheme(
                        android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                shuffle();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });


        pd = new ProgressDialog(getContext());
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();


        webView=(WebView)view.findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                pd.setProgress(progress);
                if (progress == 100) {
                    pd.dismiss();
                                  }

            }
        });
        webView.loadUrl("https://www.groomauto.in/app_contact.php");
        return view;
    }

    private void shuffle() {
        //Collections.shuffle(, new Random(System.currentTimeMillis()));

    }
}
