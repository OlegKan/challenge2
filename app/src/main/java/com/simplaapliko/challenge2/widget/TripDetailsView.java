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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplaapliko.challenge2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TripDetailsView extends LinearLayout {

    private static final SimpleDateFormat DATE_FORMAT;
    private static final SimpleDateFormat TIME_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        TIME_FORMAT = new SimpleDateFormat("h:mm a", Locale.getDefault());
    }

    private String flightsText;
    private Date departureDateTime;
    private Date returnDateTime;
    private int dateTextAppearance;
    private int flightsTextAppearance;
    private int timeTextAppearance;

    private TextView flightsTextView;
    private TextView departureDateTextView;
    private TextView departureTimeTextView;
    private TextView returnDateTextView;
    private TextView returnTimeTextView;

    public TripDetailsView(Context context) {
        this(context, null);
    }

    public TripDetailsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TripDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.view_trip_details, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TripDetailsView, 0, 0);

        try {
            dateTextAppearance = a.getResourceId(R.styleable.TripDetailsView_dateTextAppearance, 0);
            flightsTextAppearance = a.getResourceId(R.styleable.TripDetailsView_flightsTextAppearance, 0);
            timeTextAppearance = a.getResourceId(R.styleable.TripDetailsView_timeTextAppearance, 0);
        } finally {
            a.recycle();
        }

        flightsTextView = findViewById(R.id.flights);
        departureDateTextView = findViewById(R.id.departure_date);
        departureTimeTextView = findViewById(R.id.departure_time);
        returnDateTextView = findViewById(R.id.return_date);
        returnTimeTextView = findViewById(R.id.return_time);

        if (dateTextAppearance == 0) {
            departureDateTextView.setTextAppearance(context, R.style.TripDetailsView_Date);
            returnDateTextView.setTextAppearance(context, R.style.TripDetailsView_Date);
        } else {
            departureDateTextView.setTextAppearance(context, dateTextAppearance);
            returnDateTextView.setTextAppearance(context, dateTextAppearance);
        }

        if (flightsTextAppearance == 0) {
            flightsTextView.setTextAppearance(context, R.style.TripDetailsView_Flights);
        } else {
            flightsTextView.setTextAppearance(context, flightsTextAppearance);
        }

        if (timeTextAppearance == 0) {
            departureTimeTextView.setTextAppearance(context, R.style.TripDetailsView_Time);
            returnTimeTextView.setTextAppearance(context, R.style.TripDetailsView_Time);
        } else {
            departureTimeTextView.setTextAppearance(context, timeTextAppearance);
            returnTimeTextView.setTextAppearance(context, timeTextAppearance);
        }
    }

    public String getFlightsText() {
        return flightsText;
    }

    public void setFlights(String value) {
        flightsText = value;
        flightsTextView.setText(value);
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Date value) {
        departureDateTime = value;
        departureDateTextView.setText(DATE_FORMAT.format(departureDateTime));
        departureTimeTextView.setText(TIME_FORMAT.format(departureDateTime));
    }

    public Date getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(Date value) {
        returnDateTime = value;
        returnDateTextView.setText(DATE_FORMAT.format(returnDateTime));
        returnTimeTextView.setText(TIME_FORMAT.format(returnDateTime));
    }
}
