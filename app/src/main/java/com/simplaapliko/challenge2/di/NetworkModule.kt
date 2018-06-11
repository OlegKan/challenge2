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

package com.simplaapliko.challenge2.di

import com.simplaapliko.challenge2.BuildConfig
import com.simplaapliko.challenge2.domain.repository.AirlineRepository
import com.simplaapliko.challenge2.domain.repository.AirportRepository
import com.simplaapliko.challenge2.domain.repository.CurrencyRepository
import com.simplaapliko.challenge2.domain.repository.HotelRepository
import com.simplaapliko.challenge2.domain.usecase.PrefetchUseCase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @ApplicationScope
    fun providePrefetchUseCase(airlineRepository: AirlineRepository,
        airportRepository: AirportRepository, currencyRepository: CurrencyRepository,
        hotelRepository: HotelRepository): PrefetchUseCase {
        return PrefetchUseCase(airlineRepository, airportRepository, currencyRepository,
            hotelRepository)
    }
}
