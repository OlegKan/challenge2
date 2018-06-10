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
import com.simplaapliko.challenge2.data.datasource.response.HotelResponse
import io.reactivex.Maybe

interface Cache {

    interface Airline {
        fun cache(list: List<AirlineResponse.AirlineEntity>)

        fun getCached(id: String): Maybe<AirlineResponse.AirlineEntity>
    }

    interface Airport {
        fun cache(list: List<AirportResponse.AirportEntity>)

        fun getCached(id: String): Maybe<AirportResponse.AirportEntity>
    }

    interface Currency {
        fun cache(list: List<CurrencyResponse.CurrencyEntity>)

        fun getCached(id: String): Maybe<CurrencyResponse.CurrencyEntity>
    }

    interface Hotel {
        fun cache(list: List<HotelResponse.HotelEntity>)

        fun getCached(id: String): Maybe<HotelResponse.HotelEntity>
    }
}