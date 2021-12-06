package com.example.hp.groomauto.Activitiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.R;
import com.sasidhar.smaps.payumoney.MakePaymentActivity;
import com.sasidhar.smaps.payumoney.PayUMoney_Constants;
import com.sasidhar.smaps.payumoney.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by hp on 5/9/2018.
 */

public class Booking_Preview extends AppCompatActivity {
    private TextView txtVehiComp, edvinum, tvServiceDate, tvServiceTime, tvaddress, tvmodel, txnmf, txemailf, txpkgnm, tvpkgpz;
    private SharedPreferences sharedPreferences;
    private EditText txphonef;
    private Button btnbookservi, btnbookback;
    private String url = "https://www.groomauto.in/android/booking.php";
    private JSONArray jsonArray;
    private TableRow tbRow6,tbRow7 ;
    String bid;


    String merchant_key="yHPyaPG6"; // test
    String salt="BytVsz2fmJ"; // test

    String SUCCESS = "https://www.payumoney.com/mobileapp/payumoney/success.php" ; // failed
    String FAILE = "https://www.payumoney.com/mobileapp/payumoney/failure.php" ;
    private HashMap<String,String> params;
    private String txnid;
    private TextView tvPickupAddress1,tvPickupTime1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_preview);
        txnmf = (TextView) findViewById(R.id.txnmf);
        txemailf = (TextView) findViewById(R.id.txemailf);
        txpkgnm = (TextView) findViewById(R.id.txpkgnm);
        tvpkgpz = (TextView) findViewById(R.id.tvpkgpz);
        txtVehiComp = (TextView) findViewById(R.id.spivehicomp);
        edvinum = (TextView) findViewById(R.id.edvinum);
        tvaddress = (TextView) findViewById(R.id.tvaddress);
        tvServiceDate = (TextView) findViewById(R.id.tvServiceDate);
        tvServiceTime = (TextView) findViewById(R.id.tvServiceTime);
        tvmodel = (TextView) findViewById(R.id.tvmodel);
        txphonef = (EditText) findViewById(R.id.txphonef);
        btnbookservi = (Button) findViewById(R.id.btnbookservi);
        btnbookback = (Button) findViewById(R.id.btnbookback);
        tvPickupAddress1=(TextView)findViewById(R.id.tvPickupAddress1);
        tvPickupTime1=(TextView)findViewById(R.id.tvPickupTime1);
        tbRow6=(TableRow)findViewById(R.id.rowpikup);
        tbRow7=(TableRow)findViewById(R.id.rowpikup1);
        sharedPreferences = getApplication().getSharedPreferences("User", 0);

        bid = getIntent().getStringExtra("bid");


        // set data booking preview page

        txtVehiComp.setText(getIntent().getStringExtra("vehicomp"));
        edvinum.setText(getIntent().getStringExtra("vechinum"));
        tvmodel.setText(getIntent().getStringExtra("vehimodel"));
        tvServiceTime.setText(getIntent().getStringExtra("btime"));
        tvServiceDate.setText(getIntent().getStringExtra("bdate"));
        tvaddress.setText(getIntent().getStringExtra("address1"));
        txnmf.setText(sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", ""));
        txemailf.setText(sharedPreferences.getString("email", null));
        txpkgnm.setText(sharedPreferences.getString("sername", null));
        tvpkgpz.setText(sharedPreferences.getString("serprice", null));
        txphonef.setText(sharedPreferences.getString("phone", null));
        if (getIntent().getStringExtra("pickup").equalsIgnoreCase("Yes")) {
            tbRow6.setVisibility(View.VISIBLE);
            tbRow7.setVisibility(View.VISIBLE);
            tvPickupAddress1.setText(sharedPreferences.getString("pickaddress",null));
            tvPickupTime1.setText(sharedPreferences.getString("picktime",null));
        }
        btnbookback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        btnbookservi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("eid", sharedPreferences.getString("id", ""));
                    obj.put("b_id", bid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       
                        try {
                            jsonArray = response.getJSONArray("booking");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            int val = jsonObject.getInt("Val");
                            if (val == 0) {
                                Toast.makeText(getApplicationContext(), "not Booking Conform", Toast.LENGTH_LONG).show();
                            } else if (val == 1) {

                                Toast.makeText(getApplicationContext(), "Booking Conform & view Booking Details", Toast.LENGTH_LONG).show();
//                                Intent intent=new Intent(Booking_Preview.this,PayMentGateWay.class);
//                                intent.putExtra("bid",bid );
//                                intent.putExtra("Service_name",sharedPreferences.getString("sername", ""));
//                                intent.putExtra("Service_price",sharedPreferences.getString("serprice", ""));
//                                intent.putExtra("uid",sharedPreferences.getString("id", ""));
//                                intent.putExtra("uname",sharedPreferences.getString("first_name", "") +" " + (sharedPreferences.getString("last_name", "")));
//                                intent.putExtra("uemail",sharedPreferences.getString("email", ""));
//                                intent.putExtra("uphone",sharedPreferences.getString("phone", ""));


                                params= new HashMap<String,String>();
                                Random rand = new Random();
                                String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
                                txnid=hashCal("SHA-256",rndm).substring(0,20);

                                params.put(PayUMoney_Constants.KEY, merchant_key);
                                params.put(PayUMoney_Constants.TXN_ID, txnid);
                                params.put(PayUMoney_Constants.AMOUNT, sharedPreferences.getString("serprice", ""));
                                params.put(PayUMoney_Constants.PRODUCT_INFO, "product_info");
                                params.put(PayUMoney_Constants.FIRST_NAME, sharedPreferences.getString("first_name", ""));
                                params.put(PayUMoney_Constants.EMAIL, sharedPreferences.getString("email", ""));
                                params.put(PayUMoney_Constants.PHONE, sharedPreferences.getString("phone", ""));
                                params.put(PayUMoney_Constants.SURL, "https://www.payumoney.com/mobileapp/payumoney/success.php");
                                params.put(PayUMoney_Constants.FURL, "https://www.payumoney.com/mobileapp/payumoney/failure.php");
                                params.put(PayUMoney_Constants.UDF1, "");
                                params.put(PayUMoney_Constants.UDF2, "");
                                params.put(PayUMoney_Constants.UDF3, "");
                                params.put(PayUMoney_Constants.UDF4, "");
                                params.put(PayUMoney_Constants.UDF5, "");

                                String hash = Utils.generateHash(params, salt);

                                params.put(PayUMoney_Constants.HASH, hash);
                                params.put(PayUMoney_Constants.SERVICE_PROVIDER, "payu_paisa");

                                Intent intent = new Intent(Booking_Preview.this, MakePaymentActivity.class);
                                intent.putExtra(PayUMoney_Constants.ENVIRONMENT, PayUMoney_Constants.ENV_DEV);
                                intent.putExtra(PayUMoney_Constants.PARAMS, params);

                                startActivityForResult(intent, PayUMoney_Constants.PAYMENT_REQUEST);
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
                Volley.newRequestQueue(Booking_Preview.this).add(jsonObjectRequest);
            }
        });
    }

    public String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();



            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }

        }catch(NoSuchAlgorithmException nsae){ }

        return hexString.toString();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayUMoney_Constants.PAYMENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Payment Success,", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Booking_Preview.this,PaymentSuccess.class);
                intent.putExtra("booking_id",bid);
                intent.putExtra("test",sharedPreferences.getString("first_name", "") +" " + (sharedPreferences.getString("last_name", "")));
                intent.putExtra("package_name",sharedPreferences.getString("sername", ""));
                intent.putExtra("package_price",sharedPreferences.getString("serprice", ""));
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Payment Failed | Cancelled.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
