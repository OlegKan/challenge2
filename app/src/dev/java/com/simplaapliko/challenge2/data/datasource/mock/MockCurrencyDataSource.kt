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

package com.simplaapliko.challenge2.data.datasource.mock

import android.content.Context
import com.google.gson.Gson
import com.simplaapliko.challenge2.R
import com.simplaapliko.challenge2.data.datasource.CurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.response.CurrencyResponse
import com.simplaapliko.challenge2.di.Utils
import io.reactivex.Maybe

class MockCurrencyDataSource(context: Context, gson: Gson) : CurrencyDataSource {

    private var map: MutableMap<String, CurrencyResponse.CurrencyEntity> = mutableMapOf()

    init {
        val jsonData = Utils.rawResourceAsString(context, R.raw.currencies)

        val item = gson.fromJson(jsonData, CurrencyResponse::class.java)

        map[item.bht.id] = item.bht
        map[item.eur.id] = item.eur
        map[item.usd.id] = item.usd
        map[item.yen.id] = item.yen
    }

    override fun get(id: String): Maybe<CurrencyResponse.CurrencyEntity> {
        val item = map[id]
        return if (item != null) {
            Maybe.just(item)
        } else {
            Maybe.empty()
        }
    }
}
