package com.example.hp.groomauto.Activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hp.groomauto.R;

/**
 * Created by maxgen1 on 2/9/2018.
 */

public class PaymentSuccess extends Activity {
    String bid,pkgnm,pkgprice;
    TextView tbid,tpkgnm,tpkgprice;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentsuccess);
        bid=getIntent().getStringExtra("booking_id");
        pkgnm=getIntent().getStringExtra("package_name");
        pkgprice=getIntent().getStringExtra("package_price");

        tbid= findViewById(R.id.txtbookingid);
        tpkgnm= findViewById(R.id.txtpkgnm);
        tpkgprice= findViewById(R.id.txtpkgpz);

        tbid.setText(bid);
        tpkgnm.setText(pkgnm);
        tpkgprice.setText(pkgprice);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
