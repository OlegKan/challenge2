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

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LodgingDetailsView extends LinearLayout {

    private static final NumberFormat FORMATTER = DecimalFormat.getNumberInstance();

    static {
        FORMATTER.setMaximumFractionDigits(1);
        FORMATTER.setMinimumFractionDigits(0);
    }

    private String hotelText;
    private int nights;
    private double rating;
    private int hotelInfoTextAppearance;
    private int nightsLabelTextAppearance;
    private int nightsTextAppearance;

    private ImageView hotelLogoImageView;
    private TextView hotelInfoTextView;
    private TextView nightsLabelTextView;
    private TextView nightsTextView;

    public LodgingDetailsView(Context context) {
        this(context, null);
    }

    public LodgingDetailsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LodgingDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.view_lodging_details, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LodgingDetailsView, 0, 0);

        try {
            hotelInfoTextAppearance = a.getResourceId(R.styleable.LodgingDetailsView_hotelInfoTextAppearance, 0);
            nightsLabelTextAppearance = a.getResourceId(R.styleable.LodgingDetailsView_labelTextAppearance, 0);
            nightsTextAppearance = a.getResourceId(R.styleable.LodgingDetailsView_nightsTextAppearance, 0);
        } finally {
            a.recycle();
        }

        hotelInfoTextView = findViewById(R.id.hotel_info);
        hotelLogoImageView = findViewById(R.id.hotel_logo);
        nightsLabelTextView = findViewById(R.id.nights_label);
        nightsTextView = findViewById(R.id.nights_number);

        if (hotelInfoTextAppearance == 0) {
            hotelInfoTextView.setTextAppearance(context, R.style.LodgingDetailsView_HotelInfo);
        } else {
            hotelInfoTextView.setTextAppearance(context, hotelInfoTextAppearance);
        }

        if (nightsLabelTextAppearance == 0) {
            nightsLabelTextView.setTextAppearance(context, R.style.LodgingDetailsView_NightsLabel);
        } else {
            nightsLabelTextView.setTextAppearance(context, nightsLabelTextAppearance);
        }

        if (nightsTextAppearance == 0) {
            nightsTextView.setTextAppearance(context, R.style.LodgingDetailsView_Nights);
        } else {
            nightsTextView.setTextAppearance(context, nightsTextAppearance);
        }
    }

    public ImageView getHotelLogoImageView() {
        return hotelLogoImageView;
    }

    public void setHotel(String value) {
        hotelText = value;
        refreshHotelInfoText();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double value) {
        rating = value;
        refreshHotelInfoText();
    }

    private void refreshHotelInfoText() {
        String formattedRating = FORMATTER.format(rating);

        String hotelInfoText = String.format("%s (%s★)", hotelText, formattedRating);
        hotelInfoTextView.setText(hotelInfoText);
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;

        String label = getResources().getQuantityString(R.plurals.nights, this.nights);

        nightsLabelTextView.setText(label);
        nightsTextView.setText(String.valueOf(this.nights));
    }
}
