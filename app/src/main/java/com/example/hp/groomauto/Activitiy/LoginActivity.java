package com.example.hp.groomauto.Activitiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.util.Validation;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText Emailtext, Passtext;
    TextView Forgettext, Skiptext, Newusertext;
    Button Signinbtn, FacebookSbtn, GoogleSbtn;
    LoginButton login;
    private String URL = "https://www.groomauto.in/android/login_social.php";
    private String FEED = "https://www.groomauto.in/android/login.php";
    private SharedPreferences preference;
    private GoogleApiClient mGoogleApiClient;
    private String TAG;
    private ProgressDialog mProgressDialog;
    private String txtName;
    private String authid, personName, personPhotoUrl, email;
    private CallbackManager callbackManager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        Emailtext = (EditText) findViewById(R.id.Emailtext);
        Passtext = (EditText) findViewById(R.id.Passtext);
        Forgettext = (TextView) findViewById(R.id.Forgettext);
        Skiptext = (TextView) findViewById(R.id.Skiptext);
        Newusertext = (TextView) findViewById(R.id.Newusertext);
        Signinbtn = (Button) findViewById(R.id.Signinbtn);
        FacebookSbtn = (Button) findViewById(R.id.FacebookSbtn);
        GoogleSbtn = (Button) findViewById(R.id.GoogleSbtn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.serverclientid))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FacebookSbtn = (Button) findViewById(R.id.FacebookSbtn);
        login = (LoginButton) findViewById(R.id.login_button);
        login.setReadPermissions("email");
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        // login account

        preference = getSharedPreferences("UserData", 0);

        if (preference.getBoolean("isLogin", false)) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }


        FacebookSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.callOnClick();
            }
        });


        Signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isEmpty(Emailtext)) {
                    Emailtext.setError("Enter Your Email/Phone No.");
                } else if (Validation.isEmpty(Passtext)) {
                    Passtext.setError("Enter Your Password");
                } else {

                    JSONObject obj = new JSONObject();


                    try {
                        obj.put("email", Emailtext.getText().toString());
                        obj.put("password", Passtext.getText().toString());
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
                                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                                } else {
                                    preference.edit().putString("id", response.getString("id")).apply();
                                    preference.edit().putString("first_name", response.getString("first_name")).apply();
                                    preference.edit().putString("last_name", response.getString("last_name")).apply();
                                    preference.edit().putString("email", response.getString("email")).apply();
                                    preference.edit().putString("age", response.getString("age")).apply();
                                    preference.edit().putString("phone", response.getString("phone")).apply();
                                    preference.edit().putString("gender", response.getString("gender")).apply();
                                    preference.edit().putString("resi_address", response.getString("resi_address")).apply();
                                    preference.edit().putString("alter_number", response.getString("alter_number")).apply();
                                    preference.edit().putBoolean("isLogin", true).apply();
                                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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
                    Volley.newRequestQueue(LoginActivity.this).add(jsonObjectRequest);
                }
            }
        });

        Newusertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        Skiptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Forgettext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        GoogleSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, 007);
            }
        });
    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        try {
                            personName = json_object.getString("name");
                            email = json_object.getString("email");
                            authid = json_object.getString("id");
                            JSONObject profile_pic_data = new JSONObject(json_object.get("picture").toString());
                            JSONObject profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
                            personPhotoUrl = profile_pic_url.getString("url");
                            SocialLogin("facebook");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            personName = acct.getDisplayName();
            personPhotoUrl = acct.getPhotoUrl().toString();
            email = acct.getEmail();
            authid = acct.getIdToken();

            SocialLogin("google");


            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

        } else {

        }

    }

    public void SocialLogin(String social) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Name", personName);
            obj.put("Email", email);

            if (social.equalsIgnoreCase("google")) {
                obj.put("AuthId", authid.subSequence(0, 99));
            } else {
                obj.put("AuthId", authid);
            }
            obj.put("PictureUrl", personPhotoUrl);
            obj.put("Social", social);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        Log.d("objval", obj.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int val = jsonObject.getInt("Val");
                    if (val == 0) {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    } else {
                        preference.edit().putString("id", response.getString("id")).apply();
                        preference.edit().putString("first_name", response.getString("first_name")).apply();
                        preference.edit().putString("last_name", response.getString("last_name")).apply();
                        preference.edit().putString("email", response.getString("email")).apply();
                        preference.edit().putString("age", response.getString("age")).apply();
                        preference.edit().putString("phone", response.getString("phone")).apply();
                        preference.edit().putString("gender", response.getString("gender")).apply();
                        preference.edit().putString("resi_address", response.getString("resi_address")).apply();
                        preference.edit().putString("alter_number", response.getString("alter_number")).apply();
                        preference.edit().putBoolean("isLogin", true).apply();
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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
        Volley.newRequestQueue(LoginActivity.this).add(jsonObjectRequest);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 007) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


}
