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

package com.simplaapliko.challenge2.data.datasource.memory

import com.simplaapliko.challenge2.data.datasource.AirportDataSource
import com.simplaapliko.challenge2.data.datasource.response.AirportResponse
import io.reactivex.Completable
import io.reactivex.Maybe

class MemoryAirportDataSource : AirportDataSource, Cache.Airport {

    private var map: MutableMap<String, AirportResponse.AirportEntity> = mutableMapOf()

    override fun get(id: String): Maybe<AirportResponse.AirportEntity> {
        val item = map[id]
        return if (item != null) {
            Maybe.just(item)
        } else {
            Maybe.empty()
        }
    }

    override fun put(list: List<AirportResponse.AirportEntity>) {
        for (item in list) {
            map[item.id] = item
        }
    }

    override fun prefetch(): Completable {
        return Completable.complete()
    }
}
