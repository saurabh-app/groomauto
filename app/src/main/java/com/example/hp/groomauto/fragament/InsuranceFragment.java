package com.example.hp.groomauto.fragament;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.Adapter.RecyclerAdapter;
import com.example.hp.groomauto.Activitiy.Company;
import com.example.hp.groomauto.Activitiy.LoginActivity;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.util.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hp on 4/10/2018.
 */

@SuppressLint("ValidFragment")
public class InsuranceFragment extends Fragment {
    TextView vmtxt, vntxt, addresstxt, servicetxt, Rqtxt;
    EditText vnext, adext, sdext,vpyext, rqext;
    Spinner spinner;
    Button sbbtn;
    SharedPreferences preferences;
    ImageView Dateimg;
    Calendar calendar;
    RecyclerView recyclerView;
   
    ArrayList<Company> arrayList = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
    private ArrayList<String> string = new ArrayList<>();
    private ProgressDialog pd;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_insurance, container, false);
        vmtxt = (TextView) view.findViewById(R.id.vmtxt);
        vntxt = (TextView) view.findViewById(R.id.vntxt);
        addresstxt = (TextView) view.findViewById(R.id.addresstxt);
        servicetxt = (TextView) view.findViewById(R.id.serivcetxt);
        Rqtxt = (TextView) view.findViewById(R.id.Rqtxt);
        vpyext = (EditText) view.findViewById(R.id.vpyext);
        vnext = (EditText) view.findViewById(R.id.vnext);
        adext = (EditText) view.findViewById(R.id.adext);
        sdext = (EditText) view.findViewById(R.id.sdext);
        rqext = (EditText) view.findViewById(R.id.rqext);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        sbbtn = (Button) view.findViewById(R.id.sbbtn);
        Dateimg = (ImageView) view.findViewById(R.id.Dateimg);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        pd = new ProgressDialog(getContext());
//        pd.setTitle("Processing...");
//        pd.setMessage("Please wait.");
//        pd.setCancelable(false);
//        pd.setIndeterminate(true);
//        pd.show();
//        calendar = Calendar.getInstance();
//        preferences = getActivity().getSharedPreferences("UserData", 0);
//
//        string.add("Select Model");
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, string);
//        spinner.setAdapter(arrayAdapter);
//
//        recyclerAdapter = new RecyclerAdapter(getActivity(), arrayList, spinner);
//        GridLayoutManager glm = new GridLayoutManager(getActivity(), 5);
//        recyclerView.setLayoutManager(glm);
//        recyclerView.setAdapter(recyclerAdapter);

// Data urls to get server coding............

//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    pd.dismiss();
//                    JSONArray jsonArray = response.getJSONArray("vehicle_company");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        Company c = new Company();
//                        c.setCompany_name(jsonObject.getString("company_name"));
//                        c.setLogo(jsonObject.getString("logo"));
//                        arrayList.add(c);
//                    }
//                    recyclerAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

//Submit button coding..................................

        sbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getBoolean("isLogin", false)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    Toast.makeText(view.getContext(), "please log into your account", Toast.LENGTH_LONG).show();
                } else if (spinner.getSelectedItem().toString().equalsIgnoreCase("Select Model")) {
                    Toast.makeText(getContext(), "Select Company Model ", Toast.LENGTH_LONG).show();
                } else if (!Validation.isvalidNumber(vnext.getText().toString())) {
                    vnext.setError("Enter Vehicle number");
                } else if (Validation.isEmpty(vpyext)) {
                    vpyext.setError("Enter Your Address");
                } else if (Validation.isEmpty(sdext)) {
                    sdext.setError("Enter Date");
                } else if (Validation.isEmpty(rqext)) {
                    rqext.setError("Enter Repairing Query");
                } else {
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        String Userid = preferences.getString("id", null);
//                        String email = preferences.getString("email", null);
//                        String Uphone = preferences.getString("uphone", null);
//                        String fname = preferences.getString("first_name", null) + preferences.getString("last_name", null);
//                        jsonObject.put("vehicomp", RecyclerAdapter.cname);
//                        jsonObject.put("uid", Userid);
//                        jsonObject.put("uemail", email);
//                        jsonObject.put("uphone", Uphone);
//                        jsonObject.put("uname", fname);
//                        jsonObject.put("vehimodel", spinner.getSelectedItem().toString());
//                        jsonObject.put("vehinum", vnext.getText().toString());
//                        jsonObject.put("bdate", sdext.getText().toString());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL , jsonObject, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                JSONArray jsonArray = response.getJSONArray("data");
//                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                int val = jsonObject.getInt("Val");
//                                if (val == 0) {
//                                    Toast.makeText(view.getContext(), "Submit Failed", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(view.getContext(), "Submit Sucesessfull", Toast.LENGTH_LONG).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    });
//                    Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
//
//                }
//
//
//            }
//        });

// Datepicker Image view coding...........................
//
//        Dateimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        sdext.setText(dayOfMonth + "/" + month + "/" + year);
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//            }
//        });

                }

            }
        });
        return view;
    }
    }