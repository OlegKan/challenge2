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

import com.google.gson.Gson
import com.simplaapliko.challenge2.data.datasource.AirlineDataSource
import com.simplaapliko.challenge2.data.datasource.AirportDataSource
import com.simplaapliko.challenge2.data.datasource.CurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.DealDataSource
import com.simplaapliko.challenge2.data.datasource.HotelDataSource
import com.simplaapliko.challenge2.data.datasource.memory.Cache
import com.simplaapliko.challenge2.data.datasource.memory.MemoryAirlineDataSource
import com.simplaapliko.challenge2.data.datasource.memory.MemoryAirportDataSource
import com.simplaapliko.challenge2.data.datasource.memory.MemoryCurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.memory.MemoryDealDataSource
import com.simplaapliko.challenge2.data.datasource.memory.MemoryHotelDataSource
import com.simplaapliko.challenge2.data.datasource.remote.RemoteAirlineDataSource
import com.simplaapliko.challenge2.data.datasource.remote.RemoteAirportDataSource
import com.simplaapliko.challenge2.data.datasource.remote.RemoteCurrencyDataSource
import com.simplaapliko.challenge2.data.datasource.remote.RemoteDealDataSource
import com.simplaapliko.challenge2.data.datasource.remote.RemoteHotelDataSource
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
import okhttp3.OkHttpClient

@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideAirlineCache(): Cache.Airline {
        return MemoryAirlineDataSource()
    }

    @Provides
    @ApplicationScope
    fun provideAirportCache(): Cache.Airport {
        return MemoryAirportDataSource()
    }

    @Provides
    @ApplicationScope
    fun provideCurrencyCache(): Cache.Currency {
        return MemoryCurrencyDataSource()
    }

    @Provides
    @ApplicationScope
    fun provideDealCache(): Cache.Deal {
        return MemoryDealDataSource()
    }

    @Provides
    @ApplicationScope
    fun provideHotelCache(): Cache.Hotel {
        return MemoryHotelDataSource()
    }

    @Provides
    @ApplicationScope
    fun provideAirlineDataSource(okHttpClient: OkHttpClient, gson: Gson,
        cache: Cache.Airline): AirlineDataSource {
        return RemoteAirlineDataSource(okHttpClient, gson, cache)
    }

    @Provides
    @ApplicationScope
    fun provideAirportDataSource(okHttpClient: OkHttpClient, gson: Gson,
        cache: Cache.Airport): AirportDataSource {
        return RemoteAirportDataSource(okHttpClient, gson, cache)
    }

    @Provides
    @ApplicationScope
    fun provideCurrencyDataSource(okHttpClient: OkHttpClient, gson: Gson,
        cache: Cache.Currency): CurrencyDataSource {
        return RemoteCurrencyDataSource(okHttpClient, gson, cache)
    }

    @Provides
    @ApplicationScope
    fun provideDealDataSource(okHttpClient: OkHttpClient, gson: Gson,
        cache: Cache.Deal): DealDataSource {
        return RemoteDealDataSource(okHttpClient, gson, cache)
    }

    @Provides
    @ApplicationScope
    fun provideHotelDataSource(okHttpClient: OkHttpClient, gson: Gson,
        cache: Cache.Hotel): HotelDataSource {
        return RemoteHotelDataSource(okHttpClient, gson, cache)
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
        dealDataSource: DealDataSource, hotelRepository: HotelRepository): DealRepository {
        return DealDataRepository(airlineRepository, airportRepository, currencyRepository,
            dealDataSource, hotelRepository)
    }
}
