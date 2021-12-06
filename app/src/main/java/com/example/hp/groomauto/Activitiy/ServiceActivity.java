package com.example.hp.groomauto.Activitiy;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.Adapter.RecyclerAdapter;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.util.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hp on 4/11/2018.
 */

@SuppressLint("ValidFragment")
public class ServiceActivity extends AppCompatActivity {
    TextView vmtxt, vntxt, Addresstxt, servicetxt, Rqtxt;
    EditText vnext, adext, sdext, Timeext, Addresext, pickupext;
    Spinner spinner, spinnerpick;
    Button sbbtn;
    Switch swtch;
    RecyclerView recyclerView;
    ArrayList<Company> arrayList = new ArrayList<>();
    ArrayList<String>alist=new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
    private String UR = "https://www.groomauto.in/booking_preview.php";
    private String URL = "https://www.groomauto/android/get_vehicle_company.php";
    private View view;
    private ImageView dateimg;
    private Calendar calendar;
    private SharedPreferences preferences;
    private int mHour, mMinute;
    private String format;
    private TableRow tbRow6,tbRow7;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        vmtxt = (TextView) findViewById(R.id.vmtxt);
        vntxt = (TextView) findViewById(R.id.vntxt);
        Addresstxt = (TextView) findViewById(R.id.Addresstxt);
        servicetxt = (TextView) findViewById(R.id.serivcetxt);
        Rqtxt = (TextView) findViewById(R.id.Rqtxt);

        vnext = (EditText) findViewById(R.id.vnext);
        Timeext = (EditText) findViewById(R.id.Timeext);
        pickupext = (EditText) findViewById(R.id.pickupext);
        adext = (EditText) findViewById(R.id.adext);
        sdext = (EditText) findViewById(R.id.sdext);
        Addresext = (EditText) findViewById(R.id.Addresext);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerpick = (Spinner) findViewById(R.id.spinnerpick);
        tbRow6=(TableRow)findViewById(R.id.tbRow6);
        tbRow7=(TableRow)findViewById(R.id.tbRow7);
        dateimg = (ImageView) findViewById(R.id.dateimg);
        swtch = (Switch) findViewById(R.id.swtch);
        sbbtn = (Button) findViewById(R.id.sbbtn);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        preferences = getApplication().getSharedPreferences("UserData", 0);
        calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        recyclerAdapter = new RecyclerAdapter(this, arrayList, spinner);
        GridLayoutManager glm = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(recyclerAdapter);

        alist.add("Select Model");
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,alist));

        alist = new ArrayList<>();
        alist.add("Select");
        alist.add("09:00am-10:00am");
        alist.add("10:00am-11:00am");
        alist.add("11:00am-12:00am");
        alist.add("12:00am-01:00pm");
        alist.add("01:00pm-02:00pm");
        alist.add("02:00pm-03:00pm");
        alist.add("03:00pm-04:00pm");
        alist.add("04:00pm-05:00pm");
        alist.add("05:00pm-06:00pm");
        alist.add("06:00pm-07:00pm");
        alist.add("08:00pm-09:00pm");
        alist.add("09:00pm-10:00pm");
        alist.add("10:00pm-11:00pm");
        alist.add("11:00pm-12:00pm");
        spinnerpick.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,alist));

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tbRow6.setVisibility(View.VISIBLE);
                    tbRow7.setVisibility(View.VISIBLE);
                }else {
                    tbRow6.setVisibility(View.GONE);
                    tbRow7.setVisibility(View.GONE);
                }
            }
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("vehicle_company");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Company c = new Company();
                        c.setCompany_name(jsonObject.getString("company_name"));
                        c.setLogo(jsonObject.getString("logo"));
                        arrayList.add(c);
                    }
                    recyclerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);

