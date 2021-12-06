package com.example.hp.groomauto.Activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText Emailtxt, Phonetxt;
    Button sbmbtn;
    private String URL = "https://www.groomauto.in/forgot_password.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Emailtxt = (EditText) findViewById(R.id.Emailtxt);
        Phonetxt = (EditText) findViewById(R.id.Phonetxt);
        sbmbtn = (Button) findViewById(R.id.sbmbtn);
        sbmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isEmpty(Emailtxt)) {
                    Emailtxt.setError("Enter Your Email");
                } else if (Validation.isEmpty(Phonetxt)) {
                    Phonetxt.setError("Enter Your Phone Number");

                    JSONObject obj = new JSONObject();


                    try {
                        obj.put("email", Emailtxt.getText().toString());
                        obj.put("contact", Phonetxt.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                int val = jsonObject.getInt("Val");
                                if (val == 0) {
                                    Toast.makeText(getApplicationContext(), "Email & Contact Number not match", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Cheak Your Email", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
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
                    Volley.newRequestQueue(ForgotPasswordActivity.this).add(jsonObjectRequest);
                }
            }
        });

    }
}
