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
import com.simplaapliko.challenge2.data.datasource.CurrencyDataSource;
import com.simplaapliko.challenge2.data.datasource.memory.Cache;
import com.simplaapliko.challenge2.data.datasource.remote.rx.GetCurrenciesSingle;
import com.simplaapliko.challenge2.data.datasource.response.CurrencyResponse;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.OkHttpClient;

public class RemoteCurrencyDataSource implements CurrencyDataSource {

    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private final Cache.Currency cache;

    public RemoteCurrencyDataSource(OkHttpClient okHttpClient, Gson gson, Cache.Currency cache) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.cache = cache;
    }

    @NotNull
    @Override
    public Maybe<CurrencyResponse.CurrencyEntity> get(@NotNull String id) {
        return Maybe.concat(
                cache.get(id),
                GetCurrenciesSingle.create(okHttpClient, gson)
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
        return GetCurrenciesSingle.create(okHttpClient, gson)
                .doOnSuccess(cache::put)
                .ignoreElement();
    }
}
