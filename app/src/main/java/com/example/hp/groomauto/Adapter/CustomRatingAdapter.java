package com.example.hp.groomauto.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hp.groomauto.R;
import com.example.hp.groomauto.Activitiy.ServiceActivity;
import com.example.hp.groomauto.Activitiy.Servicerating;

import java.util.ArrayList;

/**
 * Created by hp on 4/26/2018.
 */

public class CustomRatingAdapter extends ArrayAdapter {
    Activity context;
    ArrayList<Servicerating> serviceratings;
    private TextView txtserv, txtdes;
    private RatingBar rating;
    private ImageView imgc;
    private SharedPreferences sharedpreference;


    public CustomRatingAdapter(@NonNull Activity context, ArrayList<Servicerating> serviceratings) {
        super(context, R.layout.servicerating);
        this.context = context;
        this.serviceratings = serviceratings;
        sharedpreference = context.getSharedPreferences("UserData", 0);

    }

    @Override
    public int getCount() {
        return serviceratings.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(R.layout.servicerating, parent, false);
        txtserv = (TextView) view.findViewById(R.id.txtserv);
        txtdes = (TextView) view.findViewById(R.id.txtdes);
        rating = (RatingBar) view.findViewById(R.id.rating);
        imgc = (ImageView) view.findViewById(R.id.imgc);

        txtserv.setText(serviceratings.get(position).getName());
        txtdes.setText(serviceratings.get(position).getDistance());
        try{
            rating.setRating(Float.parseFloat(serviceratings.get(position).getAverage_rating()));
        } catch (NumberFormatException e){

        }
//        imgc.setImageURI(Uri.parse("https://www.groomauto.in/package_image/" + serviceratings.get(position).getImage()));
//        imgc.setImageResource(serviceratings.get(position).getImage());
//        Glide.with(context)
//                .load("https://www.groomauto.in/package_image/" + serviceratings.get(position).getImage())
//                .into(imgc);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiceActivity.class);
                context.startActivity(intent);
                sharedpreference.edit().putString("dist", serviceratings.get(position).getDistance()).apply();
                sharedpreference.edit().putString("scenter", serviceratings.get(position).getService_center_rating()).apply();
            }
        });
        return view;
    }
}
