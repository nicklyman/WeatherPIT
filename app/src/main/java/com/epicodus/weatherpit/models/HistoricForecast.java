package com.epicodus.weatherpit.models;


import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;

import com.epicodus.weatherpit.services.HistoricForecastService;
import com.epicodus.weatherpit.ui.CurrentHistoricWeatherActivity;
import com.epicodus.weatherpit.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Parcel
public class HistoricForecast {
    private long mHistoricTime;
    private String mHistoricDailySummary;
    private double mHistoricMinTemp;
    private double mHistoricMaxTemp;
    private long mHistoricTimeOffset;
    private double mLatitude;
    private double mLongitude;
    private String mHistoricDailyIcon;

    public HistoricForecast() {}

    public HistoricForecast(long historicTime, String historicDailySummary, double minHistoricTemp, double maxHistoricTemp, long historicTimeOffset, double latitude, double longitude, String historicDailyIcon) {
        this.mHistoricTime = historicTime;
        this.mHistoricDailySummary = historicDailySummary;
        this.mHistoricMinTemp = minHistoricTemp;
        this.mHistoricMaxTemp = maxHistoricTemp;
        this.mHistoricTimeOffset = historicTimeOffset;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mHistoricDailyIcon = historicDailyIcon;
    }

    public long getHistoricUnixTime() {
        return mHistoricTime;
    }

    public String getHistoricDailySummary() {
        return mHistoricDailySummary;
    }

    public double getHistoricDailyMinTemp() {
        return mHistoricMinTemp;
    }

    public double getHistoricDailyMaxTemp() {
        return mHistoricMaxTemp;
    }

    public long getHistoricTimeOffset() { return mHistoricTimeOffset; }

    public double getLatitude() { return mLatitude; }

    public double getLongitude() { return mLongitude; }

    public String getHistoricDailyIcon() { return mHistoricDailyIcon; }

}





