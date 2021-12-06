package com.example.hp.groomauto.Activitiy;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.groomauto.R;
import com.example.hp.groomauto.fragament.AboutsFragment;
import com.example.hp.groomauto.fragament.BookingFragment;
import com.example.hp.groomauto.fragament.ContactFragment;
import com.example.hp.groomauto.fragament.HomeFragment;
import com.example.hp.groomauto.fragament.InsuranceFragment;
import com.example.hp.groomauto.fragament.ProfileFragment;
import com.example.hp.groomauto.fragament.RepairFragment;
import com.example.hp.groomauto.fragament.ServiceFragament;
import com.example.hp.groomauto.fragament.VouchersFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG;
    public Toolbar toolbar;
    DrawerLayout drawer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("UserData", 0);

        final Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,"Groomauto");
        ft.commit();
        toolbar.setTitle("Groomauto");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView Sign, txtusername;
        LinearLayout llHome, llAbout, llService, llRepair, llInsurance, llcontact, llprofile, llbook, llvouch, llSignout;
        llHome = (LinearLayout) navigationView.findViewById(R.id.llHome);
        llRepair = (LinearLayout) navigationView.findViewById(R.id.llRepair);
        llAbout = (LinearLayout) navigationView.findViewById(R.id.llAbout);
        llService = (LinearLayout) navigationView.findViewById(R.id.llService);
        llInsurance = (LinearLayout) navigationView.findViewById(R.id.llInsurance);
        llprofile = (LinearLayout) navigationView.findViewById(R.id.llprofile);
        llbook = (LinearLayout) navigationView.findViewById(R.id.llbook);
        llvouch = (LinearLayout) navigationView.findViewById(R.id.llvouch);
        llcontact = (LinearLayout) navigationView.findViewById(R.id.llcontact);
        llSignout = (LinearLayout) navigationView.findViewById(R.id.llSignot);
        Sign = (TextView) navigationView.findViewById(R.id.Sign);
        txtusername = (TextView) navigationView.findViewById(R.id.txtusername);
        txtusername.setText("Welcome " + sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", ""));

        if(sharedPreferences.getBoolean("isLogin" , false)){
            Sign.setText("Sign Out");
        }
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                // AppState.getSingleInstance().setLoggingOut(true);
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(HomeActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        llRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RepairFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                ft.commit();
                toolbar.setTitle("Repair");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "home");
                ft.commit();
                toolbar.setTitle("Groomauto");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AboutsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                ft.commit();
                toolbar.setTitle("Abouts");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ServiceFragament();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                ft.commit();
                toolbar.setTitle("Service");
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        llbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("isLogin",false)){
                    Fragment fragment = new BookingFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                    ft.commit();
                    toolbar.setTitle("Booking Details");
                    drawer.closeDrawer(GravityCompat.START);
                }else {
                    Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        llvouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new VouchersFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                ft.commit();
                toolbar.setTitle("Vouchers");

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ContactFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                ft.commit();
                toolbar.setTitle("Contact");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (sharedPreferences.getBoolean("isLogin",false)) {
                     Fragment fragment = new ProfileFragment();
                     FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                     ft.commit();
                     toolbar.setTitle("Profile");
                     drawer.closeDrawer(GravityCompat.START);
                 }else {
                     Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                     startActivity(intent);
                 }

            }
        });
        llInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new InsuranceFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "");
                ft.commit();
                toolbar.setTitle("Insurance");
                drawer.closeDrawer(GravityCompat.START);


            }
        });
        llSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                // AppState.getSingleInstance().setLoggingOut(true);
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(HomeActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().findFragmentById(R.id.frameLayout) !=
                getSupportFragmentManager().findFragmentByTag("home")) {
            Fragment fragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, "home");
            ft.commit();
            toolbar.setTitle("Groomauto");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeActivity.this.finish();
//                            Application.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void onNavigationClick(View v){
//        if(v.getId() == R.id.llHome){
//            Fragment fragment = new HomeFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llAbout){
//            Fragment fragment = new AboutFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llbook){
//            Fragment fragment = new BookingFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llcontact){
//            Fragment fragment = new ContactFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llInsurance){
//            Fragment fragment = new InsuranceFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//
//        }else if (v.getId()==R.id.llRepair){
//            Fragment fragment = new RepairFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llprofile){
//            Fragment fragment = new ProfileFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llService){
//            Fragment fragment = new ServiceActivity();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }else if (v.getId()==R.id.llvouch){
//            Fragment fragment = new VouchersFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
//            ft.commit();
//        }
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment fragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
            ft.commit();
            //   toolbar.setTitle("Groomauto");

        } else if (id == R.id.nav_about) {
            Fragment fragment = new AboutsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
            ft.commit();
        } else if (id == R.id.nav_Service) {

        } else if (id == R.id.nav_repair) {

        } else if (id == R.id.nav_insurance) {

        } else if (id == R.id.nav_contact) {
            Fragment fragment = new ContactFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment);
            ft.commit();

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_booking) {

        } else if (id == R.id.nav_vouchersicon) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    }

