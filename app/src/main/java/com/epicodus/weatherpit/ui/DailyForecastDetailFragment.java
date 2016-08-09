package com.epicodus.weatherpit.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.weatherpit.R;
import com.epicodus.weatherpit.models.Forecast;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DailyForecastDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.hourlySummaryTextView) TextView mHourlySummary;
    @Bind(R.id.iconTextView) TextView mIcon;
    @Bind(R.id.weatherImageView) ImageView mWeatherIconPlaceholder;
    @Bind(R.id.lowTemperatureTextView) TextView mLowTemperatureTextView;
    @Bind(R.id.forecastLinkTextView) TextView mAPILink;
    @Bind(R.id.highTemperatureTextView) TextView mHighTemperatureTextView;
    @Bind(R.id.humidityPrecipitationTextView) TextView mHumidityPrecipitationTextView;

    private Forecast mForecast;

    public static DailyForecastDetailFragment newInstance(Forecast forecast) {
        DailyForecastDetailFragment dailyForecastDetailFragment = new DailyForecastDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("forecast", Parcels.wrap(forecast));
        dailyForecastDetailFragment.setArguments(args);
        return dailyForecastDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForecast = Parcels.unwrap(getArguments().getParcelable("forecast"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_forecast_detail, container, false);
        ButterKnife.bind(this, view);


        mHourlySummary.setText(mForecast.getWeeklySummary());
        if (mForecast.getDailyIcon().equals("clear-day")){
            Picasso.with(view.getContext()).load(R.drawable.clear_day).into(mWeatherIconPlaceholder);
        } if (mForecast.getDailyIcon().equals("clear-night")){
            Picasso.with(view.getContext()).load(R.drawable.clear_night).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("rain")){
            Picasso.with(view.getContext()).load(R.drawable.rain).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("snow")){
            Picasso.with(view.getContext()).load(R.drawable.snow).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("sleet")){
            Picasso.with(view.getContext()).load(R.drawable.sleet).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("wind")){
            Picasso.with(view.getContext()).load(R.drawable.wind).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("fog")){
            Picasso.with(view.getContext()).load(R.drawable.fog).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("cloudy")){
            Picasso.with(view.getContext()).load(R.drawable.cloudy).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("partly-cloudy-day")){
            Picasso.with(view.getContext()).load(R.drawable.partly_cloudy_day).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("partly-cloudy-night")){
            Picasso.with(view.getContext()).load(R.drawable.partly_cloudy_night).into(mWeatherIconPlaceholder);
        } if(mForecast.getDailyIcon().equals("")){
            Picasso.with(view.getContext()).load(R.drawable.weather_clock_icon).into(mWeatherIconPlaceholder);
        }
        mHighTemperatureTextView.setText("High Temperature:  " + Double.toString(mForecast.getDailyMaxTemp()) + "°F");
        mLowTemperatureTextView.setText("Low Temperature:  " + Double.toString(mForecast.getDailyMinTemp()) + "°F");
        mHumidityPrecipitationTextView.setText("Humidity: " + mForecast.getHumidityPercent() + "%  -  Preciptation: " + mForecast.getPrecpitationPercent() + "%");


        mAPILink.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mAPILink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://forecast.io/"));
            startActivity(webIntent);
        }
    }

}
