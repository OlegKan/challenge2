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
import com.simplaapliko.challenge2.data.datasource.AirlineDataSource
import com.simplaapliko.challenge2.data.datasource.response.AirlineResponse
import com.simplaapliko.challenge2.di.Utils
import io.reactivex.Completable
import io.reactivex.Maybe

class MockAirlineDataSource(context: Context, gson: Gson) : AirlineDataSource {

    private var map: MutableMap<String, AirlineResponse.AirlineEntity> = mutableMapOf()

    init {
        val jsonData = Utils.rawResourceAsString(context, R.raw.airlines)

        val item = gson.fromJson<AirlineResponse>(jsonData, AirlineResponse::class.java)

        map[item.aa.id] = item.aa
        map[item.`as`.id] = item.`as`
        map[item.b6.id] = item.b6
        map[item.dl.id] = item.dl
        map[item.ua.id] = item.ua
        map[item.vx.id] = item.vx
    }

    override fun get(id: String): Maybe<AirlineResponse.AirlineEntity> {
        val item = map[id]
        return if (item != null) {
            Maybe.just(item)
        } else {
            Maybe.empty()
        }
    }

    override fun prefetch(): Completable {
        return Completable.complete()
    }
}
