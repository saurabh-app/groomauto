package com.example.hp.groomauto.Activitiy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.groomauto.Adapter.CustomRatingAdapter;
import com.example.hp.groomauto.Adapter.Packages;
import com.example.hp.groomauto.Adapter.PlaceArrayAdapter;
import com.example.hp.groomauto.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 4/24/2018.
 */

public class Servicecenter extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView autosearch;
    ImageButton searchbtn;
    private String URL_FE = "https://www.groomauto.near_location.php";
    private static final String TAG = "Servicecenter";
    private GoogleApiClient mGoogleApiClient;
    private TextView mNameView;
    ArrayList<Servicerating> serviceratings = new ArrayList<>();
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicecenter_activity);
        autosearch = (AutoCompleteTextView) findViewById(R.id.autosearch);
        autosearch.setThreshold(3);
        mNameView = (TextView) findViewById(R.id.text);
        searchbtn = (ImageButton)findViewById(R.id.searchbtn);
        listView = (ListView) findViewById(R.id.list);
        customAdapter = new CustomRatingAdapter(this, serviceratings);
        listView.setAdapter(customAdapter);

        mGoogleApiClient = new GoogleApiClient.Builder(Servicecenter.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        autosearch.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autosearch.setAdapter(mPlaceArrayAdapter);
       searchbtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
//                jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("lat", lat);
//                    jsonObject.put("lng", lon);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        serviceratings.clear();
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for (int i = 0; i< jsonArray.length();i++){
//                                jsonObject = jsonArray.getJSONObject(i);
//                                Servicerating sr = new Servicerating();
//                                sr.setId(jsonObject.getString("id"));
//                                sr.setName(jsonObject.getString("name"));
//                                sr.setImage(jsonObject.getString("image"));
//                                sr.setAverage_rating(jsonObject.getString("average_rating"));
//                                sr.setDistance(jsonObject.getString("distance"));
//                                serviceratings.add(sr);
//                            }
//                            customAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                Volley.newRequestQueue(Servicecenter.this).add(jsonObjectRequest);
//
            }
        });
    }

    private CustomRatingAdapter customAdapter;
    private ArrayList<Packages> packages;
    private TextView txtserv, txtdes;
    private RatingBar rating;
    private JSONObject jsonObject;
    private String lat, lon;
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }

        private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                    return;
                }
                // Selecting the first object buffer.
                final Place place = places.get(0);
                CharSequence attributions = places.getAttributions();
                lon = String.valueOf(place.getLatLng().longitude);
                lat = String.valueOf(place.getLatLng().latitude);
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("lat", lat);
                    jsonObject.put("lng", lon);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "URL", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        serviceratings.clear();
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONArray jsonArray1 = response.getJSONArray("count");
                            jsonObject = jsonArray1.getJSONObject(0);
                            mNameView.setText(jsonObject.getString("total") + " service centers found");
                            for (int i = 0; i< jsonArray.length();i++){
                                jsonObject = jsonArray.getJSONObject(i);
                                Servicerating sr = new Servicerating();
                                sr.setId(jsonObject.getString("id"));
                                sr.setName(jsonObject.getString("name"));
                                sr.setImage(jsonObject.getString("image"));
                                sr.setAverage_rating(jsonObject.getString("average_rating"));
                                sr.setDistance(jsonObject.getString("distance"));
                                serviceratings.add(sr);
                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(Servicecenter.this).add(jsonObjectRequest);

            }
        };
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

    }
}

