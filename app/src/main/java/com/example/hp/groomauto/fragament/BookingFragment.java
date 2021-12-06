package com.example.hp.groomauto.fragament;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.Adapter.BookingAdapter;
import com.example.hp.groomauto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 4/10/2018.
 */

@SuppressLint("ValidFragment")
public class BookingFragment extends Fragment {
    RecyclerView listv;
    // TextView bdtxt,groomtxt,sgtxt,sgrtxt,mmtxt,mmrtxt,datetxt,dttimetxt,statustxt,stpandintxt;
    //Button showbtn,invoicebtn;
    SharedPreferences sharedPreferences;

    ArrayList<Booking> arrayList = new ArrayList<>();
    BookingAdapter ba;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_layout, container, false);
        listv = (RecyclerView) view.findViewById(R.id.Reclistv);
//        listv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ba = new BookingAdapter(getActivity(), arrayList);
//        listv.setAdapter(ba);

//        sharedPreferences = getContext().getSharedPreferences("UserData", 0);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("uid", sharedPreferences.getString("id", ""));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsonArray = response.getJSONArray("booking_history");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        Booking b = new Booking();
//                        b.setVehicle_company(jsonObject.getString("vehicle_company"));
//                        b.setVehicle_model(jsonObject.getString("vehicle_model"));
//                        b.setVehicle_number(jsonObject.getString("vehicle_number"));
//                        b.setPrice(jsonObject.getString("price"));
//                        b.setBook_id(jsonObject.getString("book_id"));
//                        b.setDate(jsonObject.getString("date"));
//                        b.setPackage_name(jsonObject.getString("package_name"));
//                        b.setTime(jsonObject.getString("time"));
//                        b.setStatus(jsonObject.getString("status"));
//                        b.setPickup_drop(jsonObject.getString("pickup_drop"));
//                        b.setPickup_address(jsonObject.getString("pickup_address"));
//                        b.setPickup_time(jsonObject.getString("pickup_time"));
//                        arrayList.add(b);
//                    }
//                    ba.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
//
        return view;
    }
    }
