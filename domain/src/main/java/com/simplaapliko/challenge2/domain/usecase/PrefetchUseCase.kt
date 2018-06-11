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

package com.simplaapliko.challenge2.domain.usecase

import com.simplaapliko.challenge2.domain.repository.AirlineRepository
import com.simplaapliko.challenge2.domain.repository.AirportRepository
import com.simplaapliko.challenge2.domain.repository.CurrencyRepository
import com.simplaapliko.challenge2.domain.repository.HotelRepository
import io.reactivex.Completable

class PrefetchUseCase(private val airlineRepository: AirlineRepository,
    private val airportRepository: AirportRepository,
    private val currencyRepository: CurrencyRepository,
    private val hotelRepository: HotelRepository) {

    fun prefech(): Completable {
        return airlineRepository.prefetch()
            .mergeWith(airportRepository.prefetch())
            .mergeWith(currencyRepository.prefetch())
            .mergeWith(hotelRepository.prefetch())
    }
}