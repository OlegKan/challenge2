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

import com.simplaapliko.challenge2.data.datasource.CurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.response.CurrencyResponse
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class MemoryCurrencyDataSource : CurrencyDataSource, Cache.Currency {

    private var map: MutableMap<String, CurrencyResponse.CurrencyEntity> = mutableMapOf()

    override fun get(id: String): Maybe<CurrencyResponse.CurrencyEntity> {
        val item = map[id]
        return if (item != null) {
            Maybe.just(item)
        } else {
            Maybe.empty()
        }
    }

    override fun put(list: List<CurrencyResponse.CurrencyEntity>) {
        for (item in list) {
            map[item.id] = item
        }
    }

    override fun getAll(): Single<List<CurrencyResponse.CurrencyEntity>> {
        return Single.just(map.values.toList())
    }

    override fun prefetch(): Completable {
        return Completable.complete()
    }
}
