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
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.simplaapliko.challenge2.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PriceView extends AppCompatTextView {

    private static final NumberFormat FORMATTER = DecimalFormat.getNumberInstance();

    private String currencyText;
    private double price = 0;
    private int textAppearance;

    static {
        FORMATTER.setGroupingUsed(true);
        FORMATTER.setMaximumFractionDigits(2);
        FORMATTER.setMinimumFractionDigits(2);
    }

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PriceView, 0, 0);

        try {
            textAppearance = a.getResourceId(R.styleable.PriceView_priceTextAppearance, 0);
        } finally {
            a.recycle();
        }

        if (textAppearance == 0) {
            setTextAppearance(context, R.style.PriceView);
        } else {
            setTextAppearance(context, textAppearance);
        }
    }

    public void setPriceText(double value) {
        this.price = value;
        refreshText();
    }

    public void setCurrency(String value) {
        this.currencyText = value;
        refreshText();
    }

    private void refreshText() {
        String formattedPrice = FORMATTER.format(price);

        if (currencyText != null && !currencyText.isEmpty()) {
            String text = String.format("%s%s", currencyText, formattedPrice);
            setText(text);
        } else {
            setText(formattedPrice);
        }
    }
}
