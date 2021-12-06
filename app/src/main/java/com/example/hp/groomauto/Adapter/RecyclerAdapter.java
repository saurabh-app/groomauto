package com.example.hp.groomauto.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hp.groomauto.Activitiy.Company;
import com.example.hp.groomauto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 4/12/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private String URL_FEEDvm = "https://www.groomauto.in/android/get_vehicle_model.php";
    Activity context;
    ArrayList<Company> arrayList;
    ArrayList<String> string = new ArrayList<>();
    Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    public static String cname;

    public RecyclerAdapter(Activity context, ArrayList<Company> arrayList, Spinner spinner) {
        this.context = context;
        this.arrayList = arrayList;
        this.spinner = spinner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load("https://www.groomauto.in/bike_logo/" + arrayList.get(position).getLogo())
                .into(holder.imgLogo);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;

        MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getVehicleModels(arrayList.get(getAdapterPosition()).getCompany_name());
                    cname = arrayList.get(getAdapterPosition()).getCompany_name();
                }
            });
            imgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
        }
    }

    private void getVehicleModels(final String cname) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cname", cname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_FEEDvm, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("vehicle_model");
                    string.clear();
                    string.add("Select Model");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        string.add(jsonObject.getString("model_name"));
                    }
                    arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, string);
                    spinner.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context.getApplicationContext()).add(jsonObjectRequest);
    }
}
