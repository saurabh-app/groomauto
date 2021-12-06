package com.example.hp.groomauto.Activitiy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 4/11/2018.
 */

public class ChangePassword extends AppCompatActivity {
    EditText oldpasstxt, newpasstxt, conpasstxt;
    Button subbtn;
    SharedPreferences preferences;
    private static final String REGISTRATION = "https://www.groomauto.in/android/change_password.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        oldpasstxt = (EditText) findViewById(R.id.oldpasstxt);
        newpasstxt = (EditText) findViewById(R.id.newpasstxt);
        conpasstxt = (EditText) findViewById(R.id.conpasstxt);
        subbtn = (Button) findViewById(R.id.subbtn);
        preferences = getApplicationContext().getSharedPreferences("UserData", 0);

        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userid =preferences.getString("id",null);
                if (Validation.isEmpty(oldpasstxt)) {
                    oldpasstxt.setError("Enter old password");
                } else if (Validation.isEmpty(newpasstxt)) {
                    newpasstxt.setError("Enter new password");
                } else if (Validation.isEmpty(conpasstxt)) {
                    conpasstxt.setError("Enter confirm password");
                } else if (!newpasstxt.getText().toString().equals(conpasstxt.getText().toString())){
                    Toast.makeText(getApplicationContext(),"pls Enter Confirm Your Password",Toast.LENGTH_LONG).show();
                } else {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("eid", userid);
                        obj.put("old_pass", oldpasstxt.getText().toString());
                        obj.put("new_pass", newpasstxt.getText().toString());
                        obj.put("con_pass", conpasstxt.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTRATION, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                            try {
//                                JSONArray jsonArray = response.getJSONArray("data");
//                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                int val = jsonObject.getInt("Val");
//                                if (val == 0) {
//                                    Toast.makeText(getApplicationContext(), "Sign Up UnSuccessfull", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Sign Up Successfull", Toast.LENGTH_LONG).show();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    Volley.newRequestQueue(ChangePassword.this).add(jsonObjectRequest);
                }
            }


        });


    }
}
