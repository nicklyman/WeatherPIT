package com.epicodus.weatherpit.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.weatherpit.R;
import com.epicodus.weatherpit.adapters.ForecastListAdapter;
import com.epicodus.weatherpit.models.Forecast;
import com.epicodus.weatherpit.services.ForecastService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SevenDayForecastActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = SevenDayForecastActivity.class.getSimpleName();
    @Bind(R.id.forecastLinkTextView) TextView mAPILink;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private ForecastListAdapter mAdapter;

    public ArrayList<Forecast> mForecasts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_day_forecast);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mForecasts);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 0.0);
        Log.v("passedLat2: ", String.valueOf(lat));
        double lng = intent.getDoubleExtra("lng", 0.0);
        Log.v("passedLng2: ", String.valueOf(lng));

//        LatLng inputPosition = intent.getParcelableExtra("coordinates");
//
//        String stringXY = String.valueOf(inputPosition);
//        Log.v("xy: ", stringXY);
//
//        String coordinates = (stringXY.split("[\\(\\)]")[1]);
//        List<String> coordinateList = Arrays.asList(coordinates.split(","));
//        String latitude = coordinateList.get(0);
//        Log.v("passedLat: ", latitude);
//        String longitude = coordinateList.get(1);
//        Log.v("passedLong: ", longitude);
//        double lat = Double.parseDouble(latitude);
//        double lng = Double.parseDouble(longitude);

        getDailySummary(lat, lng);

        mAPILink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mAPILink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://forecast.io/"));
            startActivity(webIntent);
        }
    }

    private void getDailySummary(Double lat, Double lng) {
        final ForecastService forecastService = new ForecastService();
        forecastService.findForecast(lat, lng, new Callback() {

        @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

        @Override
            public void onResponse(Call call, Response response) {
                mForecasts = forecastService.processResults(response);

                SevenDayForecastActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new ForecastListAdapter(getApplicationContext(), mForecasts);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SevenDayForecastActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}

