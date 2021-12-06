package com.example.hp.groomauto.fragament;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.hp.groomauto.Activitiy.ChangePassword;
import com.example.hp.groomauto.Activitiy.EditProfile;
import com.example.hp.groomauto.R;

/**
 * Created by hp on 4/10/2018.
 */

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {
    WebView webView;
    TextView nametxt, contacttxt, emailtxt, addresstxt, agetxt, gendertxt, etprofile, chtxt;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        nametxt = (TextView) view.findViewById(R.id.nametxt);
        contacttxt = (TextView) view.findViewById(R.id.contacttext);
        emailtxt = (TextView) view.findViewById(R.id.emaitxt);
        addresstxt = (TextView) view.findViewById(R.id.addresstxt);
        agetxt = (TextView) view.findViewById(R.id.agetxt);
        gendertxt = (TextView) view.findViewById(R.id.gendertxt);
        etprofile = (TextView) view.findViewById(R.id.etprofile);
        chtxt = (TextView) view.findViewById(R.id.chtxt);

        preferences = getContext().getSharedPreferences("UserData", 0);

        nametxt.setText(preferences.getString("first_name", "") + " " + preferences.getString("last_name", ""));
        contacttxt.setText(preferences.getString("phone", ""));
        emailtxt.setText(preferences.getString("email", ""));
        addresstxt.setText(preferences.getString("resi_address", ""));
        agetxt.setText(preferences.getString("age", ""));
        gendertxt.setText(preferences.getString("gender", ""));
        chtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });
        etprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = getContext().getSharedPreferences("UserData", 0);

        nametxt.setText(preferences.getString("first_name", "") + " " + preferences.getString("last_name", ""));
        contacttxt.setText(preferences.getString("phone", ""));
        emailtxt.setText(preferences.getString("email", ""));
        addresstxt.setText(preferences.getString("resi_address", ""));
        agetxt.setText(preferences.getString("age", ""));
        gendertxt.setText(preferences.getString("gender", ""));
    }
}
