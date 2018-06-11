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

import com.simplaapliko.challenge2.data.datasource.response.AirlineResponse
import com.simplaapliko.challenge2.data.datasource.response.AirportResponse
import com.simplaapliko.challenge2.data.datasource.response.CurrencyResponse
import com.simplaapliko.challenge2.data.datasource.response.DealResponse
import com.simplaapliko.challenge2.data.datasource.response.HotelResponse
import io.reactivex.Maybe
import io.reactivex.Single

interface Cache {

    interface Airline {
        fun put(list: List<AirlineResponse.AirlineEntity>)

        fun get(id: String): Maybe<AirlineResponse.AirlineEntity>
    }

    interface Airport {
        fun put(list: List<AirportResponse.AirportEntity>)

        fun get(id: String): Maybe<AirportResponse.AirportEntity>
    }

    interface Currency {
        fun put(list: List<CurrencyResponse.CurrencyEntity>)

        fun get(id: String): Maybe<CurrencyResponse.CurrencyEntity>

        fun getAll(): Single<List<CurrencyResponse.CurrencyEntity>>
    }

    interface Deal {
        fun put(list: List<DealResponse>)

        fun get(): Single<List<DealResponse>>
    }

    interface Hotel {
        fun put(list: List<HotelResponse.HotelEntity>)

        fun get(id: String): Maybe<HotelResponse.HotelEntity>
    }
}