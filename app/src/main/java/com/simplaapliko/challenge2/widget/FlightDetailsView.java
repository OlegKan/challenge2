/*
 * Copyright (C) 2018 Oleg Kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.challenge2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplaapliko.challenge2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FlightDetailsView extends LinearLayout {

    private static final SimpleDateFormat DATE_FORMAT;
    private static final SimpleDateFormat TIME_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy MMM dd, EEE", Locale.getDefault());
        TIME_FORMAT = new SimpleDateFormat("h:mm a", Locale.getDefault());
    }

    private String airlineText;
    private String flightInfoText;
    private String departureAirport;
    private String departureCity;
    private Date departureDateTime;
    private String arrivalAirport;
    private String arrivalCity;
    private Date arrivalDateTime;
    private int airlineTextAppearance;
    private int dateTextAppearance;
    private int flightInfoTextAppearance;
    private int locationTextAppearance;
    private int timeTextAppearance;

    private TextView airlineTextView;
    private ImageView airlineLogoImageView;
    private TextView flightInfoTextView;
    private TextView departureLocationTextView;
    private TextView departureDateTextView;
    private TextView departureTimeTextView;
    private TextView arrivalLocationTextView;
    private TextView arrivalDateTextView;
    private TextView arrivalTimeTextView;

    public FlightDetailsView(Context context) {
        this(context, null);
    }

    public FlightDetailsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlightDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.view_flight_details, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlightDetailsView, 0, 0);

        try {
            airlineTextAppearance = a.getResourceId(R.styleable.FlightDetailsView_airlineTextAppearance, 0);
            dateTextAppearance = a.getResourceId(R.styleable.FlightDetailsView_dateTextAppearance, 0);
            flightInfoTextAppearance = a.getResourceId(R.styleable.FlightDetailsView_flightInfoTextAppearance, 0);
            locationTextAppearance = a.getResourceId(R.styleable.FlightDetailsView_locationTextAppearance, 0);
            timeTextAppearance = a.getResourceId(R.styleable.FlightDetailsView_timeTextAppearance, 0);
        } finally {
            a.recycle();
        }

        airlineTextView = findViewById(R.id.airline);
        airlineLogoImageView = findViewById(R.id.airline_logo);
        flightInfoTextView = findViewById(R.id.flight_info);
        departureLocationTextView = findViewById(R.id.departure_location);
        departureDateTextView = findViewById(R.id.departure_date);
        departureTimeTextView = findViewById(R.id.departure_time);
        arrivalLocationTextView = findViewById(R.id.arrival_location);
        arrivalDateTextView = findViewById(R.id.arrival_date);
        arrivalTimeTextView = findViewById(R.id.arrival_time);

        if (airlineTextAppearance == 0) {
            airlineTextView.setTextAppearance(context, R.style.FlightDetailsView_Airline);
        } else {
            airlineTextView.setTextAppearance(context, airlineTextAppearance);
        }

        if (dateTextAppearance == 0) {
            departureDateTextView.setTextAppearance(context, R.style.FlightDetailsView_Date);
            arrivalDateTextView.setTextAppearance(context, R.style.FlightDetailsView_Date);
        } else {
            departureDateTextView.setTextAppearance(context, dateTextAppearance);
            arrivalDateTextView.setTextAppearance(context, dateTextAppearance);
        }

        if (flightInfoTextAppearance == 0) {
            flightInfoTextView.setTextAppearance(context, R.style.FlightDetailsView_FlightInfo);
        } else {
            flightInfoTextView.setTextAppearance(context, flightInfoTextAppearance);
        }

        if (locationTextAppearance == 0) {
            departureLocationTextView.setTextAppearance(context, R.style.FlightDetailsView_Location);
            arrivalLocationTextView.setTextAppearance(context, R.style.FlightDetailsView_Location);
        } else {
            departureLocationTextView.setTextAppearance(context, locationTextAppearance);
            arrivalLocationTextView.setTextAppearance(context, locationTextAppearance);
        }

        if (timeTextAppearance == 0) {
            departureTimeTextView.setTextAppearance(context, R.style.FlightDetailsView_Time);
            arrivalTimeTextView.setTextAppearance(context, R.style.FlightDetailsView_Time);
        } else {
            departureTimeTextView.setTextAppearance(context, timeTextAppearance);
            arrivalTimeTextView.setTextAppearance(context, timeTextAppearance);
        }
    }

    public ImageView getAirlineLogoImageView() {
        return airlineLogoImageView;
    }

    public String getAirline() {
        return airlineText;
    }

    public void setAirline(String value) {
        airlineText = value;
        airlineTextView.setText(value);
    }

    public String getFlightInfo() {
        return flightInfoText;
    }

    public void setFlightInfo(String value) {
        flightInfoText = value;
        flightInfoTextView.setText(value);
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
        refreshLocation(departureLocationTextView, this.departureAirport, this.departureCity);
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
        refreshLocation(departureLocationTextView, this.departureAirport, this.departureCity);
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Date value) {
        departureDateTime = value;
        String date = formatDate(getResources().getString(R.string.departs), departureDateTime);
        departureDateTextView.setText(date);
        departureTimeTextView.setText(TIME_FORMAT.format(departureDateTime));
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        refreshLocation(arrivalLocationTextView, this.arrivalAirport, this.arrivalCity);
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
        refreshLocation(arrivalLocationTextView, this.arrivalAirport, this.arrivalCity);
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    private void refreshLocation(TextView view, String airport, String city) {
        String location = String.format("%s - %s", airport, city);
        view.setText(location);
    }

    public void setArrivalDateTime(Date value) {
        arrivalDateTime = value;
        String date = formatDate(getResources().getString(R.string.arrives), arrivalDateTime);
        arrivalDateTextView.setText(date);
        arrivalTimeTextView.setText(TIME_FORMAT.format(arrivalDateTime));
    }

    private String formatDate(String prefix, Date date) {
        return String.format("%s : %s", prefix, DATE_FORMAT.format(date));
    }
}
