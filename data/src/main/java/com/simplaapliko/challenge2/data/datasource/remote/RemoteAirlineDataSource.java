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

package com.simplaapliko.challenge2.data.datasource.remote;

import com.google.gson.Gson;
import com.simplaapliko.challenge2.data.datasource.AirlineDataSource;
import com.simplaapliko.challenge2.data.datasource.memory.Cache;
import com.simplaapliko.challenge2.data.datasource.remote.rx.GetAirlinesSingle;
import com.simplaapliko.challenge2.data.datasource.response.AirlineResponse;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.OkHttpClient;

public class RemoteAirlineDataSource implements AirlineDataSource {

    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private final Cache.Airline cache;

    public RemoteAirlineDataSource(OkHttpClient okHttpClient, Gson gson, Cache.Airline cache) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.cache = cache;
    }

    @NotNull
    @Override
    public Maybe<AirlineResponse.AirlineEntity> get(@NotNull String id) {
        return Maybe.concat(
                cache.get(id),
                GetAirlinesSingle.create(okHttpClient, gson)
                        .doOnSuccess(cache::put)
                        .toObservable()
                        .flatMapIterable(t -> t)
                        .filter(entity -> id.equals(entity.id))
                        .singleElement())
                .firstElement();
    }

    @NotNull
    @Override
    public Completable prefetch() {
        return GetAirlinesSingle.create(okHttpClient, gson)
                .doOnSuccess(cache::put)
                .ignoreElement();
    }
}
