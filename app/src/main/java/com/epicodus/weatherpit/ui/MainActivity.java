package com.epicodus.weatherpit.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.epicodus.weatherpit.Constants;
import com.epicodus.weatherpit.R;
import com.epicodus.weatherpit.models.HistoricForecast;
import com.epicodus.weatherpit.services.HistoricForecastService;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.getWeatherButton) Button mGetWeatherButton;
    @Bind(R.id.aboutAppButton) Button mAboutAppButton;
    @Bind(R.id.locationEditTextView) EditText mLocationEditTextView;
    @Bind(R.id.titleTextView) TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Typeface oswaldFont = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        mTitleTextView.setTypeface(oswaldFont);

        mGetWeatherButton.setOnClickListener(this);
        mAboutAppButton.setOnClickListener(this);
        };

    //Get LatLng from user input using geocoder
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder geoCoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> address;
        LatLng coordinates = null;

        try {
            address = geoCoder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            coordinates = new LatLng(location.getLatitude(), location.getLongitude());

            return coordinates;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinates;
    }

    @Override
    public void onClick(View view) {
        String enteredLocation = mLocationEditTextView.getText().toString().trim();

        if (view == mGetWeatherButton) {
            if (enteredLocation.equals("")) {
                mLocationEditTextView.setError("Please enter a valid address");
            } else {
                LatLng newCoordinates = getLocationFromAddress(enteredLocation);
                if(newCoordinates == null) {
                    mLocationEditTextView.setError("Couldn't find coordinates for this address, please try a different address");
                    return;
                }

                //Convert LatLng object to doubles for correctly passing coordinates as intents to multiple activities
                String stringXY = String.valueOf(newCoordinates);

                String coordinates = (stringXY.split("[\\(\\)]")[1]);
                List<String> coordinateList = Arrays.asList(coordinates.split(","));
                String latitude = coordinateList.get(0);
                String longitude = coordinateList.get(1);
                double lat = Double.parseDouble(latitude);
                double lng = Double.parseDouble(longitude);

                Intent intent = new Intent(MainActivity.this, CurrentHistoricWeatherActivity.class);
                intent.putExtra("userLocation", enteredLocation);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        }
        if(view == mAboutAppButton) {
            Intent intent = new Intent(MainActivity.this, AboutAppActivity.class);
            startActivity(intent);
        }
    }
}
