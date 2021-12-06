package com.example.hp.groomauto.Activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class SignUpActivity extends AppCompatActivity {
Button Signbtn;
ImageView img1,img2,img3,img4,img5,img6;
RadioButton malebtn,femalebtn;
EditText Fname,lastname,Phonetxt,Altertxt,Agetxt,Emailtxt,passwtxt;
    private static final String resister="https://www.groomauto.in/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Signbtn=(Button)findViewById(R.id.Signbtn);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);
        img6=(ImageView)findViewById(R.id.img6);
        malebtn=(RadioButton)findViewById(R.id.malebtn);
        femalebtn=(RadioButton)findViewById(R.id.femalebtn);
        Fname=(EditText)findViewById(R.id.Fname);
        lastname=(EditText)findViewById(R.id.lastname);
        Phonetxt=(EditText)findViewById(R.id.Phonetxt);
        Altertxt=(EditText)findViewById(R.id.Altertxt);
        Agetxt=(EditText)findViewById(R.id.Agetxt);
        Emailtxt=(EditText)findViewById(R.id.Emailtxt);
        passwtxt=(EditText)findViewById(R.id.Passwtxt);
        Signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isEmpty(Fname)){
                    Fname.setError("Enter Name");
                }else if (Validation.isEmpty(lastname)){
                       lastname.setError("Enter lastname");
                }else if (Validation.isEmpty(Phonetxt)){
                       Phonetxt.setError("Enter Phone Numbar");
                }else if (Validation.isEmpty(Altertxt)){
                    Altertxt.setError("Enter Alternate Number");
                }else if(Validation.isEmpty(Agetxt)) {
                    Agetxt.setError("Enter your Age");
                }else if (!Validation.isValidEmail(Emailtxt.getText().toString())){
                    Emailtxt.setError("Enter valid email");
                }else if (Validation.isEmpty(passwtxt)){
                      passwtxt.setError("Enter Password");
                } else{
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("fname",Fname.getText().toString());
                        obj.put("lname",lastname.getText().toString());
                        obj.put("phn",Phonetxt.getText().toString());
                        obj.put("alt_phn",Altertxt.getText().toString());
                        obj.put("age",Agetxt.getText().toString());
                        obj.put("email",Emailtxt.getText().toString());
                        obj.put("password",passwtxt.getText().toString());
                        if (malebtn.isChecked()){
                            obj.put("gender",malebtn.getText().toString());
                        }else {
                            obj.put("gender",femalebtn.getText().toString());
                        }


                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,resister,  obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                JSONArray jsonArray=response.getJSONArray("data");
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                int val=jsonObject.getInt("Val");
                                if (val==0){
                                    Toast.makeText(getApplicationContext(),"Sign Up UnSuccessfull",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Sign Up Successfull",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    Volley.newRequestQueue(SignUpActivity.this).add(jsonObjectRequest);
                }
            }

        });
    }
}
