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
import com.simplaapliko.challenge2.data.datasource.HotelDataSource;
import com.simplaapliko.challenge2.data.datasource.memory.Cache;
import com.simplaapliko.challenge2.data.datasource.remote.rx.GetHotelsSingle;
import com.simplaapliko.challenge2.data.datasource.response.HotelResponse;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Maybe;
import okhttp3.OkHttpClient;

public class RemoteHotelDataSource implements HotelDataSource {

    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private final Cache.Hotel cache;

    public RemoteHotelDataSource(OkHttpClient okHttpClient, Gson gson, Cache.Hotel cache) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.cache = cache;
    }

    @NotNull
    @Override
    public Maybe<HotelResponse.HotelEntity> get(@NotNull String id) {
        return Maybe.concat(
                cache.getCached(id),
                GetHotelsSingle.create(okHttpClient, gson)
                        .doOnSuccess(cache::cache)
                        .toObservable()
                        .flatMapIterable(t -> t)
                        .filter(entity -> id.equals(entity.id))
                        .singleElement())
                .firstElement();
    }
}
