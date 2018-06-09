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

import android.content.Context
import com.google.gson.Gson
import com.simplaapliko.challenge2.data.datasource.AirlineDataSource
import com.simplaapliko.challenge2.data.datasource.AirportDataSource
import com.simplaapliko.challenge2.data.datasource.CurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.DealDataSource
import com.simplaapliko.challenge2.data.datasource.HotelDataSource
import com.simplaapliko.challenge2.data.datasource.mock.MockAirlineDataSource
import com.simplaapliko.challenge2.data.datasource.mock.MockAirportDataSource
import com.simplaapliko.challenge2.data.datasource.mock.MockCurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.mock.MockDealDataSource
import com.simplaapliko.challenge2.data.datasource.mock.MockHotelDataSource
import com.simplaapliko.challenge2.data.repository.AirlineDataRepository
import com.simplaapliko.challenge2.data.repository.AirportDataRepository
import com.simplaapliko.challenge2.data.repository.CurrencyDataRepository
import com.simplaapliko.challenge2.data.repository.DealDataRepository
import com.simplaapliko.challenge2.data.repository.HotelDataRepository
import com.simplaapliko.challenge2.domain.repository.AirlineRepository
import com.simplaapliko.challenge2.domain.repository.AirportRepository
import com.simplaapliko.challenge2.domain.repository.CurrencyRepository
import com.simplaapliko.challenge2.domain.repository.DealRepository
import com.simplaapliko.challenge2.domain.repository.HotelRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideAirlineDataSource(context: Context, gson: Gson): AirlineDataSource {
        return MockAirlineDataSource(context, gson)
    }

    @Provides
    @ApplicationScope
    fun provideAirportDataSource(context: Context, gson: Gson): AirportDataSource {
        return MockAirportDataSource(context, gson)
    }

    @Provides
    @ApplicationScope
    fun provideCurrencyDataSource(context: Context, gson: Gson): CurrencyDataSource {
        return MockCurrencyDataSource(context, gson)
    }

    @Provides
    @ApplicationScope
    fun provideDealDataSource(context: Context, gson: Gson): DealDataSource {
        return MockDealDataSource(context, gson)
    }

    @Provides
    @ApplicationScope
    fun provideHotelDataSource(context: Context, gson: Gson): HotelDataSource {
        return MockHotelDataSource(context, gson)
    }

    @Provides
    @ApplicationScope
    fun provideAirlineRepository(dataSource: AirlineDataSource): AirlineRepository {
        return AirlineDataRepository(dataSource)
    }

    @Provides
    @ApplicationScope
    fun provideAirportRepository(dataSource: AirportDataSource): AirportRepository {
        return AirportDataRepository(dataSource)
    }

    @Provides
    @ApplicationScope
    fun provideCurrencyRepository(dataSource: CurrencyDataSource): CurrencyRepository {
        return CurrencyDataRepository(dataSource)
    }

    @Provides
    @ApplicationScope
    fun provideHotelRepository(dataSource: HotelDataSource): HotelRepository {
        return HotelDataRepository(dataSource)
    }

    @Provides
    @ApplicationScope
    fun provideDealRepository(airlineRepository: AirlineRepository,
        airportRepository: AirportRepository, currencyRepository: CurrencyRepository,
        dealDataSource: DealDataSource , hotelRepository: HotelRepository): DealRepository {
        return DealDataRepository(airlineRepository, airportRepository, currencyRepository,
            dealDataSource, hotelRepository)
    }
}
