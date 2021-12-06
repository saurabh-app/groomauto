package com.example.hp.groomauto.fragament;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.Adapter.CustomAdapter;
import com.example.hp.groomauto.Adapter.Packages;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.Activitiy.Servicecenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 4/23/2018.
 */

public class ServiceFragament extends Fragment {
    ListView listView;
    private String android="/get_package.php";
    ArrayList<Packages> packages = new ArrayList<>();
    Button mgmore, getsebtn, getsbtn;
    private ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter(getActivity(), packages);
        listView.setAdapter(customAdapter);
        mgmore = (Button) view.findViewById(R.id.mgmore);
        pd = new ProgressDialog(getContext());
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        mgmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.servicemg_activity);
                getsbtn = (Button) dialog.findViewById(R.id.getsbtn);
                getsebtn = (Button) dialog.findViewById(R.id.getsebtn);
                getsbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Servicecenter.class);
                        getActivity().startActivity(intent);
                    }
                });

                getsebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Servicecenter.class);
                        getActivity().startActivity(intent);
                    }
                });

                dialog.show();
            }
        });
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,"", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pd.dismiss();
                    JSONArray jsonArray = response.getJSONArray("package");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Packages c = new Packages();
                        c.setId(jsonObject.getString("id"));
                        c.setP_name(jsonObject.getString("p_name"));
                        c.setS_id(jsonObject.getString("s_id"));
                        c.setCombo(jsonObject.getString("combo"));
                        c.setImage(jsonObject.getString("image"));
                        c.setPrice(jsonObject.getString("price"));
                        packages.add(c);
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
        return view;
    }
}
