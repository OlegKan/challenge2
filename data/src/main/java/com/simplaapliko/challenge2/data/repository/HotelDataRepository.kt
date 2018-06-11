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

import com.simplaapliko.challenge2.data.datasource.HotelDataSource
import com.simplaapliko.challenge2.domain.model.Hotel
import com.simplaapliko.challenge2.domain.repository.HotelRepository
import io.reactivex.Completable
import io.reactivex.Maybe

class HotelDataRepository(private val dataSource: HotelDataSource) : HotelRepository {

    override fun get(id: String): Maybe<Hotel> {
        return dataSource.get(id).map { t -> t.toHotel() }
    }

    override fun prefetch(): Completable {
        return dataSource.prefetch()
    }
}
