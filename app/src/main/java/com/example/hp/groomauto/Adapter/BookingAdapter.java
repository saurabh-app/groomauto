package com.example.hp.groomauto.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.groomauto.Activitiy.PDFinvoice;
import com.example.hp.groomauto.R;
import com.example.hp.groomauto.fragament.Booking;

import java.util.ArrayList;

/**
 * Created by hp on 4/16/2018.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {
    private Activity context;
    private ArrayList<Booking> bookings;


    public BookingAdapter(@NonNull Activity context, ArrayList<Booking> Booking) {
        this.context = context;
        this.bookings = Booking;
    }


    @NonNull
    @Override
    public BookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.bookingdetails, parent, false);

        return new BookingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingAdapter.MyViewHolder holder, final int position) {
// initialize key name

        holder.groomtxt.setText(bookings.get(position).getBook_id());
        holder.sgtxt.setText(bookings.get(position).getPackage_name());
        holder.sgrtxt.setText("₹"+ bookings.get(position).getPrice());
        holder.mmtxt.setText(bookings.get(position).getVehicle_company() + " - " + bookings.get(position).getVehicle_model());
        holder.mmrtxt.setText(bookings.get(position).getVehicle_number());
        holder.datetxt.setText(bookings.get(position).getDate());
        holder.dttimetxt.setText(bookings.get(position).getTime());
        holder.stpandintxt.setText(bookings.get(position).getStatus());


        holder.Showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.Showbtn.getText().toString().equalsIgnoreCase("Show more")) {
                    holder.lldetails.setVisibility(View.VISIBLE);
                    holder.Showbtn.setText("Hide");
                } else if (holder.Showbtn.getText().toString().equalsIgnoreCase("Hide")) {
                    holder.lldetails.setVisibility(View.GONE);
                    holder.Showbtn.setText("Show More");
                }
            }
        });
        holder.invoicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, PDFinvoice.class);

                intent.putExtra("booking_id",(bookings.get(position).getBook_id()));
                intent.putExtra("packageName",(bookings.get(position).getPackage_name()));
                intent.putExtra("vehicleCompany",(bookings.get(position).getVehicle_company()));
                intent.putExtra("vehicleModel", bookings.get(position).getVehicle_model());
                intent.putExtra("price",("₹"+ bookings.get(position).getPrice()));
                intent.putExtra("vehicleNumber",(bookings.get(position).getVehicle_number()));
                intent.putExtra("date",(bookings.get(position).getDate()));
                intent.putExtra("time",(bookings.get(position).getTime()));
                intent.putExtra("status",(bookings.get(position).getStatus()));
                intent.putExtra("pickupdrop",bookings.get(position).getPickup_drop());
                intent.putExtra("pickupAddress",bookings.get(position).getPickup_address());
                intent.putExtra("pickupTime",bookings.get(position).getPickup_time());
                intent.putExtra("fileName", Environment.getExternalStorageDirectory().getPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lldetails;
        private final TextView stpandintxt, bdtxt, groomtxt, sgtxt, sgrtxt, mmtxt, mmrtxt, datetxt, dttimetxt, statustxt;
        private Button Showbtn, invoicebtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            bdtxt = (TextView) itemView.findViewById(R.id.bdtxt);
            groomtxt = (TextView) itemView.findViewById(R.id.groomtxt);
            sgtxt = (TextView) itemView.findViewById(R.id.sgtxt);
            sgrtxt = (TextView) itemView.findViewById(R.id.sgrtxt);
            mmtxt = (TextView) itemView.findViewById(R.id.mmtxt);
            mmrtxt = (TextView) itemView.findViewById(R.id.mmnrtxt);
            datetxt = (TextView) itemView.findViewById(R.id.datetxt);
            dttimetxt = (TextView) itemView.findViewById(R.id.dttimetxt);
            statustxt = (TextView) itemView.findViewById(R.id.statustxt);
            stpandintxt = (TextView) itemView.findViewById(R.id.stpendingtxt);
            Showbtn = (Button) itemView.findViewById(R.id.Showbtn);
            invoicebtn = (Button) itemView.findViewById(R.id.invoicebtn);
            lldetails = (LinearLayout) itemView.findViewById(R.id.lldetails);

        }
    }
}
