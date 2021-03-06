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

package com.simplaapliko.challenge2.data.repository

import com.simplaapliko.challenge2.data.datasource.CurrencyDataSource
import com.simplaapliko.challenge2.domain.model.Currency
import com.simplaapliko.challenge2.domain.repository.CurrencyRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class CurrencyDataRepository(private val dataSource: CurrencyDataSource) : CurrencyRepository {

    override fun get(id: String): Maybe<Currency> {
        return dataSource.get(id).map { t -> t.toCurrency() }
    }

    override fun getAll(): Single<List<Currency>> {
        return dataSource.getAll()
            .toObservable()
            .flatMapIterable { t -> t }
            .map { t -> t.toCurrency() }
            .toList()
    }

    override fun prefetch(): Completable {
        return dataSource.prefetch()
    }
}
