package com.example.hp.groomauto.Activitiy;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.util.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 4/11/2018.
 */

public class EditProfile extends AppCompatActivity {
   EditText nametxt,lnametxt,agetxt,mailtxt,phonetxt,altertxt,addresstxt;
   RadioButton malebtn,femalebtn, rbGender;
   RadioGroup rgGender;
   Button sumit;
   SharedPreferences preferences;
    private static final String REGISTRATION = "https://www.groomauto.in/android/edit_user.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        nametxt=(EditText)findViewById(R.id.nametxt);
        lnametxt=(EditText)findViewById(R.id.lnametxt);
        agetxt=(EditText)findViewById(R.id.agetxt);
        mailtxt=(EditText)findViewById(R.id.mailtxt);
        phonetxt=(EditText)findViewById(R.id.phonetxt);
        altertxt=(EditText)findViewById(R.id.altertxt);
        addresstxt=(EditText)findViewById(R.id.addresstxt);
        malebtn=(RadioButton)findViewById(R.id.malebtn);
        femalebtn=(RadioButton)findViewById(R.id.femalebtn);
        sumit=(Button)findViewById(R.id.submit);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);

        preferences = getApplicationContext().getSharedPreferences("UserData", 0);

        nametxt.setText(preferences.getString("first_name",""));
        phonetxt.setText(preferences.getString("phone",""));
        lnametxt.setText(preferences.getString("last_name",""));
        mailtxt.setText(preferences.getString("email",""));
        addresstxt.setText(preferences.getString("resi_address",""));
        agetxt.setText(preferences.getString("age",""));
        altertxt.setText(preferences.getString("alter_number",""));
        if (preferences.getString("gender","").equalsIgnoreCase("male")){
            malebtn.setChecked(true);
        } else if (preferences.getString("gender","").equalsIgnoreCase("female")){
            femalebtn.setChecked(true);
        }

        //.setText(preferences.getString("gender",""));
        sumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid =preferences.getString("id",null);
                rbGender = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
                if (Validation.isEmpty(nametxt)) {
                    nametxt.setError("Enter Your name");
                } else if (Validation.isEmpty(agetxt)) {
                    agetxt.setError("Enter Your Age");
                } else if (Validation.isEmpty(mailtxt)) {
                    mailtxt.setError("Enter valid email");
                } else {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("eid", userid);
                        obj.put("fname", nametxt.getText().toString());
                        obj.put("lname", lnametxt.getText().toString());
                        obj.put("age",agetxt.getText().toString());
                        obj.put("email", mailtxt.getText().toString());
                        obj.put("phn", phonetxt.getText().toString());
                        obj.put("resi_address", addresstxt.getText().toString());
                        obj.put("alt_phn", altertxt.getText().toString());
                        obj.put("gender", rbGender.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTRATION, obj, new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                int val = jsonObject.getInt("Val");
                                if (val == 0) {
                                    Toast.makeText(getApplicationContext(), "Sign Up UnSuccessfull", Toast.LENGTH_LONG).show();
                                } else {
                                    preferences.edit().putString("first_name", nametxt.getText().toString()).apply();
                                    preferences.edit().putString("last_name", lnametxt.getText().toString()).apply();
                                    preferences.edit().putString("email", mailtxt.getText().toString()).apply();
                                    preferences.edit().putString("age", agetxt.getText().toString()).apply();
                                    preferences.edit().putString("phone", phonetxt.getText().toString()).apply();
                                    preferences.edit().putString("gender", rbGender.getText().toString()).apply();
                                    preferences.edit().putString("resi_address", addresstxt.getText().toString()).apply();
                                    preferences.edit().putString("alter_number", altertxt.getText().toString()).apply();
                                    Toast.makeText(getApplicationContext(), "Sign Up Successfull", Toast.LENGTH_LONG).show();
                                    finish();
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
                    Volley.newRequestQueue(EditProfile.this).add(jsonObjectRequest);
                }
            }





        });

    }
}
