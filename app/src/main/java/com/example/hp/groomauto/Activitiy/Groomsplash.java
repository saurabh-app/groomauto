package com.example.hp.groomauto.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.groomauto.R;

/**
 * Created by hp on 5/3/2018.
 */

public class Groomsplash extends AppCompatActivity {

    private Thread splashTread;
    private int _splashTime=3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groomsplash_activity);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized(this){
                        wait(_splashTime);
                    }

                } catch(InterruptedException e) {}
                finally {
                    finish();

                    Intent i = new Intent();
                    i.setClass(Groomsplash.this, Silider_Load.class);
                    startActivity(i);

                    //stop();
                }
            }
        };

        splashTread.start();

    }
}
