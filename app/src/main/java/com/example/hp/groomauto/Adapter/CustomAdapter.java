package com.example.hp.groomauto.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.Activitiy.Servicecenter;

import java.util.ArrayList;

/**
 * Created by hp on 4/23/2018.
 */

public class CustomAdapter extends ArrayAdapter {
    View view;
    Activity context;
    ArrayList<Packages> packages;
    private LayoutInflater inflater;
    private TextView smarttxt, rsetxt, dectxt;
    private ImageView mty;
    private Button Btnmore;
    private SharedPreferences sharedpreference;

    public CustomAdapter(@NonNull Activity context, ArrayList<Packages> packages) {

        super(context, R.layout.service_item);
        this.context = context;
        this.packages = packages;
        inflater = context.getLayoutInflater();
        sharedpreference = context.getSharedPreferences("UserData", 0);

    }

    @Override
    public int getCount() {
        return packages.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        view = inflater.inflate(R.layout.service_item, null);
        smarttxt = (TextView) view.findViewById(R.id.smarttxt);
        rsetxt = (TextView) view.findViewById(R.id.rsetxt);
        mty = (ImageView) view.findViewById(R.id.mty);
        Btnmore = (Button) view.findViewById(R.id.Btnmore);

        smarttxt.setText(packages.get(position).getP_name());
        rsetxt.setText("₹ " + packages.get(position).getPrice());
        Glide.with(context)
                .load("https://www.groomauto.in/package_image/" + packages.get(position).getImage())
                .into(mty);
        // mty.setImageIcon(packages.get(position).getImage());
        Btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alartdailogbox);
                mty = (ImageView) dialog.findViewById(R.id.mty);
                smarttxt = (TextView) dialog.findViewById(R.id.smarttxt);
                rsetxt = (TextView) dialog.findViewById(R.id.rsetxt);
                dectxt = (TextView) dialog.findViewById(R.id.dectxt);
                Btnmore = (Button) dialog.findViewById(R.id.Btnmore);
                dialog.setCancelable(true);
                Glide.with(context)
                        .load("https://www.groomauto.in/package_image/" + packages.get(position).getImage())
                        .into(mty);
                smarttxt.setText(packages.get(position).getP_name());
                rsetxt.setText("₹ " + packages.get(position).getPrice());
                dectxt.setText(packages.get(position).getCombo());
                Btnmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Servicecenter.class);
                        context.startActivity(intent);
                        sharedpreference.edit().putString("sername", packages.get(position).getP_name()).apply();
                        sharedpreference.edit().putString("serprice", packages.get(position).getPrice()).apply();
                        sharedpreference.edit().putString("pid", packages.get(position).getS_id()).apply();
                    }
                });
                dialog.show();
            }
        });

        return view;


    }
}
