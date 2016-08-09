package com.epicodus.weatherpit.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.weatherpit.Constants;
import com.epicodus.weatherpit.R;
import com.epicodus.weatherpit.adapters.ForecastListAdapter;
import com.epicodus.weatherpit.models.Forecast;
import com.epicodus.weatherpit.models.HistoricForecast;
import com.epicodus.weatherpit.services.HistoricForecastService;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class CurrentHistoricWeatherActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.forecastLinkTextView) TextView mAPILink;
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.sevenDayForecastButton) Button mSevenDayForecastButton;
    @Bind(R.id.historicWeatherTextView) TextView mHistoricWeatherTextView;
    @Bind(R.id.randomYearTextView) TextView mRandomYearTextView;
    @Bind(R.id.historicTemperaturesTextView) TextView mHistoricTemperatureTextView;
    @Bind(R.id.historicWeatherImageView) ImageView mHistoricWeatherImageView;

    public ArrayList<HistoricForecast> mHistoricForecasts = new ArrayList<>();

    private HistoricForecast mHistoricForecast;

    public double lat;
    public double lng;

    private String formattedYear;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_historic_weather);
        ButterKnife.bind(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mHistoricForecasts);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        LatLng inputPosition = intent.getParcelableExtra("coordinates");

        String stringXY = String.valueOf(inputPosition);
        Log.v("xy: ", stringXY);

        String coordinates = (stringXY.split("[\\(\\)]")[1]);
        List<String> coordinateList = Arrays.asList(coordinates.split(","));
        String latitude = coordinateList.get(0);
        Log.v("lat: ", latitude);
        String longitude = coordinateList.get(1);
        Log.v("long: ", longitude);
        double lat = Double.parseDouble(latitude);
        double lng = Double.parseDouble(longitude);


//        double lat = intent.getDoubleExtra("lat", 0.0);
//        double lng = intent.getDoubleExtra("lng", 0.0);
        long randomYear = getRandomYear();
        Date date = new Date(randomYear * 1000L);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        formattedYear = year.format(date);

        getHistoricDailySummary(lat, lng, randomYear);

        mSevenDayForecastButton.setOnClickListener(this);
        mAPILink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mSevenDayForecastButton) {
            Bundle coordinates = getIntent().getExtras();
            lat = coordinates.getDouble("lat");
            lng = coordinates.getDouble("lng");
            Intent intent = new Intent(CurrentHistoricWeatherActivity.this, SevenDayForecastActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);
        }
        if (view == mAPILink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://forecast.io/"));
            startActivity(webIntent);
        }
    }


    private void getHistoricDailySummary(Double lat, Double lng, long randomYear) {
        final HistoricForecastService historicForecastService = new HistoricForecastService();

        historicForecastService.findHistoricForecast(lat, lng, randomYear, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mHistoricForecasts = historicForecastService.processResults(response);

                CurrentHistoricWeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRandomYearTextView.setText(cityName + ": " + formattedYear);

                        if (mHistoricForecasts.get(0).getHistoricDailyIcon().equals("clear-day")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.clear_day).into(mHistoricWeatherImageView);
                        } if (mHistoricForecasts.get(0).getHistoricDailyIcon().equals("clear-night")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.clear_night).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("rain")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.rain).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("snow")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.snow).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("sleet")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.sleet).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("wind")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.wind).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("fog")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.fog).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("cloudy")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.cloudy).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("partly-cloudy-day")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.partly_cloudy_day).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("partly-cloudy-night")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.partly_cloudy_night).into(mHistoricWeatherImageView);
                        } if(mHistoricForecasts.get(0).getHistoricDailyIcon().equals("")){
                            Picasso.with(CurrentHistoricWeatherActivity.this).load(R.drawable.weather_clock_icon).into(mHistoricWeatherImageView);
                        }

                        mHistoricWeatherTextView.setText("Around this time of year in " + formattedYear + ", the forecast called for:\r\n" + mHistoricForecasts.get(0).getHistoricDailySummary());
                        mHistoricTemperatureTextView.setText("The high temperature was " + mHistoricForecasts.get(0).getHistoricDailyMaxTemp() + "°F.\r\n The low temperature was " + mHistoricForecasts.get(0).getHistoricDailyMinTemp() + "°F.");
                    }

                });
            }
        });
    }

    public long getRandomYear() {

        long currentSeconds = System.currentTimeMillis()/1000L;
        long randomYearMath = (long) ((Math.random()*59)+1) * 31536000;
        long randomYear = currentSeconds - randomYearMath;
        return randomYear;
    }
}