//Sumbit Button

        sbbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!preferences.getBoolean("isLogin", false)) {
                    Intent intent = new Intent(ServiceActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(view.getContext(), "please log in your account", Toast.LENGTH_LONG).show();
                } else if (spinner.getSelectedItem().toString().equalsIgnoreCase("Select Model")) {
                    Toast.makeText(getApplication(), "Select Company Model ", Toast.LENGTH_LONG).show();
                } else if (!Validation.isvalidNumber(vnext.getText().toString())) {
                    vnext.setError("Enter Vehicle number");
                } else if (Validation.isEmpty(sdext)) {
                    sdext.setError("Enter Your Date");
                } else if (Validation.isEmpty(Timeext)) {
                    Timeext.setError("Enter Time");
                } else if (Validation.isEmpty(Addresext)) {
                    Addresext.setError("Enter Repairing Query");
                } else if (swtch.isChecked() && Validation.isEmpty(pickupext)) {
                    pickupext.setError("Enter Pickup address");
                }    else if (swtch.isChecked() && spinnerpick.getSelectedItem().toString().equalsIgnoreCase("Select pickup Time")) {
                    Toast.makeText(getApplication(), "Select pick Time", Toast.LENGTH_LONG).show();
                }else {


                JSONObject jsonObject = new JSONObject();
                    try {
                        String Userid = preferences.getString("id", null);
                        String Fastname = preferences.getString("first_name", null);
                        String lastname = preferences.getString("last_name", null);
                        String email = preferences.getString("email", null);
                        String phone = preferences.getString("phone", null);
                        String age = preferences.getString("age", null);
                        String gender = preferences.getString("gender", null);
                        String scenter = preferences.getString("scenter", null);
                        String distance = preferences.getString("dist", null);
                        String sername = preferences.getString("sername", null);
                        String serprice = preferences.getString("serprice", null);
                        String pid = preferences.getString("pid", null);
                        jsonObject.put("uid", Userid);
                        jsonObject.put("uname", Fastname);
                        jsonObject.put("ulname", lastname);
                        jsonObject.put("uemail", email);
                        jsonObject.put("uphone", phone);
                        jsonObject.put("uage", age);
                        jsonObject.put("ugender", gender);
                        jsonObject.put("pid", pid);
                        jsonObject.put("scenter", scenter);
                        jsonObject.put("dist", distance);
                        jsonObject.put("sername", sername);
                        jsonObject.put("serprice", serprice);
                        jsonObject.put("address1", Addresext.getText().toString());
                        jsonObject.put("vehicomp", RecyclerAdapter.cname);
                        jsonObject.put("vehimodel", spinner.getSelectedItem().toString());
                        jsonObject.put("vehinum", vnext.getText().toString());
                        jsonObject.put("btime", Timeext.getText().toString());
                        jsonObject.put("bdate", sdext.getText().toString());
                        jsonObject.put("pickup", swtch.getTextOff().toString());
                        jsonObject.put("",spinnerpick.getSelectedItem().toString());
                        if (swtch.isChecked()) {
                            jsonObject.put("pickup", swtch.getTextOn().toString());
                            jsonObject.put("address", pickupext.getText().toString());
                            jsonObject.put("pitime", spinnerpick.getSelectedItem().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UR, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                int val = jsonObject.getInt("Val");
                                if (val == 0) {
                                    Toast.makeText(getApplicationContext(), "not Sumbitted", Toast.LENGTH_LONG).show();
                                } else if (val == 1) {
                                    Toast.makeText(getApplicationContext(), "Sumbitted Successfull", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ServiceActivity.this, Booking_Preview.class);
                                    intent.putExtra("bid", String.valueOf(jsonObject.getInt("b_id")));
                                    intent.putExtra("vechinum", vnext.getText().toString());
                                    intent.putExtra("vehimodel", spinner.getSelectedItem().toString());
                                    intent.putExtra("btime", Timeext.getText().toString());
                                    intent.putExtra("bdate", sdext.getText().toString());
                                    intent.putExtra("address1", Addresext.getText().toString());
                                    intent.putExtra("vehicomp", RecyclerAdapter.cname);
                                    intent.putExtra("pickup", swtch.getTextOff().toString());
                                    if (swtch.isChecked()) {
                                        intent.putExtra("pickup", swtch.getTextOn().toString());
                                        intent.putExtra("pickaddress", pickupext.getText().toString());
                                        intent.putExtra("picktime", spinnerpick.getSelectedItem().toString());

                                    }
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    Volley.newRequestQueue(ServiceActivity.this).add(jsonObjectRequest);

                }

            }
        });
// data pickar use
        dateimg.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ServiceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sdext.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        Timeext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ServiceActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        showTime(hourOfDay, minute);
                    }

                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        Timeext.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

}
