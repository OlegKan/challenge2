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
import com.simplaapliko.challenge2.data.datasource.AirportDataSource
import com.simplaapliko.challenge2.data.datasource.response.AirportResponse
import com.simplaapliko.challenge2.di.Utils
import io.reactivex.Maybe

class MockAirportDataSource(context: Context, gson: Gson) : AirportDataSource {

    private var map: MutableMap<String, AirportResponse.AirportEntity> = mutableMapOf()

    init {
        val jsonData = Utils.rawResourceAsString(context, R.raw.airports)

        val item = gson.fromJson<AirportResponse>(jsonData, AirportResponse::class.java)

        map[item.atl.id] = item.atl
        map[item.bkk.id] = item.bkk
        map[item.cnx.id] = item.cnx
        map[item.hkt.id] = item.hkt
        map[item.iad.id] = item.iad
        map[item.jfk.id] = item.jfk
        map[item.lhr.id] = item.lhr
        map[item.nrt.id] = item.nrt
        map[item.san.id] = item.san
        map[item.sea.id] = item.sea
        map[item.txl.id] = item.txl
        map[item.ukb.id] = item.ukb
    }

    override fun get(id: String): Maybe<AirportResponse.AirportEntity> {
        val item = map[id]
        return if (item != null) {
            Maybe.just(item)
        } else {
            Maybe.empty()
        }
    }
}
