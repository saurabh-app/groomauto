package com.example.hp.groomauto.fragament;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.hp.groomauto.R;

/**
 * Created by hp on 4/9/2018.
 */

public class AboutsFragment extends Fragment {
    WebView webView;
    private ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aboutsfragment,container,false);
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
        webView.loadUrl("https://www.groomauto.in/app_about.php");
        return view;

    }
}
