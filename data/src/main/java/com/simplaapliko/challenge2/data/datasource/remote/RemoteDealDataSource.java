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
import com.simplaapliko.challenge2.data.datasource.DealDataSource;
import com.simplaapliko.challenge2.data.datasource.memory.Cache;
import com.simplaapliko.challenge2.data.datasource.remote.rx.GetDealsSingle;
import com.simplaapliko.challenge2.data.datasource.response.DealResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Single;
import okhttp3.OkHttpClient;

public class RemoteDealDataSource implements DealDataSource {

    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private final Cache.Deal cache;

    public RemoteDealDataSource(OkHttpClient okHttpClient, Gson gson, Cache.Deal cache) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.cache = cache;
    }

    @NotNull
    @Override
    public Single<List<DealResponse>> getAll() {
        return cache.get()
                .onErrorResumeNext(GetDealsSingle.create(okHttpClient, gson)
                        .doOnSuccess(cache::put));
    }
}
