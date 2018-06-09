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

public class LodgingView extends LinearLayout {

    private int image;
    private String labelText;
    private int nights;
    private int labelTextAppearance;
    private int nightsTextAppearance;

    private ImageView imageView;
    private TextView labelTextView;
    private TextView nightsTextView;

    public LodgingView(Context context) {
        this(context, null);
    }

    public LodgingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LodgingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.view_lodging, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LodgingView, 0, 0);

        try {
            image = a.getResourceId(R.styleable.LodgingView_image, 0);
            nights = a.getInteger(R.styleable.LodgingView_nights, 1);
            labelTextAppearance = a.getResourceId(R.styleable.LodgingView_labelTextAppearance, 0);
            nightsTextAppearance = a.getResourceId(R.styleable.LodgingView_nightsTextAppearance, 0);
        } finally {
            a.recycle();
        }

        imageView = findViewById(R.id.image);
        labelTextView = findViewById(R.id.nights_label);
        nightsTextView = findViewById(R.id.nights_number);

        if (labelTextAppearance == 0) {
            labelTextView.setTextAppearance(context, R.style.LodgingView_NightsLabel);
        } else {
            labelTextView.setTextAppearance(context, labelTextAppearance);
        }

        if (nightsTextAppearance == 0) {
            nightsTextView.setTextAppearance(context, R.style.LodgingView_Nights);
        } else {
            nightsTextView.setTextAppearance(context, nightsTextAppearance);
        }

        setImage(image);
        setNights(nights);
    }

    public void setImage(int image) {
        this.image = image;

        if (this.image != 0) {
            imageView.setImageResource(this.image);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setNights(int nights) {
        this.nights = nights;

        String label = getResources().getQuantityString(R.plurals.nights, this.nights);

        labelTextView.setText(label);
        nightsTextView.setText(String.valueOf(this.nights));
    }
}
